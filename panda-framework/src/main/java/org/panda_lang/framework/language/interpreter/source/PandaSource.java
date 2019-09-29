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

package org.panda_lang.framework.language.interpreter.source;

import org.panda_lang.framework.design.interpreter.source.Source;

public class PandaSource implements Source {

    private final String title;
    private final String content;
    private final boolean virtual;

    public PandaSource(PandaURLSource codeSource) {
        this(codeSource.getLocation().getPath(), codeSource.getContent(), false);
    }

    public PandaSource(Object title, String content) {
        this(title, content, true);
    }

    public PandaSource(Object title, String content, boolean virtual) {
        this.title = title.toString();
        this.content = content;
        this.virtual = virtual;
    }

    @Override
    public boolean isVirtual() {
        return virtual;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getId() {
        return this.title;
    }

}
