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

package org.panda_lang.language.interpreter.parser.expression;

import java.util.Collection;

/**
 * Collection of {@link org.panda_lang.language.interpreter.parser.expression.ExpressionSubparser}
 */
public class ExpressionSubparsers {

    private final Collection<ExpressionSubparser> subparsers;


    public ExpressionSubparsers(Collection<ExpressionSubparser> subparsers) {
        this.subparsers = subparsers;
    }

    /**
     * Get collection of subparsers
     *
     * @return the collection of subparsers
     */
    public Collection<? extends ExpressionSubparser> getSubparsers() {
        return subparsers;
    }

    /**
     * Create default {@link org.panda_lang.language.interpreter.parser.expression.ExpressionParser} using the current subparsers
     *
     * @return a new instance of expression parser
     */
    public ExpressionParser toParser() {
        return new PandaExpressionParser(this);
    }

}
