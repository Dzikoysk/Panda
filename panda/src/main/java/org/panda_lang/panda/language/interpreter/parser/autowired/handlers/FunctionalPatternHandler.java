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

package org.panda_lang.panda.language.interpreter.parser.autowired.handlers;

import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.LocalChannel;
import org.panda_lang.language.interpreter.parser.pipeline.Handler;
import org.panda_lang.language.interpreter.pattern.functional.FunctionalData;
import org.panda_lang.language.interpreter.pattern.functional.FunctionalPattern;
import org.panda_lang.language.interpreter.pattern.functional.PatternData;
import org.panda_lang.language.interpreter.token.PandaSourceStream;
import org.panda_lang.language.interpreter.token.Snippet;

public final class FunctionalPatternHandler implements Handler {

    private final FunctionalPattern functionalPattern;

    public FunctionalPatternHandler(FunctionalPattern functionalPattern) {
        this.functionalPattern = functionalPattern;
    }

    @Override
    public Object handle(Context context, LocalChannel channel, Snippet source) {
        return functionalPattern
                .match(source, new PandaSourceStream(source), PatternData.capacity(1).with(FunctionalData.CONTEXT, context))
                .isMatched();
    }

}
