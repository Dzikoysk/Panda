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

package org.panda_lang.panda.language.runtime.expression;

import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.expression.ExpressionEvaluator;
import org.panda_lang.panda.framework.design.runtime.expression.ExpressionValueType;
import org.panda_lang.panda.framework.design.runtime.ProcessStack;

import java.security.InvalidParameterException;

public class PandaExpression implements Expression {

    private final ExpressionValueType type;
    private final ClassPrototype returnType;
    private final ExpressionEvaluator evaluator;
    private final Object value;

    public PandaExpression(ClassPrototype returnType, Object value) {
        this(ExpressionValueType.KNOWN, returnType, null, value);
    }

    public PandaExpression(DynamicExpression expression) {
        this(ExpressionValueType.UNKNOWN, expression.getReturnType(), expression, null);
    }

    protected PandaExpression(ExpressionValueType type, ClassPrototype returnType, ExpressionEvaluator evaluator, Object value) {
        if (type == null) {
            throw new InvalidParameterException("ExpressionType cannot be null");
        }

        this.type = type;
        this.returnType = returnType;
        this.evaluator = evaluator;
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object evaluate(ProcessStack stack, Object instance) {
        return type == ExpressionValueType.KNOWN ? value : evaluator.evaluate(stack, instance);
    }

    @Override
    public ClassPrototype getReturnType() {
        return returnType;
    }

    @Override
    public ExpressionValueType getType() {
        return type;
    }

    @Override
    public String toString() {
        String s = type.name() + ":" + (returnType != null ? returnType.getName() : "any");
        return ExpressionValueType.KNOWN == type ? s + ":" + value : s;
    }

}
