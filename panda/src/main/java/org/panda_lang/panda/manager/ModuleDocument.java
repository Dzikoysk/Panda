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

package org.panda_lang.panda.manager;

import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class ModuleDocument {

    private final File document;
    private final JsonObject content;

    ModuleDocument(File document, JsonObject content) {
        this.document = document;
        this.content = content;
    }

    protected Dependency toDependency() {
        return new Dependency(getOwner(), getName(), getVersion(), "");
    }

    private List<? extends String> getList(String name) {
        return Optional.ofNullable(content.get(name))
                .map(object -> object.asArray().values().stream()
                        .map(JsonValue::asString)
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    private List<Dependency> getDependencies(String name) {
        return getList(name).stream()
                .map(Dependency::parseDependency)
                .collect(Collectors.toList());
    }

    protected List<Dependency> getTestsDependencies() {
        return getDependencies("tests-dependencies");
    }

    protected List<Dependency> getDependencies() {
        return getDependencies("dependencies");
    }

    protected String getMainScript() {
        JsonValue scripts = content.get("scripts");
        return scripts == null ? null : scripts.asObject().getString("main", null);
    }

    protected String getOwner() {
        return content.getString("owner", null);
    }

    protected String getVersion() {
        return content.getString("version", null);
    }

    protected String getName() {
        return content.getString("name", null);
    }

    protected File getModulesDirectory() {
        return new File(getDocument().getParent(), "panda_modules");
    }

    protected File getDocument() {
        return document;
    }

}
