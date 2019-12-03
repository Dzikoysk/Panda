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

import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.prototype.Prototype;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.design.architecture.prototype.State;
import org.panda_lang.framework.design.architecture.prototype.Visibility;
import org.panda_lang.framework.language.architecture.prototype.generator.PrototypeGeneratorManager;
import org.panda_lang.framework.language.interpreter.source.PandaClassSource;
import org.panda_lang.utilities.commons.ClassUtils;

import java.util.Collection;

public final class PandaPrototypeUtils {

    private PandaPrototypeUtils() { }

    public static Reference of(Module module, Class<?> type, String name) {
        return module.add(PandaPrototype.builder()
                .name(name)
                .module(module)
                .associated(type)
                .visibility(Visibility.PUBLIC)
                .state(State.DEFAULT)
                .type(type.isInterface() ? "interface" : "class")
                .location(new PandaClassSource(type).toLocation())
                .build()
        ).toReference();
    }

    public static Reference generateOf(Module module, Class<?> type, String name) {
        return PrototypeGeneratorManager.getInstance().generate(module, type, name);
    }

    public static boolean hasCommonPrototypes(Collection<? extends Prototype> fromPrototypes, Collection<? extends Prototype> toPrototypes) {
        for (Prototype from : fromPrototypes) {
            for (Prototype to : toPrototypes) {
                if (from.equals(to)) {
                    return true;
                }

                if (isAssignableFrom(from.getAssociatedClass(), to.getAssociatedClass())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isAssignableFrom(Class<?> from, Class<?> to) {
        return from != null && to != null && (from == to || ClassUtils.isAssignableFrom(from, to));
    }

}
