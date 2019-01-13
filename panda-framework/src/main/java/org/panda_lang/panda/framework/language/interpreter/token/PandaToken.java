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

package org.panda_lang.panda.framework.language.interpreter.token;

import org.panda_lang.panda.framework.design.interpreter.token.TokenType;

public class PandaToken extends EqualableToken {

    private final TokenType type;
    private final String name;
    private final String value;

    public PandaToken(TokenType type, String value) {
        this(type, type.getTypeName(), value);
    }

    public PandaToken(TokenType type, String name, String value) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTokenValue() {
        return value;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getTokenValue();
    }

}
