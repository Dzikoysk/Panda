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

package org.panda_lang.framework.design.architecture.module;

import org.panda_lang.framework.design.architecture.type.Type;

import java.util.Collection;
import java.util.Map;

/**
 * Custom implementation of map to store types with support for associated classes and {@link org.panda_lang.framework.design.architecture.module.ModuleResource}
 */
public interface TypesMap extends Map<String, Type>, ModuleResource {

    /**
     * Add reference to map
     *
     * @param type the reference to add
     * @return false if a name or type is already stored, otherwise true
     */
    boolean put(Type type);

    /**
     * Count used types
     *
     * @return the amount of used types
     */
    long countUsedTypes();

    /**
     * Get collection of entries that contains types
     *
     * @return the collection of types
     */
    Collection<Entry<String, Type>> getTypes();

}
