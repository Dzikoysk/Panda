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

/**
 * Represents method property
 */
public interface PrototypeMethod extends ExecutableProperty {

    /**
     * Check if method is abstract
     *
     * @return true if method is abstract, otherwise false
     */
    boolean isAbstract();

    /**
     * Check if method comes from Java mappings.
     * It may be important data for e.g. setting value.
     *
     * @return true if field comes from java
     */
    boolean isNative();

    /**
     * Check if method is static
     *
     * @return true if method is static
     */
    boolean isStatic();

}
