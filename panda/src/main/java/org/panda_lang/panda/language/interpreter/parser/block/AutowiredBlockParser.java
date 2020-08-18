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

package org.panda_lang.panda.language.interpreter.parser.block;

import org.panda_lang.language.interpreter.parser.Components;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.language.interpreter.token.PandaSourceStream;
import org.panda_lang.panda.language.interpreter.parser.autowired.AutowiredParser;

public abstract class AutowiredBlockParser extends AutowiredParser<BlockData> implements BlockSubparser {

    @Override
    public final BlockData parse(Context context, Snippet declaration) {
        return super.parse(context.withComponent(Components.STREAM, new PandaSourceStream(declaration)));
    }

}
