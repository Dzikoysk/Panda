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

package org.panda_lang.panda.framework.language.resource.parsers.common;

import org.panda_lang.panda.framework.design.interpreter.parser.Parser;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserHandler;
import org.panda_lang.panda.framework.design.interpreter.token.Token;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.language.interpreter.token.PandaSnippet;

import java.util.ArrayList;
import java.util.List;

public class CommentParser implements Parser, ParserHandler {

    @Override
    public boolean handle(ParserData data, SourceStream source) {
        return isComment(source.read().getToken());
    }

    public static Snippet uncomment(Snippet source) {
        List<TokenRepresentation> uncommentedSource = new ArrayList<>(source.size());

        for (TokenRepresentation tokenRepresentation : source.getTokensRepresentations()) {
            Token token = tokenRepresentation.getToken();

            if (isComment(token)) {
                continue;
            }

            uncommentedSource.add(tokenRepresentation);
        }

        return new PandaSnippet(uncommentedSource);
    }

    private static boolean isComment(Token token) {
        return token.getType() == TokenType.SEQUENCE && token.getName().isPresent() && token.getName().get().equals("Comment");
    }

}
