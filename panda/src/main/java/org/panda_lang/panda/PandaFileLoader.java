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

package org.panda_lang.panda;

import org.panda_lang.framework.architecture.Application;
import org.panda_lang.framework.architecture.packages.Package;
import org.panda_lang.panda.language.PandaEnvironment;
import panda.std.Result;

import java.io.File;

public final class PandaFileLoader {

    private final Panda panda;

    public PandaFileLoader(Panda panda) {
        this.panda = panda;
    }

    public Result<Application, Throwable> load(File workingDirectory, Package pkg) {
        PandaEnvironment environment = new PandaEnvironment(panda, workingDirectory);
        environment.initialize();

        return environment.getInterpreter().interpret(pkg);
    }

}
