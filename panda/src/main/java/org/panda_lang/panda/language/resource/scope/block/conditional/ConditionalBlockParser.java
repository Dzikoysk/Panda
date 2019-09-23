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

package org.panda_lang.panda.language.resource.scope.block.conditional;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.expression.Expression;
import org.panda_lang.framework.design.architecture.statement.Block;
import org.panda_lang.framework.design.architecture.statement.Scope;
import org.panda_lang.framework.design.interpreter.parser.Context;
import org.panda_lang.framework.design.interpreter.pattern.descriptive.extractor.ExtractorResult;
import org.panda_lang.framework.design.interpreter.source.SourceLocation;
import org.panda_lang.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.framework.language.resource.syntax.keyword.Keywords;
import org.panda_lang.panda.language.interpreter.parser.PandaPipelines;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.block.BlockComponents;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.block.BlockData;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.block.BlockSubparserBootstrap;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.context.BootstrapInitializer;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.context.annotations.Autowired;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.context.annotations.Component;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.context.annotations.Inter;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.context.annotations.Src;
import org.panda_lang.panda.language.interpreter.parser.bootstraps.context.handlers.TokenHandler;
import org.panda_lang.panda.language.interpreter.parser.loader.Registrable;

@Registrable(pipeline = PandaPipelines.BLOCK_LABEL)
public class ConditionalBlockParser extends BlockSubparserBootstrap {

    @Override
    protected BootstrapInitializer<BlockData> initialize(Context context, BootstrapInitializer<BlockData> initializer) {
        return initializer
                .handler(new TokenHandler(Keywords.IF, Keywords.ELSE))
                .pattern("((if:if|elseif:else if) <condition:reader expression>|else:else)");
    }

    @Autowired
    BlockData parse(
            Context context,
            @Inter ExtractorResult result,
            @Inter SourceLocation location,
            @Component Scope parent,
            @Component(BlockComponents.PREVIOUS_BLOCK_LABEL) Block previousBlock,
            @Src("condition") @Nullable Expression condition) {

        if (result.hasIdentifier("else")) {
            ElseBlock elseBlock = new ElseBlock(parent, location);

            if (!(previousBlock instanceof ConditionalBlock)) {
                throw new PandaParserFailure(context, "The Else-block without associated If-block");
            }

            ConditionalBlock conditionalBlock = (ConditionalBlock) previousBlock;
            conditionalBlock.setElseBlock(elseBlock);

            return new BlockData(elseBlock, true);
        }

        if (condition == null) {
            throw new PandaParserFailure(context, "Empty condition");
        }

        ConditionalBlock conditionalBlock = new ConditionalBlock(parent, location, condition);

        if (result.hasIdentifier("if")) {
            return new BlockData(conditionalBlock);
        }

        if (result.hasIdentifier("elseif")) {
            if (!(previousBlock instanceof ConditionalBlock)) {
                throw new PandaParserFailure(context, "The If-Else-block without associated If-block");
            }

            ConditionalBlock previousConditionalBlock = (ConditionalBlock) previousBlock;
            conditionalBlock.setElseBlock(previousConditionalBlock);
            return new BlockData(previousBlock, true);
        }

        throw new PandaParserFailure(context, "Unrecognized condition type");
    }

}
