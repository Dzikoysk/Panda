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

package org.panda_lang.framework.language.interpreter.pattern.custom.elements;

import org.panda_lang.framework.design.interpreter.token.Snippet;
import org.panda_lang.framework.design.interpreter.token.Snippetable;
import org.panda_lang.framework.language.architecture.prototype.utils.TypeDeclarationUtils;
import org.panda_lang.framework.language.interpreter.pattern.custom.CustomPatternElementBuilder;

import java.util.Optional;

public final class TypeElement extends CustomPatternElementBuilder<Snippetable, TypeElement> {

    private TypeElement(String id) {
        super(id);
    }

    public static TypeElement create(String id) {
        return new TypeElement(id).custom((data, source) -> {
            Optional<Snippet> type = TypeDeclarationUtils.readType(source.getAvailableSource());

            if (!type.isPresent()) {
                return null;
            }

            source.next(type.get().size());
            return type.get();
        });
    }

}
