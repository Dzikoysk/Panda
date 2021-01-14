/*
 * Copyright (c) 2020 Dzikoysk
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

package org.panda_lang.panda.language.resource.syntax.expressions;

import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparsers;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.CastExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.ConstructorExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.CreaseExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.DeclarationExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.IsExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.LiteralExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.MethodExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.NegateExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.number.NegativeExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.number.NotBitwiseExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.number.NumberExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.operation.OperationExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.SectionExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.SequenceExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.StaticExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.VariableExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.AssignationExpressionSubparser;

import java.util.Arrays;
import java.util.Collection;

public final class PandaExpressions {

    public static Collection<ExpressionSubparser> createSubparsers() {
        return Arrays.asList(
                new AssignationExpressionSubparser(),
                new CastExpressionSubparser(),
                new ConstructorExpressionSubparser(),
                new CreaseExpressionSubparser(),
                new DeclarationExpressionSubparser(),
                new IsExpressionSubparser(),
                new LiteralExpressionSubparser(),
                new MethodExpressionSubparser(),
                new NegateExpressionSubparser(),
                new NegativeExpressionSubparser(),
                new NotBitwiseExpressionSubparser(),
                new NumberExpressionSubparser(),
                new OperationExpressionSubparser(),
                new SectionExpressionSubparser(),
                new SequenceExpressionSubparser(),
                new StaticExpressionSubparser(),
                new VariableExpressionSubparser()
        );
    }

    public static ExpressionSubparsers createExpressionSubparsers() {
        return new ExpressionSubparsers(createSubparsers());
    }

}
