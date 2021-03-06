/*
 * Copyright (c) 2021 dzikoysk
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

package panda.interpreter.syntax.expressions.subparsers.operation.subparsers;

import panda.interpreter.architecture.expression.Expression;
import panda.interpreter.parser.Context;
import panda.interpreter.resource.syntax.operator.Operator;
import panda.interpreter.resource.syntax.operator.Operators;
import panda.interpreter.syntax.expressions.subparsers.operation.Operation;
import panda.interpreter.syntax.expressions.subparsers.operation.OperationParser;
import panda.interpreter.syntax.expressions.subparsers.operation.OperationSubparser;
import panda.interpreter.syntax.expressions.subparsers.operation.rpn.RPNOperation;
import panda.interpreter.syntax.expressions.subparsers.operation.rpn.RPNOperationSupplier;
import panda.interpreter.syntax.expressions.subparsers.operation.subparsers.math.AdditionOperation;
import panda.interpreter.syntax.expressions.subparsers.operation.subparsers.math.DivisionOperation;
import panda.interpreter.syntax.expressions.subparsers.operation.subparsers.math.ModuloOperation;
import panda.interpreter.syntax.expressions.subparsers.operation.subparsers.math.MultiplicationOperation;
import panda.interpreter.syntax.expressions.subparsers.operation.subparsers.math.SubtractionOperation;
import panda.utilities.collection.Maps;

import java.util.Map;

public final class MathOperationSubparser implements OperationSubparser {

    private static final Map<Operator, Integer> PRIORITIES = Maps.of(
            Operators.ADDITION, 1,
            Operators.SUBTRACTION, 1,

            Operators.MULTIPLICATION, 2,
            Operators.DIVISION, 2,
            Operators.MODULO, 2
    );

    private static final Map<Operator, RPNOperationSupplier<?>> ACTIONS = Maps.of(
            Operators.ADDITION, new AdditionOperation(),
            Operators.SUBTRACTION, new SubtractionOperation(),

            Operators.MULTIPLICATION, new MultiplicationOperation(),
            Operators.DIVISION, new DivisionOperation(),
            Operators.MODULO, new ModuloOperation()
    );

    @Override
    public Expression parse(OperationParser parser, Context<?> context, Operation operation) {
        RPNOperation rpn = RPNOperation.builder()
                .withData(context)
                .withOperation(operation)
                .withPriorities(PRIORITIES)
                .withActions(ACTIONS)
                .build();

        return rpn.rectify();
    }

}
