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

package org.panda_lang.panda.framework.design.architecture.prototype.field;

import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototypeReference;
import org.panda_lang.panda.framework.design.architecture.prototype.parameter.ParameterizedExecutable;
import org.panda_lang.panda.framework.design.architecture.value.Value;
import org.panda_lang.panda.framework.design.architecture.value.Variable;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;

public interface PrototypeField extends ParameterizedExecutable, Variable {

    void setDefaultValue(Expression defaultValue);

    void setStaticValue(Value staticValue);

    boolean isStatic();

    boolean isNative();

    boolean hasDefaultValue();

    Value getStaticValue();

    Expression getDefaultValue();

    int getFieldIndex();

    default ClassPrototypeReference getType() {
        return getReturnType();
    }

}
