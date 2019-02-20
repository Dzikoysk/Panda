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

package org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.subparsers;

import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparser;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparserWorker;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionUtils;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserException;
import org.panda_lang.panda.framework.language.resource.PandaTypes;
import org.panda_lang.panda.framework.language.resource.parsers.expression.xxx.callbacks.ThisExpressionCallback;
import org.panda_lang.panda.framework.language.resource.parsers.prototype.ClassPrototypeComponents;

public class LiteralExpressionSubparser implements ExpressionSubparser {

    @Override
    public ExpressionSubparserWorker createSubparser() {
        return new SequenceWorker();
    }

    static class SequenceWorker implements ExpressionSubparserWorker {

        private TokenRepresentation token;

        @Override
        public boolean next(ParserData data, TokenRepresentation representation) {
            if (token != null) {
                return false;
            }

            if (representation.getType() == TokenType.LITERAL) {
                token = representation;
            }

            return token != null;
        }

        @Override
        public Expression parse(ParserData data) {
            switch (token.getTokenValue()) {
                case "null":
                    return ExpressionUtils.toExpression(null, null);
                case "true":
                    return ExpressionUtils.toExpression(PandaTypes.BOOLEAN, true);
                case "false":
                    return ExpressionUtils.toExpression(PandaTypes.BOOLEAN, false);
                case "this":
                    return ThisExpressionCallback.asExpression(data.getComponent(ClassPrototypeComponents.CLASS_PROTOTYPE));
                default:
                    throw new PandaParserException("Unknown literal: " + token);
            }
        }

    }

}
