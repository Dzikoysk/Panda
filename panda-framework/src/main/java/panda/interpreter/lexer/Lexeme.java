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

package panda.interpreter.lexer;

import panda.interpreter.source.Location;
import panda.interpreter.token.Token;

/**
 * The smallest unit of lexical meaning
 */
public interface Lexeme {

    /**
     * Get associated predefined token
     *
     * @return the associated token
     */
    Token getAssociatedToken();

    /**
     * Location of lexeme in source
     *
     * @return the location in source
     */
    Location getLocation();

    /**
     * Get value of token
     *
     * @return get
     */
    String getValue();

}
