/*
 * Copyright (c) 2020 Dzikoysk
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

package org.panda_lang.language.architecture.type.member.parameter;

import org.panda_lang.language.architecture.statement.PandaVariable;
import org.panda_lang.language.architecture.type.Signature;

public final class PropertyParameterImpl extends PandaVariable implements PropertyParameter {

    private final boolean varargs;

    public PropertyParameterImpl(int parameterIndex, Signature signature, String name, boolean varargs, boolean mutable, boolean nillable) {
        super(parameterIndex, signature, name, mutable, nillable);
        this.varargs = varargs;
    }

    public PropertyParameterImpl(int parameterIndex, Signature signature, String name) {
        this(parameterIndex, signature, name, false, false, false);
    }

    @Override
    public boolean isVarargs() {
        return varargs;
    }

}