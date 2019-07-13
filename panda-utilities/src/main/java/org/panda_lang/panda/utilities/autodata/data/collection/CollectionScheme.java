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

package org.panda_lang.panda.utilities.autodata.data.collection;

import org.panda_lang.panda.utilities.autodata.data.entity.EntityScheme;

public class CollectionScheme {

    private final String name;
    private final EntityScheme entityScheme;

    CollectionScheme(String name, EntityScheme entityScheme) {
        this.name = name;
        this.entityScheme = entityScheme;
    }

    public EntityScheme getEntityScheme() {
        return entityScheme;
    }

    public String getName() {
        return name;
    }

    public static CollectionScheme of(DataCollectionStereotype collectionStereotype) {
        return new CollectionSchemeLoader().load(collectionStereotype);
    }

}
