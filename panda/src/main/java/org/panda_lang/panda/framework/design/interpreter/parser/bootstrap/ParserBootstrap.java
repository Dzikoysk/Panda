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

package org.panda_lang.panda.framework.design.interpreter.parser.bootstrap;

import org.panda_lang.panda.framework.design.interpreter.parser.Context;
import org.panda_lang.panda.framework.design.interpreter.parser.UnifiedParser;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserHandler;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;

public abstract class ParserBootstrap<T> implements UnifiedParser<T>, ParserHandler {

    protected ParserRepresentation<UnifiedParser<T>> parser;

    protected abstract BootstrapInitializer<T> initialize(Context context, BootstrapInitializer<T> initializer);

    public boolean customHandle(ParserHandler handler, Context context, Snippet source) {
        return handler.handle(context, source);
    }

    @Override
    public final boolean handle(Context context, Snippet source) {
        return customHandle(get(context).getHandler(), context, source);
    }

    @Override
    public final T parse(Context context) throws Exception {
        return get(context).getParser().parse(context);
    }

    protected ParserRepresentation<UnifiedParser<T>> get(Context context) {
        if (parser != null) {
            return parser;
        }

        this.parser = initialize(context, new BootstrapInitializer<T>().instance(this)).build(context);
        return parser;
    }

}