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

import org.panda_lang.framework.PandaFrameworkException;
import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.prototype.Prototype;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.design.architecture.prototype.Visibility;
import org.panda_lang.framework.language.architecture.prototype.PandaPrototype;
import org.panda_lang.framework.language.interpreter.source.PandaClassSource;

import java.util.Optional;

public final class ArrayPrototype extends PandaPrototype {

    private final Prototype prototype;

    public ArrayPrototype(Module module, Class<?> associated, Prototype type) {
        super(module, associated.getSimpleName(), new PandaClassSource(associated).toLocation(), associated, type.getType(), type.getState(), Visibility.PUBLIC);

        Optional<Reference> arrayBaseReference = module.getModuleLoader().forClass(PandaArray.class);

        if (!arrayBaseReference.isPresent()) {
            throw new PandaFrameworkException("Cannot find array base reference");
        }

        super.addBase(arrayBaseReference.get().fetch());
        this.prototype = type;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public Prototype getArrayType() {
        return prototype;
    }

}
