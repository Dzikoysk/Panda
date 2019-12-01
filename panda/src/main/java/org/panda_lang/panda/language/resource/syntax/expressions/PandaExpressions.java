/*
 * Copyright (c) 2015-2019 Dzikoysk
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

import org.panda_lang.framework.design.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.framework.language.interpreter.parser.PandaParsersUtils;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.ArrayValueExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.CastExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.ConstructorExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.CreaseExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.IsExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.LiteralExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.MethodExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.NegateExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.NumberExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.OperationExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.SectionExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.SequenceExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.StaticExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.VariableExpressionSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationExpressionSubparser;

public final class PandaExpressions {

    /**
     * Array of default expression subparsers
     */
    public static final Class<? extends ExpressionSubparser>[] SUBPARSERS = PandaParsersUtils.of(
            ArrayValueExpressionSubparser.class,
            AssignationExpressionSubparser.class,
            CastExpressionSubparser.class,
            ConstructorExpressionSubparser.class,
            CreaseExpressionSubparser.class,
            IsExpressionSubparser.class,
            LiteralExpressionSubparser.class,
            MethodExpressionSubparser.class,
            NegateExpressionSubparser.class,
            NumberExpressionSubparser.class,
            OperationExpressionSubparser.class,
            SectionExpressionSubparser.class,
            SequenceExpressionSubparser.class,
            StaticExpressionSubparser.class,
            VariableExpressionSubparser.class
    );

}
