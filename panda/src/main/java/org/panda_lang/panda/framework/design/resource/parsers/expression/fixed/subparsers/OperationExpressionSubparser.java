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

package org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionParser;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionResult;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparser;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparserType;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparserWorker;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.util.AbstractExpressionSubparserWorker;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.resource.parsers.expression.xxx.operation.Operation;
import org.panda_lang.panda.framework.language.resource.parsers.expression.xxx.operation.OperationParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OperationExpressionSubparser implements ExpressionSubparser {

    private static final OperationParser OPERATION_PARSER = new OperationParser();

    @Override
    public ExpressionSubparserWorker createSubparser() {
        return new OperationWorker().withSubparser(this);
    }

    @Override
    public ExpressionSubparserType getType() {
        return ExpressionSubparserType.MUTUAL;
    }

    static class OperationWorker extends AbstractExpressionSubparserWorker implements ExpressionSubparserWorker {

        private List<Operation.OperationElement> elements;

        @Override
        public @Nullable ExpressionResult<Expression> next(ExpressionParser parser, ParserData data, TokenRepresentation token, Stack<Expression> results) {
            if (results.isEmpty()) {
                return ExpressionResult.empty();
            }

            if (token.getType() != TokenType.OPERATOR) {
                if (elements != null) {
                    elements.add(new Operation.OperationElement(results.pop()));
                    return finish(parser, data, results);
                }

                return null;
            }

            if (elements == null) {
                this.elements = new ArrayList<>(3);
            }

            elements.add(new Operation.OperationElement(results.pop()));
            elements.add(new Operation.OperationElement(token));

            return ExpressionResult.empty();
        }

        @Override
        public @Nullable ExpressionResult<Expression> finish(ExpressionParser parser, ParserData data, Stack<Expression> results) {
            if (elements == null) {
                return null;
            }

            if (!results.isEmpty()) {
                elements.add(new Operation.OperationElement(results.pop()));
            }

            Operation operation = new Operation(elements);
            this.elements = null;

            return ExpressionResult.of(OPERATION_PARSER.parse(data, operation));
        }

    }

}
