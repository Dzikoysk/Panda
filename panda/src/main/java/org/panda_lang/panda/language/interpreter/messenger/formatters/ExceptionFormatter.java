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

package org.panda_lang.panda.language.interpreter.messenger.formatters;

import org.panda_lang.framework.design.interpreter.messenger.MessengerTypeFormatter;
import org.panda_lang.panda.language.interpreter.messenger.MessengerDataFormatter;
import org.panda_lang.panda.language.interpreter.messenger.mappers.StacktraceMapper;
import org.panda_lang.utilities.commons.ArrayUtils;
import org.panda_lang.utilities.commons.StackTraceUtils;

public final class ExceptionFormatter implements MessengerDataFormatter<Exception> {

    @Override
    public void onInitialize(MessengerTypeFormatter<Exception> typeFormatter) {
        typeFormatter.register("{{exception.short}}", (formatter, exception) -> {
            StackTraceElement[] stacktrace = StackTraceUtils.filter(exception.getStackTrace(), StacktraceMapper.IGNORED);

            return "Stack: " + System.lineSeparator() +
                    //"  at " + exception.getClass().getName() + (exception.getMessage() != null ? ": " + exception.getMessage() : "") + System.lineSeparator() +
                    "  " + ArrayUtils.get(stacktrace, 0).map(StackTraceElement::toString).orElseGet("") + System.lineSeparator() +
                    "  " + ArrayUtils.get(stacktrace, 1).map(StackTraceElement::toString).orElseGet("");
        });
    }

    @Override
    public Class<Exception> getType() {
        return Exception.class;
    }

}
