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

package org.panda_lang.framework.design.architecture.prototype;

import org.panda_lang.framework.design.interpreter.parser.ContextComponent;
import org.panda_lang.framework.language.architecture.prototype.PrototypeScope;

public final class PrototypeComponents {

    public static final ContextComponent<Prototype> CLASS_PROTOTYPE = ContextComponent.of("panda-class-prototype", Prototype.class);

    public static final ContextComponent<PrototypeScope> CLASS_FRAME = ContextComponent.of("panda-class-frame", PrototypeScope.class);

    private PrototypeComponents() { }

}