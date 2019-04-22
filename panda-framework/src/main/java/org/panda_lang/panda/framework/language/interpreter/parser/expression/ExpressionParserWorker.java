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

package org.panda_lang.panda.framework.language.interpreter.parser.expression;

import org.panda_lang.panda.framework.design.interpreter.parser.expression.ExpressionParser;
import org.panda_lang.panda.framework.design.interpreter.parser.expression.ExpressionParserSettings;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;

import java.util.Collection;
import java.util.Stack;

public class ExpressionParserWorker {

    private static final int NONE = -1;

    private final ExpressionSubparserWorker[] subparsers;
    private Stack<ExpressionSubparserWorker> workers = new Stack<>();
    private ExpressionResult<Expression> error = null;
    private int previousSubparser = NONE;
    private int lastSucceededRead = 0;

    protected ExpressionParserWorker(ExpressionParser parser, ExpressionContext context, SourceStream source, Collection<ExpressionSubparserRepresentation> subparsers, ExpressionParserSettings settings) {
        this.subparsers = subparsers.stream()
                .filter(subparser -> settings.isCombined() || subparser.getSubparser().getType() != ExpressionType.COMBINED)
                .filter(subparser -> !settings.isStandaloneOnly() || subparser.getSubparser().hasStandaloneSupport())
                .map(subparser -> {
                    ExpressionSubparserWorker worker = subparser.getSubparser().createWorker();

                    if (worker == null) {
                        throw new ExpressionParserException(subparser.getClass() + ": null worker", context, source);
                    }

                    return worker.withSubparser(subparser);
                })
                .toArray(ExpressionSubparserWorker[]::new);

        // System.out.println(Arrays.toString(Arrays.stream(this.subparsers).map(subparser -> subparser.getSubparser().getSubparserName()).toArray()));
    }

    protected void finish(ExpressionContext context) {
        for (ExpressionSubparserWorker worker : subparsers) {
            // skip removed subparsers
            if (worker == null || worker.getSubparserRepresentation().getSubparser().getSubparserType() != ExpressionSubparserType.MUTUAL) {
                continue;
            }

            ExpressionResult<Expression> result = worker.finish(context);

            if (result != null && result.isPresent()) {
                context.getResults().push(result.get());
                break;
            }
        }
    }

    protected boolean next(ExpressionContext context) {
        int cachedSubparser = previousSubparser;

        // try to use cached subparser
        if (previousSubparser != NONE) {
            // cache and reset values
            previousSubparser = NONE;

            // return result from cached subparser
            if (this.next(context, cachedSubparser) && previousSubparser != NONE) {
                return true;
            }
        }

        for (int index = 0; index < subparsers.length; index++) {
            // skip cached subparser
            if (previousSubparser == index) {
                continue;
            }

            // return result from subparser
            if (this.next(context, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parse the next element from context using the subparser at the specified index
     *
     * @param context the context with data
     * @param index the index of subparser in the array
     * @return true if the result was found using the specified subparser, otherwise false
     */
    private boolean next(ExpressionContext context, int index) {
        ExpressionSubparserWorker worker = subparsers[index];

        // skip individual subparser if there's some content
        if (worker.getSubparserRepresentation().getSubparser().getSubparserType() == ExpressionSubparserType.INDIVIDUAL && !context.getResults().isEmpty()) {
            return false;
        }

        ExpressionResult<Expression> result = worker.next(context);

        // if something went wrong
        if (result == null || result.containsError()) {

            // do not override previous error
            if (result != null && error == null) {
                error = result;
            }

            return false;
        }

        previousSubparser = index;
        workers.push(worker);

        // not yet
        if (result.isEmpty()) {
            return true;
        }

        // save the result, cleanup cache, move the index
        context.getResults().push(result.get());
        lastSucceededRead = context.getDiffusedSource().getIndex();
        error = null;
        return true;
    }

    public int getPreviousSubparser() {
        return previousSubparser;
    }

    public Stack<ExpressionSubparserWorker> getWorkers() {
        return workers;
    }

    public boolean hasError() {
        return getError() != null;
    }

    public int getLastSucceededRead() {
        return lastSucceededRead;
    }

    public ExpressionResult<Expression> getError() {
        return error;
    }

    public ExpressionSubparserWorker[] getSubparsers() {
        return subparsers;
    }

}
