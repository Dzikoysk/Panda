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

package org.panda_lang.panda.framework.design.resource.parsers.expression;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.component.UniversalComponents;
import org.panda_lang.panda.framework.design.interpreter.token.Tokens;
import org.panda_lang.panda.framework.design.interpreter.token.TokensUtils;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.panda.framework.language.interpreter.token.stream.PandaSourceStream;

public class ExpressionParser {

    private final ExpressionParser main;
    private final ExpressionSubparsers subparsers;

    public ExpressionParser(@Nullable ExpressionParser main, ExpressionSubparsers subparsers) {
        this.main = main == null ? this : main;
        this.subparsers = subparsers;
    }

    public @Nullable Expression parseSilently(ParserData data, Tokens tokens) {
        try {
            return parse(data, tokens);
        }
        catch (ExpressionParserException exception) {
            throw exception;
        }
        catch (Throwable throwable) {
            // mute, we don't want to catch any error that comes from ExpressionParser#parse method
            return null;
        }
    }

    public Expression parse(ParserData data, Tokens tokens) {
        return parse(data, new PandaSourceStream(tokens));
    }

    public Expression parse(ParserData data, SourceStream source) {
        Result result = readResult(source.toTokenizedSource());

        if (result == null) {
            throw new PandaExpressionFailure("Cannot read the specified source", data);
        }

        Expression expression = result.subparser.parse(main, data, result.source);

        if (expression == null) {
            ParserData errorData = data.fork().setComponent(UniversalComponents.SOURCE_STREAM, new PandaSourceStream(result.source));
            throw new PandaExpressionFailure("Cannot parse expression using " + result.subparser.getName() + " subparser", errorData);
        }

        if (source.getUnreadLength() != result.source.size()) {
            throw new PandaParserFailure("Unrecognized syntax", data.setComponent(UniversalComponents.SOURCE_STREAM, new PandaSourceStream(source.toTokenizedSource())));
        }

        return expression;
    }

    public @Nullable Tokens read(SourceStream source) {
        Tokens result = read(source.toTokenizedSource());

        if (result == null) {
            return null;
        }

        source.readDifference(result);
        return result;
    }

    public @Nullable Tokens read(Tokens source) {
        Result result = readResult(source);

        if (result == null) {
            return null;
        }

        if (TokensUtils.isEmpty(result.source)) {
            return null;
        }

        return result.source;
    }

    private @Nullable Result readResult(Tokens source) {
        if (subparsers.isEmpty()) {
            throw new ExpressionParserException("ExpressionParser does not contain any subparsers");
        }

        if (source.isEmpty()) {
            return null;
        }

        // create special group of subparsers to compare
        Result previousResult = null;

        for (ExpressionSubparser subparser : subparsers.getSubparsers()) {
            if (subparser.getMinimumLength() != -1 && source.size() < subparser.getMinimumLength()) {
                continue;
            }

            Tokens tokens = subparser.read(main, source);

            if (TokensUtils.isEmpty(tokens)) {
                continue;
            }

            if (previousResult == null || previousResult.source.size() < tokens.size()) {
                previousResult = new Result(subparser, new ExpressionTokens(tokens, subparser));
            }
        }

        return previousResult;
    }

    public ExpressionSubparsers getSubparsers() {
        return subparsers;
    }

    private final class Result {

        private final ExpressionSubparser subparser;
        private final Tokens source;

        public Result(ExpressionSubparser subparser, Tokens source) {
            this.subparser = subparser;
            this.source = source;
        }

    }

}
