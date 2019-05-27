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

package org.panda_lang.panda.framework.design.interpreter.pattern.linear;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.utilities.commons.StringUtils;

final class WildcardElementCompiler implements LinearPatternElementCompiler {

    @Override
    public boolean handle(LinearPatternCompiler compiler, @Nullable String identifier, String content, boolean optional) {
        return content.startsWith("*");
    }

    @Override
    public LinearPatternElement compile(LinearPatternCompiler compiler, @Nullable String identifier, String content, boolean optional) {
        if (!content.contains("=")) {
            return new WildcardLinearPatternElement(WildcardLinearPatternElement.Type.DEFAULT, identifier, optional);
        }

        String data = StringUtils.splitFirst(content, "=")[1];

        if (data.equals("expression")) {
            return new WildcardLinearPatternElement(WildcardLinearPatternElement.Type.EXPRESSION, identifier, optional);
        }

        return null;
    }

}
