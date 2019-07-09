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

package org.panda_lang.panda.framework.language.resource.prototypes;

import org.panda_lang.panda.framework.design.architecture.prototype.PrototypeVisibility;
import org.panda_lang.panda.framework.design.architecture.value.Value;
import org.panda_lang.panda.framework.design.resource.prototypes.model.ClassPrototypeModel;
import org.panda_lang.panda.framework.design.resource.prototypes.model.ClassPrototypeModel.ClassDeclaration;
import org.panda_lang.panda.framework.design.resource.prototypes.model.ClassPrototypeModel.ModuleDeclaration;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;

import java.security.InvalidParameterException;

@ClassDeclaration("Consolex")
@ModuleDeclaration("panda-lang")
public class ConsolePrototype implements ClassPrototypeModel {

    @MethodDeclaration(visibility = PrototypeVisibility.PUBLIC, isStatic = true, catchAllParameters = true)
    public static void print(Flow flow, System instance, @TypeDeclaration("panda-lang:Array<Object>") Value[] parameters) {
        StringBuilder node = new StringBuilder();

        if (parameters.length == 0) {
            throw new InvalidParameterException("Values are not specified");
        }

        for (Value value : parameters) {
            node.append(value == null ? "null" : value.getObject());
            node.append(", ");
        }

        String message = node.substring(0, node.length() - 2);
        System.out.println(message);
    }

}
