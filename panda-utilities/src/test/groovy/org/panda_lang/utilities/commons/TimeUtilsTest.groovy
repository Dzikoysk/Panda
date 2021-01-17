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

package org.panda_lang.utilities.commons

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

@CompileStatic
final class TimeUtilsTest {

    @Test
    void toMilliseconds() {
        assertEquals "1.00ms", TimeUtils.toMilliseconds(1_000_000)
    }

    @Test
    void toSeconds() {
        assertEquals "1.00s", TimeUtils.toSeconds(1_000)
    }

    @Test
    void getUptime() {
        assertTrue TimeUtils.getUptime(System.currentTimeMillis() - 1L) > 0
    }

}
