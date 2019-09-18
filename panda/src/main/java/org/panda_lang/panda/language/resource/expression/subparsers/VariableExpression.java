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

package org.panda_lang.panda.language.resource.expression.subparsers;

import org.panda_lang.panda.language.architecture.dynamic.accessor.Accessor;
import org.panda_lang.panda.language.architecture.dynamic.accessor.AccessorExpression;
import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.framework.design.architecture.statement.Variable;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.ProcessStack;
import org.panda_lang.panda.language.resource.expression.subparsers.assignation.variable.VariableAccessor;
import org.panda_lang.panda.language.runtime.expression.DynamicExpression;

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
    public Object evaluate(ProcessStack stack, Object instance) {
        return accessor.getValue(stack, instance);
    }

    @Override
    public ClassPrototype getReturnType() {
        return accessor.getTypeReference().fetch();
    }

    @Override
    public Expression toExpression() {
        return new AccessorExpression(accessor, this);
    }

}
