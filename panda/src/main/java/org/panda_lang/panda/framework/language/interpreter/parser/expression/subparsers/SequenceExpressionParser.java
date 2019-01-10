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

package org.panda_lang.panda.framework.language.interpreter.parser.expression.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;
import org.panda_lang.panda.framework.design.interpreter.token.Tokens;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.panda.framework.language.interpreter.parser.expression.ExpressionParser;
import org.panda_lang.panda.framework.language.interpreter.parser.expression.ExpressionSubparser;

public class SequenceExpressionParser implements ExpressionSubparser {

    @Override
    public @Nullable Tokens read(ExpressionParser main, Tokens source) {
        return SubparserUtils.readFirstOfType(source, TokenType.SEQUENCE);
    }

    @Override
    public Expression parse(ExpressionParser main, ParserData data, Tokens source) {
        TokenRepresentation token = source.get(0);

        if (token.getTokenName().equals("String")) {
            return toSimpleKnownExpression(data, "String", token.getTokenValue());
        }

        throw new PandaParserFailure("Unknown sequence: " + token, data);
    }

    @Override
    public double getPriority() {
        return DefaultSubparsers.Priorities.SINGLE;
    }

    @Override
    public String getName() {
        return DefaultSubparsers.Names.SEQUENCE;
    }

}
