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

package org.panda_lang.framework.language.interpreter;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.Environment;
import org.panda_lang.framework.design.interpreter.Interpretation;
import org.panda_lang.framework.design.interpreter.Interpreter;
import org.panda_lang.framework.design.resource.Language;
import org.panda_lang.utilities.commons.function.ThrowingRunnable;
import org.panda_lang.utilities.commons.function.ThrowingSupplier;

import java.util.ArrayList;
import java.util.Collection;

public final class PandaInterpretation implements Interpretation {

    private final Language language;
    private final Environment environment;
    private final Interpreter interpreter;
    private final Collection<Exception> failures = new ArrayList<>(1);
    private boolean healthy = true;

    public PandaInterpretation(Language language, Environment environment, Interpreter interpreter) {
        this.language = language;
        this.environment = environment;
        this.interpreter = interpreter;
    }

    @Override
    public <E extends Exception> Interpretation execute(ThrowingRunnable<E> runnable) {
        execute(() -> {
            runnable.run();
            return null;
        });

        return this;
    }

    @Override
    public @Nullable <T, E extends Exception> T execute(ThrowingSupplier<T, E> callback) {
        try {
            return isHealthy() ? callback.get() : null;
        } catch (Exception exception) {
            this.healthy = !getEnvironment().getMessenger().send(exception);
            return null;
        }
    }

    @Override
    public boolean isHealthy() {
        return healthy;
    }

    @Override
    public Collection<? extends Exception> getFailures() {
        return failures;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public Interpreter getInterpreter() {
        return interpreter;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

}
