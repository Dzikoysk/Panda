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

package org.panda_lang.panda.language.resource.syntax.scope;

import org.panda_lang.language.architecture.statement.Scope;
import org.panda_lang.language.architecture.statement.Variable;
import org.panda_lang.language.architecture.statement.VariableData;
import org.panda_lang.language.architecture.type.DynamicClass;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.LocalChannel;
import org.panda_lang.language.interpreter.parser.Parser;
import org.panda_lang.language.interpreter.parser.pipeline.PipelineComponent;
import org.panda_lang.language.interpreter.parser.pipeline.Pipelines;
import org.panda_lang.language.interpreter.source.Location;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.language.architecture.statement.PandaBlock;
import org.panda_lang.language.architecture.statement.PandaVariableDataInitializer;
import org.panda_lang.language.resource.syntax.keyword.Keywords;
import org.panda_lang.panda.language.interpreter.parser.ScopeParser;
import org.panda_lang.panda.language.interpreter.parser.context.BootstrapInitializer;
import org.panda_lang.panda.language.interpreter.parser.context.ParserBootstrap;
import org.panda_lang.panda.language.interpreter.parser.context.annotations.Autowired;
import org.panda_lang.panda.language.interpreter.parser.context.annotations.Channel;
import org.panda_lang.panda.language.interpreter.parser.context.annotations.Ctx;
import org.panda_lang.panda.language.interpreter.parser.context.annotations.Src;
import org.panda_lang.panda.language.interpreter.parser.context.handlers.TokenHandler;
import org.panda_lang.utilities.commons.ArrayUtils;

public final class TryCatchParser extends ParserBootstrap<Void> {

    private static final ScopeParser SCOPE_PARSER = new ScopeParser();

    @Override
    public PipelineComponent<? extends Parser>[] pipeline() {
        return ArrayUtils.of(Pipelines.SCOPE);
    }

    @Override
    protected BootstrapInitializer<Void> initialize(Context context, BootstrapInitializer<Void> initializer) {
        return initializer
                .handler(new TokenHandler(Keywords.TRY))
                .linear("try try-body:{~} catch catch-what:(~) catch-body:{~}");
    }

    @Autowired(order = 1)
    public void parse(Context context, LocalChannel channel, @Ctx Scope parent, @Channel Location location, @Src("try-body") Snippet tryBody) {
        Scope tryBlock = SCOPE_PARSER.parse(context, new PandaBlock(parent, location), tryBody);
        TryCatch tryCatch = channel.allocated("statement", new TryCatch(location, tryBlock, new PandaBlock(parent, location)));
        parent.addStatement(tryCatch);
    }

    @Autowired(order = 2)
    public void parse(Context context, @Ctx Scope parent, @Channel TryCatch tryCatch, @Src("catch-what") Snippet catchWhat, @Src("catch-body") Snippet catchBody) {
        Scope catchBlock = new PandaBlock(parent, catchWhat.getLocation());

        PandaVariableDataInitializer dataInitializer = new PandaVariableDataInitializer(context, catchBlock);
        VariableData variableData = dataInitializer.createVariableData(catchWhat, false, false);
        Variable variable = catchBlock.createVariable(variableData);

        SCOPE_PARSER.parse(context, catchBlock, catchBody);
        DynamicClass type = variableData.getType().getAssociatedClass();

        if (type.isAssignableTo(Throwable.class)) {
            //noinspection unchecked
            tryCatch.addHandler((Class<? extends Throwable>) type.fetchStructure(), variable, catchBlock);
        }
    }

}
