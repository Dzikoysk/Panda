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

package panda.interpreter.syntax.head;

import panda.interpreter.source.Location;
import panda.interpreter.token.TokenInfo;
import panda.interpreter.architecture.statement.AbstractStatement;

public final class CommentStatement extends AbstractStatement {

    private final String comment;

    public CommentStatement(Location location, String comment) {
        super(location);
        this.comment = comment;
    }

    public CommentStatement(TokenInfo token) {
        this(token.getLocation(), token.getValue());
    }

    public String getComment() {
        return comment;
    }

}
