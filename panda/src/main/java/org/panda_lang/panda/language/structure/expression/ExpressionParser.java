/*
 * Copyright (c) 2015-2017 Dzikoysk
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

package org.panda_lang.panda.language.structure.expression;

import org.panda_lang.framework.interpreter.lexer.token.Token;
import org.panda_lang.framework.interpreter.lexer.token.TokenType;
import org.panda_lang.framework.interpreter.lexer.token.TokenUtils;
import org.panda_lang.framework.interpreter.lexer.token.TokenizedSource;
import org.panda_lang.framework.interpreter.lexer.token.reader.TokenReader;
import org.panda_lang.framework.interpreter.parser.Parser;
import org.panda_lang.framework.interpreter.parser.ParserInfo;
import org.panda_lang.panda.implementation.interpreter.lexer.token.reader.PandaTokenReader;
import org.panda_lang.panda.implementation.interpreter.parser.PandaParserException;
import org.panda_lang.panda.implementation.structure.value.PandaValue;
import org.panda_lang.panda.implementation.structure.value.Variable;
import org.panda_lang.panda.implementation.structure.wrapper.Scope;
import org.panda_lang.panda.language.structure.expression.callbacks.GetVariableExpressionCallback;
import org.panda_lang.panda.language.structure.group.GroupRegistry;
import org.panda_lang.panda.language.structure.variable.VariableParserUtils;

public class ExpressionParser implements Parser {

    public Expression parse(ParserInfo info, TokenizedSource expressionSource) {
        if (expressionSource.size() == 1) {
            Token token = expressionSource.getToken(0);
            String value = token.getTokenValue();

            if (token.getType() == TokenType.LITERAL) {
                switch (token.getTokenValue()) {
                    case "null":
                        return new Expression(new PandaValue(null, null));
                    case "true":
                        return toSimpleKnownExpression("panda.lang:Boolean", true);
                    case "false":
                        return toSimpleKnownExpression("panda.lang:Boolean", false);
                    default:
                        throw new PandaParserException("Unknown literal: " + token);
                }
            }

            if (token.getType() == TokenType.SEQUENCE) {
                switch (token.getName()) {
                    case "String":
                        return toSimpleKnownExpression("panda.lang:String", value);
                    default:
                        throw new PandaParserException("Unknown sequence: " + token);
                }
            }

            if (ExpressionUtils.isNumber(value)) {
                return toSimpleKnownExpression("panda.lang:Integer", Integer.parseInt(value));
            }

            Scope scope = info.getComponent("scope");
            Variable variable = VariableParserUtils.getVariable(scope, value);

            if (variable != null) {
                int memoryIndex = VariableParserUtils.indexOf(scope, variable);
                return new Expression(variable.getVariableType(), new GetVariableExpressionCallback(memoryIndex));
            }
        }
        else if (TokenUtils.equals(expressionSource.get(0), TokenType.KEYWORD, "new")) {
            TokenReader reader = new PandaTokenReader(expressionSource);

        }

        return null;
    }

    private Expression toSimpleKnownExpression(String forName, Object value) {
        return new Expression(new PandaValue(GroupRegistry.forName(forName), value));
    }

}
