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

package org.panda_lang.panda.framework.design.resource.parsers.expression.fixed;

import org.panda_lang.panda.framework.design.interpreter.parser.Parser;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.Tokens;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.interpreter.token.stream.PandaSourceStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExpressionParser implements Parser {

    private final Collection<ExpressionSubparser> representations = new ArrayList<>();

    public ExpressionParser(Collection<ExpressionSubparser> representations) {
        if (representations.size() < 2) {
            throw new IllegalArgumentException("Expression parser does not support less than 2 subparsers");
        }

        this.representations.addAll(representations);
    }

    public Expression parse(ParserData data, Tokens source) {
        return parse(data, new PandaSourceStream(source));
    }

    public Expression parse(ParserData data, SourceStream source) {
        Stack<Object> parsed = new Stack<>();

        Collection<ExpressionSubparserWorker> subparsers = representations.stream()
                .map(ExpressionSubparser::createSubparser)
                .collect(Collectors.toList());

        for (TokenRepresentation representation : source.toTokenReader()) {
            Collection<ExpressionSubparserWorker> excluded = new ArrayList<>(subparsers.size() / 2);

            for (ExpressionSubparserWorker subparser : subparsers) {
                boolean result = subparser.next(data, representation);

                if (!result) {
                    excluded.add(subparser);
                }
            }

            if (subparsers.size() == 1 && excluded.size() == 1) {
                break;
            }

            subparsers.removeAll(excluded);
        }

        if (subparsers.isEmpty() || subparsers.size() > 1) {
            return null;
        }

        return subparsers.iterator().next().parse(data);
    }

}
