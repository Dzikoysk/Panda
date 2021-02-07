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

package org.panda_lang.panda.language.syntax.expressions.subparsers;

import org.panda_lang.framework.architecture.dynamic.accessor.Accessor;
import org.panda_lang.framework.architecture.dynamic.accessor.AccessorExpression;
import org.panda_lang.framework.architecture.expression.DynamicExpression;
import org.panda_lang.framework.architecture.expression.Expression;
import org.panda_lang.framework.architecture.statement.Variable;
import org.panda_lang.framework.architecture.type.signature.Signature;
import org.panda_lang.framework.runtime.ProcessStack;
import org.panda_lang.framework.architecture.statement.VariableAccessor;

final class VariableExpression implements DynamicExpression {

    private final Accessor<?> accessor;

    public VariableExpression(Variable variable) {
        this(new VariableAccessor(variable));
    }

    public VariableExpression(Accessor<?> accessor) {
        this.accessor = accessor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object evaluate(ProcessStack stack, Object instance) throws Exception {
        return accessor.getValue(stack, instance);
    }

    @Override
    public Signature getReturnType() {
        return accessor.getSignature();
    }

    @Override
    public Expression toExpression() {
        return new AccessorExpression(accessor, this);
    }

}
