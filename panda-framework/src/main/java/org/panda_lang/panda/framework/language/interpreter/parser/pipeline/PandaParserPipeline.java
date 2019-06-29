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

package org.panda_lang.panda.framework.language.interpreter.parser.pipeline;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.interpreter.parser.Parser;
import org.panda_lang.panda.framework.design.interpreter.parser.Context;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserHandler;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserPipeline;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.utilities.commons.TimeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class PandaParserPipeline<P extends Parser> implements ParserPipeline<P> {

    private final ParserPipeline<P> parentPipeline;
    private final List<ParserRepresentation<P>> representations;
    private final Comparator<ParserRepresentation> comparator;
    private long handleTime;
    private int count;

    public PandaParserPipeline() {
        this(null);
    }

    public PandaParserPipeline(ParserPipeline<P> parentPipeline) {
        this.parentPipeline = parentPipeline;
        this.representations = new ArrayList<>();
        this.comparator = new ParserRepresentationComparator();
    }

    @Override
    public P handle(Context context, Snippet source) {
        if (count > 100) {
            count = 0;
            sort();
        }

        if (parentPipeline != null) {
            P parser = handle(context, source, parentPipeline.getRepresentations());

            if (parser != null) {
                return parser;
            }
        }

        return handle(context, source, representations);
    }

    private @Nullable P handle(Context context, Snippet source, Collection<? extends ParserRepresentation<P>> representations) {
        long currentTime = System.nanoTime();

        for (ParserRepresentation<P> representation : representations) {
            ParserHandler handler = representation.getHandler();

            if (handler.handle(context, source)) {
                representation.increaseUsages();

                long time = System.nanoTime() - currentTime;
                handleTime += time;

                if (TimeUtils.NANOSECOND * 10 < time) {
                    // System.out.println(TimeUtils.toMilliseconds(time) + "/" + TimeUtils.toMilliseconds(handleTime) + " by " + representation.getParser().getClass().getSimpleName());
                }

                count++;
                return representation.getParser();
            }
        }

        handleTime += (System.nanoTime() - currentTime);
        return null;
    }

    protected void sort() {
        representations.sort(comparator);
    }

    @Override
    public void registerParserRepresentation(ParserRepresentation<P> parserRepresentation) {
        representations.add(parserRepresentation);
        sort();
    }

    @Override
    public long getHandleTime() {
        return handleTime;
    }

    @Override
    public List<? extends ParserRepresentation<P>> getRepresentations() {
        return representations;
    }

}
