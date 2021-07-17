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

package panda.interpreter.parser.expression;

import panda.interpreter.parser.Contextual;
import panda.interpreter.token.TokenInfo;
import panda.utilities.collection.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class ExpressionParserWorker {

    public static final Map<String, Long> TIMES = new HashMap<>();
    private static final int NONE = -1;

    private final ExpressionParserSettings settings;
    private final List<SubparserRepresentation> subparsers;
    private final ExpressionSubparserWorker[] workers;
    private final Stack<ExpressionSubparserWorker> cachedWorkers = new Stack<>();
    private ExpressionResult error = null;
    private int previousSubparser = NONE;
    private int lastSucceededRead = 0;

    protected ExpressionParserWorker(ExpressionParserSettings settings, Contextual<?> context, List<SubparserRepresentation> subparsers) {
        this.settings = settings;
        this.subparsers = subparsers;
        this.workers = new ExpressionSubparserWorker[subparsers.size()];

        for (int index = 0; index < subparsers.size(); index++) {
            this.workers[index] = subparsers.get(index).getSubparser().createWorker(context.toContext());
        }
    }

    protected void finish(ExpressionContext<?> context) {
        for (ExpressionSubparserWorker worker : workers) {
            // skip removed subparsers
            if (!(worker instanceof ExpressionSubparserPostProcessor)) {
                continue;
            }

            ExpressionResult result = ((ExpressionSubparserPostProcessor) worker).finish(context);

            if (result != null && result.isPresent()) {
                context.getResults().push(result.get());
                cachedWorkers.push(worker);
                break;
            }
        }
    }

    protected boolean next(ExpressionContext<?> context, TokenInfo token) {
        int cachedSubparser = previousSubparser;

        // try to use cached subparser
        if (previousSubparser != NONE) {
            // cache and reset values
            previousSubparser = NONE;

            // return result from cached subparser
            if (this.next(cachedSubparser, context, token) && previousSubparser != NONE) {
                return true;
            }
        }

        for (int index = 0; index < workers.length; index++) {
            // skip cached subparser
            if (previousSubparser == index) {
                continue;
            }

            // return result from subparser
            if (this.next(index, context, token)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parse the next element from context using the subparser at the specified index
     *
     * @param context the context with data
     * @param index   the index of subparser in the array
     * @return true if the result was found using the specified subparser, otherwise false
     */
    private boolean next(int index, ExpressionContext<?> context, TokenInfo token) {
        ExpressionSubparserWorker worker = workers[index];

        // skip removed subparsers
        if (worker == null) {
            return false;
        }

        ExpressionSubparser subparser = worker.getSubparser();

        if (settings.isMutualDisabled() && subparser.type() == ExpressionSubparserType.MUTUAL) {
            return false;
        }

        // skip subparser that does not meet assumptions
        if (subparser.minimalRequiredLengthOfSource() > context.getSynchronizedSource().getAmountOfAvailableSource() + 1) {
            return false;
        }

        // skip individual subparser if there's some content
        if (subparser.type() == ExpressionSubparserType.INDIVIDUAL && context.hasResults()) {
            return false;
        }

        long time = System.nanoTime();
        int cachedIndex = context.getSynchronizedSource().getIndex();

        ExpressionResult result = worker.next(context, token);
        Maps.update(TIMES, subparser.name(), () -> 0L, cachedTime -> cachedTime + (System.nanoTime() - time));

        // if something went wrong
        if (result == null || result.containsError()) {
            context.getSynchronizedSource().setIndex(cachedIndex);

            // do not override previous error
            if (result != null && error == null) {
                this.error = result;
            }

            return false;
        }

        // cache current subparser
        previousSubparser = index;

        // not yet
        if (result.isEmpty()) {
            return true;
        }

        // increase usage
        subparsers.get(index).increaseUsages();
        cachedWorkers.push(worker);

        // only one expr on stack
        if (context.hasResults()) {
            context.getSynchronizedSource().setIndex(cachedIndex);
            return false;
        }

        // save the result
        context.getResults().push(result.get());
        // cleanup cache, move the index
        this.lastSucceededRead = context.getSynchronizedSource().getIndex();

        if (error != null && !(subparser instanceof PartialResultSubparser)) {
            context.getErrors().push(error);
            this.error = null;
        }

        return true;
    }

    public boolean hasError() {
        return getError() != null;
    }

    public ExpressionResult getError() {
        return error;
    }

    public ExpressionCategory getLastCategory() {
        return cachedWorkers.peek().getSubparser().category();
    }

    public int getLastSucceededRead() {
        return lastSucceededRead;
    }

}
