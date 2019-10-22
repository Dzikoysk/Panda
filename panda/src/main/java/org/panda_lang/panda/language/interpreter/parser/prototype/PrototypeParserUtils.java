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

package org.panda_lang.panda.language.interpreter.parser.prototype;

import org.panda_lang.framework.design.architecture.prototype.Prototype;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.design.interpreter.parser.Components;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.design.interpreter.token.Snippet;
import org.panda_lang.framework.design.interpreter.token.Snippetable;
import org.panda_lang.framework.language.architecture.prototype.StateComparator;
import org.panda_lang.framework.language.architecture.prototype.TypeDeclarationUtils;
import org.panda_lang.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.framework.language.interpreter.token.SynchronizedSource;
import org.panda_lang.framework.language.resource.syntax.separator.Separators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

final class PrototypeParserUtils {

    private PrototypeParserUtils() { }

    public static Collection<Snippetable> readTypes(SynchronizedSource source) {
        Collection<Snippetable> types = new ArrayList<>(1);

        while (source.hasNext()) {
            if (!types.isEmpty()) {
                if (!source.getNext().equals(Separators.COMMA)) {
                    break;
                }

                source.next();
            }

            Optional<Snippet> type = TypeDeclarationUtils.readType(source);

            if (!type.isPresent()) {
                break;
            }

            types.add(type.get());
        }

        return types;
    }

    public static void appendExtended(Context context, Prototype prototype, Snippetable typeSource) {
        String name = typeSource.toString();
        Optional<Reference> extendedPrototype = context.getComponent(Components.IMPORTS).forName(name);

        if (extendedPrototype.isPresent()) {
            StateComparator.requireInheritance(context, extendedPrototype.get(), typeSource);
            prototype.addBase(extendedPrototype.get());
            return;
        }

        throw new PandaParserFailure(context, typeSource,
                "Type " + name + " not found",
                "Make sure that the name does not have a typo and module which should contain that class is imported"
        );
    }

}
