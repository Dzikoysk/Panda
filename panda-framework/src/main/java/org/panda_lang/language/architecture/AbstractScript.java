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

package org.panda_lang.language.architecture;

import org.panda_lang.language.architecture.module.Module;
import org.panda_lang.language.architecture.statement.Statement;
import org.panda_lang.utilities.commons.function.Completable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScript implements Script {

    private final String scriptName;
    private final List<Statement> statements = new ArrayList<>();
    private final Completable<Module> associatedModule = new Completable<>();

    public AbstractScript(String scriptName) {
        this.scriptName = scriptName;
    }

    @Override
    @SuppressWarnings({ "unchecked " })
    public <T extends Statement> List<T> select(Class<? extends T> statementClass) {
        List<T> selectedStatements = new ArrayList<>();

        for (Statement statement : statements) {
            if (!statementClass.isInstance(statement)) {
                continue;
            }

            T element = (T) statement;
            selectedStatements.add(element);
        }

        return selectedStatements;
    }

    @Override
    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public Completable<Module> getModule() {
        return associatedModule;
    }

    @Override
    public String getName() {
        return scriptName;
    }

}
