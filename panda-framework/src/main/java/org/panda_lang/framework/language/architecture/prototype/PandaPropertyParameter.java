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

package org.panda_lang.framework.language.architecture.prototype;

import org.panda_lang.framework.design.architecture.prototype.PropertyParameter;
import org.panda_lang.framework.design.architecture.prototype.Referencable;
import org.panda_lang.framework.language.architecture.statement.PandaVariable;

public final class PandaPropertyParameter extends PandaVariable implements PropertyParameter {

    private final boolean varargs;

    public PandaPropertyParameter(int parameterIndex, Referencable referencable, String name, boolean varargs, boolean mutable, boolean nillable) {
        super(parameterIndex, referencable.toReference(), name, mutable, nillable);
        this.varargs = varargs;
    }

    public PandaPropertyParameter(int parameterIndex, Referencable referencable, String name) {
        this(parameterIndex, referencable, name, false, false, false);
    }

    @Override
    public boolean isVarargs() {
        return varargs;
    }

}
