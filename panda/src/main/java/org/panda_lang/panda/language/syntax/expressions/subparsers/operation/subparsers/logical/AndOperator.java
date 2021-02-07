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

package org.panda_lang.panda.language.syntax.expressions.subparsers.operation.subparsers.logical;

import org.panda_lang.framework.architecture.expression.Expression;
import org.panda_lang.framework.architecture.module.TypeLoader;
import org.panda_lang.framework.architecture.type.signature.Signature;
import org.panda_lang.framework.runtime.ProcessStack;
import org.panda_lang.panda.language.syntax.expressions.subparsers.operation.rpn.RPNOperationAction;

public final class AndOperator extends OrOperation {

    @Override
    public RPNOperationAction<Boolean> of(TypeLoader typeLoader, Expression a, Expression b) {
        return new RPNOperationAction<Boolean>() {
            @Override
            public Boolean get(ProcessStack stack, Object instance) throws Exception {
                Boolean aValue = a.evaluate(stack, instance);

                if (!aValue) {
                    return false;
                }

                return b.evaluate(stack, instance);
            }

            @Override
            public Signature returnType(TypeLoader loader) {
                return AndOperator.super.requiredType(loader).getSignature();
            }
        };
    }
}
