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

package org.panda_lang.utilities.commons.javassist;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.panda_lang.utilities.commons.StringUtils;
import org.panda_lang.utilities.commons.text.MessageFormatter;

public final class CtCode<T extends CtBehavior> {

    private final T behavior;
    private final MessageFormatter formatter = new MessageFormatter();

    private CtCode(T behavior) {
        this.behavior = behavior;
    }

    public CtCode<T> alias(String placeholder, String value) {
        formatter.register(placeholder, value);
        return this;
    }

    public T compile(String... lines) throws CannotCompileException {
        behavior.setBody("{ " + formatter.format(StringUtils.join(lines)) + " }");
        return behavior;
    }

    public static <T extends CtBehavior> CtCode<T> of(T behavior) {
        return new CtCode<>(behavior);
    }

}
