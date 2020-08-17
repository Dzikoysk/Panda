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

package org.panda_lang.language.interpreter.parser;

import org.panda_lang.language.FrameworkController;
import org.panda_lang.language.architecture.Application;
import org.panda_lang.language.architecture.Environment;
import org.panda_lang.language.architecture.Script;
import org.panda_lang.language.architecture.module.Imports;
import org.panda_lang.language.architecture.statement.Scope;
import org.panda_lang.language.architecture.module.TypeLoader;
import org.panda_lang.language.interpreter.parser.expression.ExpressionParser;
import org.panda_lang.language.interpreter.parser.stage.StageController;
import org.panda_lang.language.interpreter.parser.pipeline.PipelinePath;
import org.panda_lang.language.interpreter.source.SourceSet;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.language.interpreter.token.SourceStream;

/**
 * The most common components available in the context
 */
public final class Components {

    /**
     * Represents the framework controller
     */
    public static final ContextComponent<FrameworkController> CONTROLLER = ContextComponent.of("controller", FrameworkController.class);

    /**
     * Represents the application environment
     */
    public static final ContextComponent<Environment> ENVIRONMENT = ContextComponent.of("environment", Environment.class);

    /**
     * Represents the generation
     */
    public static final ContextComponent<StageController> GENERATION = ContextComponent.of("generation", StageController.class);

    /**
     * Represents the pipeline path with all registered pipelines
     */
    public static final ContextComponent<PipelinePath> PIPELINE = ContextComponent.of("pipeline-path", PipelinePath.class);

    /**
     * Represents the main expression parser with all registered subparsers
     */
    public static final ContextComponent<ExpressionParser> EXPRESSION = ContextComponent.of("expression-parser", ExpressionParser.class);

    /**
     * Represents the application module loader
     */
    public static final ContextComponent<TypeLoader> TYPE_LOADER = ContextComponent.of("type-loader", TypeLoader.class);

    /**
     * Represents the current application
     */
    public static final ContextComponent<Application> APPLICATION = ContextComponent.of("application", Application.class);

    /**
     * Represents all sources to parse
     */
    public static final ContextComponent<SourceSet> SOURCES = ContextComponent.of("source-set", SourceSet.class);

    /**
     * Represents the current script
     */
    public static final ContextComponent<Script> SCRIPT = ContextComponent.of("script", Script.class);

    /**
     * Represents the current source of script
     */
    public static final ContextComponent<Snippet> SOURCE = ContextComponent.of("source", Snippet.class);

    /**
     * Represents imports assigned to the current script
     */
    public static final ContextComponent<Imports> IMPORTS = ContextComponent.of("imports", Imports.class);

    /**
     * Represents the currently parsed source
     */
    public static final ContextComponent<Snippet> CURRENT_SOURCE = ContextComponent.of("current-source", Snippet.class);

    /**
     * Represents the current stream of source
     */
    public static final ContextComponent<SourceStream> STREAM = ContextComponent.of("source-stream", SourceStream.class);

    /**
     * Represents the channel between handler and parser
     */
    public static final ContextComponent<LocalChannel> CHANNEL = ContextComponent.of("channel", LocalChannel.class);

    /**
     * Represents the current scope
     */
    public static final ContextComponent<Scope> SCOPE = ContextComponent.of("scope", Scope.class);

    private Components() { }

}
