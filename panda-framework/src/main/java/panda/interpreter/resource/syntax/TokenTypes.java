/*
 * Copyright (c) 2021 dzikoysk
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

package panda.interpreter.resource.syntax;

import panda.interpreter.token.TokenType;

import java.util.ArrayList;
import java.util.Collection;

import static panda.utilities.collection.Lists.add;

/**
 * Basic collection of token types
 */
public final class TokenTypes {

    /**
     * Collected types
     */
    private static final Collection<TokenType> VALUES = new ArrayList<>();

    /**
     * Represents {@link panda.interpreter.resource.syntax.separator.Separator}
     */
    public static final TokenType SECTION = add(VALUES, new TokenType("SECTION"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.auxiliary.Identifier}
     */
    public static final TokenType IDENTIFIER = add(VALUES, new TokenType("IDENTIFIER"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.literal.Literal}
     */
    public static final TokenType LITERAL = add(VALUES, new TokenType("LITERAL"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.keyword.Keyword}
     */
    public static final TokenType KEYWORD = add(VALUES, new TokenType("KEYWORD"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.separator.Separator}
     */
    public static final TokenType SEPARATOR = add(VALUES, new TokenType("SEPARATOR"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.sequence.Sequence}
     */
    public static final TokenType SEQUENCE = add(VALUES, new TokenType("SEQUENCE"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.operator.Operator}
     */
    public static final TokenType OPERATOR = add(VALUES, new TokenType("OPERATOR"));
    /**
     * Represents {@link panda.interpreter.resource.syntax.operator.Operator}
     */
    public static final TokenType INDENTATION = add(VALUES, new TokenType("INDENTATION"));
    /**
     * Represents random {@link panda.interpreter.token.PandaToken}
     */
    public static final TokenType UNKNOWN = add(VALUES, new TokenType("UNKNOWN"));

    private TokenTypes() { }

    /**
     * Get all defined in the class types
     *
     * @return the collection of defined types
     */
    public static Collection<? extends TokenType> values() {
        return new ArrayList<>(VALUES);
    }

}
