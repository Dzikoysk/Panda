/*
 * Copyright (c) 2021 dzikoysk
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

package panda.interpreter.architecture;

import panda.interpreter.logging.Logger;
import panda.interpreter.logging.LoggerHolder;
import panda.std.Option;
import panda.std.Result;

/**
 * Application is a group of bundled scripts
 */
public interface Application extends LoggerHolder {

    /**
     * Launch application with a specified arguments
     */
    <T> Result<Option<T>, Throwable> launch(String... arguments);

    /**
     * Get application environment
     *
     * @return the application environment
     */
    Environment getEnvironment();

    @Override
    default Logger getLogger() {
        return getEnvironment().getLogger();
    }

}
