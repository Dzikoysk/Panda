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

package org.panda_lang.framework.design.interpreter.pattern.custom.elements;

import org.panda_lang.framework.design.interpreter.pattern.custom.AbstractCustomPatternElement;
import org.panda_lang.framework.design.interpreter.pattern.custom.CustomPatternElement;
import org.panda_lang.framework.design.interpreter.token.TokenRepresentation;

public final class WildcardElement extends AbstractCustomPatternElement {

    private WildcardElement(WildcardElementBuilder builder) {
        super(builder);
    }

    public static WildcardElementBuilder create(String id) {
        return new WildcardElementBuilder(id);
    }

    public static class WildcardElementBuilder extends AbstractCustomPatternElementBuilder<TokenRepresentation, WildcardElementBuilder> {

        protected WildcardElementBuilder(String id) {
            super(id);
            super.custom(((source, current) -> current));
        }

        @Override
        public CustomPatternElement build() {
            return new WildcardElement(this);
        }

    }

}
