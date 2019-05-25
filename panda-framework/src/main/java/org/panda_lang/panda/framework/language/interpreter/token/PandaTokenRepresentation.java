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

import org.panda_lang.panda.framework.design.interpreter.token.Token;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;

import java.security.InvalidParameterException;
import java.util.Objects;

public class PandaTokenRepresentation implements TokenRepresentation {

    private static final int UNKNOWN_INDEX = -2;

    private final Token token;
    private final int line;
    private final int position;

    public PandaTokenRepresentation(Token token, int line, int position) {
        if (token == null) {
            throw new InvalidParameterException("Token cannot be null");
        }

        this.token = token;
        this.line = line;
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public boolean equals(Object to) {
        if (this == to) {
            return true;
        }

        if (to == null || getClass() != to.getClass()) {
            return false;
        }

        PandaTokenRepresentation that = (PandaTokenRepresentation) to;
        return getToken().equals(that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken());
    }

    @Override
    public String toString() {
        return token.getValue();
    }

    public static TokenRepresentation of(TokenType type, String value) {
        return of(new PandaToken(type, value));
    }

    public static TokenRepresentation of(Token token) {
        return new PandaTokenRepresentation(token, UNKNOWN_INDEX, UNKNOWN_INDEX);
    }

}
