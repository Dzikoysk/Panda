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

package org.panda_lang.utilities.commons.console;

import org.panda_lang.utilities.commons.StringUtils;

public class Colored {

    private final String text;
    private String prefix;

    private Colored(String text) {
        this.text = text;
        this.prefix = StringUtils.EMPTY;
    }

    public Colored effect(Effect effect) {
        this.prefix += effect.toString();
        return this;
    }

    @Override
    public String toString() {
        return prefix + text + Effect.RESET;
    }

    public static Colored on(Object text) {
        return new Colored(text.toString());
    }

}
