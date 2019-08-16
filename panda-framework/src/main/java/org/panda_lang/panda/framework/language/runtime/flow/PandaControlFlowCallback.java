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

package org.panda_lang.panda.framework.language.runtime.flow;

import org.panda_lang.panda.framework.design.runtime.flow.ControlFlow;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;

public interface PandaControlFlowCallback {

    /**
     * Custom call
     * 
     * @param flow the current flow
     *  @param frame the frame to execute
     */
    void call(ControlFlow flow, Flow frame);

}