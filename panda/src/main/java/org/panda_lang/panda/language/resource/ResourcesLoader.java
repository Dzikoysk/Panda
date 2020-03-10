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

package org.panda_lang.panda.language.resource;

import org.atteo.classindex.ClassIndex;
import org.panda_lang.framework.PandaFrameworkException;
import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.module.ModulePath;
import org.panda_lang.framework.design.architecture.type.Type;
import org.panda_lang.framework.design.architecture.module.TypeLoader;
import org.panda_lang.framework.language.architecture.type.generator.TypeGeneratorManager;
import org.panda_lang.framework.language.resource.internal.InternalModuleInfo;
import org.panda_lang.framework.language.resource.internal.InternalModuleInfo.CustomInitializer;
import org.panda_lang.utilities.commons.StringUtils;

public final class ResourcesLoader {

    public void load(ModulePath modulePath, TypeLoader typeLoader) {
        for (Class<?> annotatedClass : ClassIndex.getAnnotated(InternalModuleInfo.class)) {
            try {
                load(modulePath, typeLoader, annotatedClass);
            } catch (Exception e) {
                throw new PandaFrameworkException("Cannot load internal module", e);
            }
        }
    }

    private void load(ModulePath modulePath, TypeLoader typeLoader, Class<?> annotatedClass) throws Exception {
        InternalModuleInfo moduleInfo = annotatedClass.getAnnotation(InternalModuleInfo.class);
        Module module = modulePath.allocate(moduleInfo.module());

        if (CustomInitializer.class.isAssignableFrom(annotatedClass)) {
            CustomInitializer initializer = (CustomInitializer) annotatedClass.newInstance();
            initializer.initialize(module);
        }

        String packageName = moduleInfo.pkg().isEmpty() ? StringUtils.EMPTY : moduleInfo.pkg() + ".";

        for (String name : moduleInfo.classes()) {
            Class<?> type = Class.forName(packageName + name);
            Type mappedType = TypeGeneratorManager.getInstance().generate(module, type.getSimpleName(), type);
            module.add(mappedType);
        }
    }

}
