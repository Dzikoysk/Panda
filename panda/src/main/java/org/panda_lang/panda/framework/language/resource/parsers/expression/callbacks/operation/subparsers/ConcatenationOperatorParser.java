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

package org.panda_lang.panda.framework.language.resource.parsers.expression.callbacks.operation.subparsers;

import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.expression.ExpressionCallback;
import org.panda_lang.panda.framework.language.resource.parsers.expression.callbacks.operation.Operation;
import org.panda_lang.panda.framework.language.resource.parsers.expression.callbacks.operation.OperationParser;

import java.util.List;
import java.util.stream.Collectors;

public class ConcatenationOperatorParser implements OperationParser {

    @Override
    public ExpressionCallback parse(ParserData data, Operation operation) {
        List<Expression> values = operation.getElements().stream()
                .filter(Operation.OperationElement::isExpression)
                .map(Operation.OperationElement::getExpression)
                .collect(Collectors.toList());

        return new ConcatenationExpressionCallback(values);
    }

}