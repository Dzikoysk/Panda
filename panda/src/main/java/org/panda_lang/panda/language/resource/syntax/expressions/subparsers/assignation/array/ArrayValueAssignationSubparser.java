/*
 * Copyright (c) 2015-2020 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.array;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.expression.Expression;
import org.panda_lang.framework.design.architecture.expression.ExpressionUtils;
import org.panda_lang.framework.design.interpreter.parser.Components;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionResult;
import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionTransaction;
import org.panda_lang.framework.design.interpreter.parser.pipeline.Channel;
import org.panda_lang.framework.design.interpreter.parser.pipeline.Handler;
import org.panda_lang.framework.design.interpreter.token.Snippet;
import org.panda_lang.framework.design.interpreter.token.SourceStream;
import org.panda_lang.framework.design.interpreter.token.TokenInfo;
import org.panda_lang.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.framework.language.interpreter.token.PandaSourceStream;
import org.panda_lang.framework.language.resource.syntax.TokenTypes;
import org.panda_lang.framework.language.resource.syntax.auxiliary.Section;
import org.panda_lang.framework.language.resource.syntax.separator.Separators;
import org.panda_lang.panda.language.interpreter.parser.PandaPipeline;
import org.panda_lang.panda.language.interpreter.parser.RegistrableParser;
import org.panda_lang.panda.language.interpreter.parser.context.BootstrapInitializer;
import org.panda_lang.panda.language.interpreter.parser.context.annotations.Autowired;
import org.panda_lang.panda.language.interpreter.parser.context.annotations.Ctx;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationComponents;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationPriorities;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationSubparserBootstrap;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationType;

import java.util.Optional;

@RegistrableParser(pipeline = PandaPipeline.ASSIGNER_LABEL, priority = AssignationPriorities.ARRAY_ASSIGNATION)
public final class ArrayValueAssignationSubparser extends AssignationSubparserBootstrap {

    private static final ArrayValueAccessorParser PARSER = new ArrayValueAccessorParser();

    @Override
    protected BootstrapInitializer<@Nullable ExpressionResult> initialize(Context context, BootstrapInitializer<@Nullable ExpressionResult> initializer) {
        return initializer;
    }

    @Override
    protected Boolean customHandle(Handler handler, Context context, Channel channel, Snippet source) {
        TokenInfo sectionRepresentation = source.getLast();

        if (sectionRepresentation == null || sectionRepresentation.getType() != TokenTypes.SECTION) {
            return false;
        }

        Section section = sectionRepresentation.toToken();

        if (!section.getSeparator().equals(Separators.SQUARE_BRACKET_LEFT)) {
            return false;
        }

        SourceStream expressionSource = new PandaSourceStream(source.subSource(0, source.size() - 1));
        Optional<ExpressionTransaction> expressionTransactionValue = context.getComponent(Components.EXPRESSION).parseSilently(context, expressionSource);

        if (!expressionTransactionValue.isPresent()) {
            return false;
        }

        ExpressionTransaction expressionTransaction = expressionTransactionValue.get();

        if (expressionSource.hasUnreadSource()) {
            expressionTransaction.rollback();
            return false;
        }

        channel.put("array-instance", expressionTransaction.getExpression());
        return true;
    }

    @Autowired
    ExpressionResult parse(
            Context context,
            @Ctx SourceStream source, @Ctx Channel channel,
            @Ctx AssignationType type, @Ctx TokenInfo operator,
            @Ctx(AssignationComponents.EXPRESSION_LABEL) Expression value
    ) {
        if (type != AssignationType.DEFAULT) {
            throw new PandaParserFailure(context, operator, "Unsupported operator");
        }

        Snippet snippet = source.toSnippet();
        ArrayAccessor accessor = PARSER.parse(context, snippet, channel.get("array-instance", Expression.class), snippet.getLast().toToken());

        if (!accessor.getReturnType().isAssignableFrom(value.getType())) {
            throw new PandaParserFailure(context,
                    "Invalid value type, cannot assign " + value.getType() + " to the array of " + accessor.getReturnType(),
                    "Make sure that you are assigning the proper value or change type of the array"
            );
        }

        Expression equalizedExpression = ExpressionUtils.equalize(value, accessor.getReturnType());
        return ExpressionResult.of(accessor.toAssignerExpression(equalizedExpression));
    }

}
