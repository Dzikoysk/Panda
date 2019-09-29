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

package org.panda_lang.framework.language.architecture.prototype.array;

import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.prototype.PrototypeReference;
import org.panda_lang.framework.language.architecture.prototype.PandaPrototype;
import org.panda_lang.framework.language.resource.PandaTypes;

public class ArrayPrototype extends PandaPrototype {

    private final PrototypeReference type;

    public ArrayPrototype(Module module, Class<?> associated, PrototypeReference type) {
        super(module, associated.getSimpleName(), associated);

        this.type = type;
        super.extended.add(PandaTypes.ARRAY.getReference());
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public PrototypeReference getType() {
        return type;
    }

}