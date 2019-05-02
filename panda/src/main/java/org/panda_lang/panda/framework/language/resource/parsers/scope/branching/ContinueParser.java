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

package org.panda_lang.panda.framework.language.resource.parsers.scope.branching;

import org.panda_lang.panda.framework.design.architecture.statement.Container;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.interceptor.LinearPatternInterceptor;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaPipelines;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.BootstrapComponents;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.BootstrapParserBuilder;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.UnifiedParserBootstrap;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.Autowired;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.annotations.Component;
import org.panda_lang.panda.framework.design.interpreter.parser.bootstrap.handlers.TokenHandler;
import org.panda_lang.panda.framework.design.resource.parsers.ParserRegistration;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.language.architecture.dynamic.branching.Continue;
import org.panda_lang.panda.framework.language.resource.syntax.keyword.Keywords;

@ParserRegistration(target = PandaPipelines.SCOPE_LABEL)
public class ContinueParser extends UnifiedParserBootstrap {

    @Override
    protected BootstrapParserBuilder initialize(ParserData data, BootstrapParserBuilder defaultBuilder) {
        return defaultBuilder
                .handler(new TokenHandler(Keywords.CONTINUE))
                .interceptor(new LinearPatternInterceptor())
                .pattern("continue &:;");
    }

    @Autowired
    private void parseContinue(@Component(BootstrapComponents.CURRENT_SOURCE_LABEL) Snippet source, @Component Container container) {
        BranchingUtils.parseBranchingStatement(source, container, Continue::new);
    }

}
