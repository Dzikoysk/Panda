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

package org.panda_lang.framework.language.interpreter.token;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.interpreter.token.Token;
import org.panda_lang.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.framework.design.interpreter.token.TokenType;
import org.panda_lang.utilities.commons.ArrayUtils;

import java.util.Comparator;

public final class TokenUtils {

    public static final Comparator<Token> TOKEN_ORDER_COMPARATOR = (a, b) -> Integer.compare(b.getValue().length(), a.getValue().length());

    private TokenUtils() { }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static boolean hasName(Token token, String name) {
        return token.hasName() && token.getName().get().equals(name);
    }

    public static boolean contains(Token[] tokens, Token element) {
        for (Token token : tokens) {
            if (element.equals(token)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isTypeOf(TokenRepresentation representation, TokenType type) {
        return type.equals(representation.getToken().getType());
    }

    public static TokenRepresentation[] toPseudoRepresentations(Token... tokens) {
        TokenRepresentation[] representations = new TokenRepresentation[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            representations[i] = PandaTokenRepresentation.of(tokens[i]);
        }

        return representations;
    }

    public static boolean contentEquals(@Nullable TokenRepresentation representation, Token... tokens) {
        if (representation == null) {
            return ArrayUtils.contains(tokens, null);
        }

        for (Token token : tokens) {
            if (representation.contentEquals(token)) {
                return true;
            }
        }

        return false;
    }

    public static boolean valueEquals(Token token, String... contents) {
        for (String content : contents) {
            if (token.getValue().equals(content)) {
                return true;
            }
        }

        return false;
    }

}
