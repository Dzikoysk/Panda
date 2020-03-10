/*
 * Copyright (c) 2015-2020 Dzikoysk
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

package org.panda_lang.framework.language.architecture.type;

import org.panda_lang.framework.design.architecture.type.TypeConstructor;
import org.panda_lang.framework.language.architecture.type.utils.ParameterUtils;

public final class PandaConstructor extends AbstractExecutableProperty implements TypeConstructor {

    private PandaConstructor(PandaParametrizedExecutableBuilder<?> builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return getType().getName() + "(" + ParameterUtils.toString(getParameters()) + ")";
    }

    public static PandaConstructorBuilder builder() {
        return new PandaConstructorBuilder().name("constructor");
    }

    public static final class PandaConstructorBuilder extends PandaParametrizedExecutableBuilder<PandaConstructorBuilder> {

        private PandaConstructorBuilder() { }

        public PandaConstructor build() {
            return new PandaConstructor(this);
        }

    }

}
