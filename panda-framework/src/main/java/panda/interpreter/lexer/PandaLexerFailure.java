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

package panda.interpreter.lexer;

import org.jetbrains.annotations.Nullable;
import panda.interpreter.source.Location;
import panda.interpreter.token.Snippetable;
import panda.interpreter.InterpreterFailure;
import panda.interpreter.source.PandaIndicatedSource;
import panda.interpreter.token.PandaToken;
import panda.interpreter.token.PandaTokenInfo;
import panda.interpreter.resource.syntax.TokenTypes;

public final class PandaLexerFailure extends InterpreterFailure {

    public PandaLexerFailure(Snippetable line, Snippetable indicated, String message, @Nullable String note) {
        super(new PandaIndicatedSource(line, indicated), message, note);
    }

    public PandaLexerFailure(CharSequence line, CharSequence indicated, Location location, String message, @Nullable String note) {
        this(of(line, location), of(indicated, location), message, note);
    }

    private static Snippetable of(CharSequence content, Location location) {
        return new PandaTokenInfo(new PandaToken(TokenTypes.UNKNOWN, content.toString()), location);
    }

}
