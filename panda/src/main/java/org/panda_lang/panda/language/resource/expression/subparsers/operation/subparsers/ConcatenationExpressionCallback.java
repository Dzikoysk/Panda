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

package org.panda_lang.panda.language.resource.expression.subparsers.operation.subparsers;

import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.ProcessStack;
import org.panda_lang.panda.language.resource.PandaTypes;
import org.panda_lang.panda.language.runtime.expression.DynamicExpression;
import org.panda_lang.panda.utilities.commons.ObjectUtils;

import java.util.List;

public class ConcatenationExpressionCallback implements DynamicExpression {

    private final List<Expression> values;

    public ConcatenationExpressionCallback(List<Expression> values) {
        this.values = values;
    }

    @Override
    public <T> T call(ProcessStack stack, Object instance) {
        StringBuilder content = new StringBuilder();

        for (Expression value : values) {
            content.append(value.evaluate(stack, instance).toString());
        }

        return ObjectUtils.cast(content);
    }

    @Override
    public ClassPrototype getReturnType() {
        return PandaTypes.STRING;
    }

}