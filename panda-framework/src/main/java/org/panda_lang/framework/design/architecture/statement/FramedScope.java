/*
 * Copyright (c) 2015-2020 Dzikoysk
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

package org.panda_lang.framework.design.architecture.statement;

import org.panda_lang.framework.design.architecture.dynamic.Frame;
import org.panda_lang.framework.design.runtime.ProcessStack;

/**
 * Specific type of scope which contains own memory, independence, etc.
 */
public interface FramedScope extends Scope {

    /**
     * Creates a new instance of the frame
     *
     * @return instance of the frame
     */
    Frame revive(ProcessStack stack, Object instance) throws Exception;

    /**
     * Allocate variable in the frame and get assigned pointer
     *
     * @return the assigned pointer
     */
    int allocate();

    /**
     * Get required memory size by {@link org.panda_lang.framework.design.architecture.dynamic.Frame}
     *
     * @return the required size of memory
     */
    int getRequiredMemorySize();

}   
