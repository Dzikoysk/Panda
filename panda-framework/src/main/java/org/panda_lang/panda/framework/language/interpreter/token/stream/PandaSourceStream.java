/*
 * Copyright (c) 2015-2018 Dzikoysk
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

package org.panda_lang.panda.framework.language.interpreter.token.stream;

import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.Tokens;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.design.interpreter.token.stream.TokenReader;
import org.panda_lang.panda.framework.language.interpreter.token.PandaTokens;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PandaSourceStream implements SourceStream {

    private Tokens source;
    private Tokens cachedSource;

    public PandaSourceStream(Tokens source) {
        if (source == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }

        this.source = source;
        this.cachedSource = source;
    }

    @Override
    public TokenRepresentation read() {
        if (source.size() < 1) {
            throw new NoSuchElementException("SourceStream is empty, cannot read next TokenRepresentation");
        }

        TokenRepresentation representation = source.get(0);
        List<TokenRepresentation> tokens = source.getTokensRepresentations().subList(1, source.size());

        this.cachedSource = this.source;
        this.source = new PandaTokens(tokens);

        return representation;
    }

    @Override
    public Tokens read(int length) {
        TokenRepresentation[] array = new TokenRepresentation[length];

        for (int i = 0; i < length; i++) {
            array[i] = source.get(i);
        }

        this.cachedSource = this.source;
        this.source = new PandaTokens(source.getTokensRepresentations().subList(length, source.size()));

        return new PandaTokens(array);
    }

    @Override
    public Tokens readLineResidue() {
        List<TokenRepresentation> residue = new ArrayList<>();
        int currentLine = this.getCurrentLine();

        while (this.hasUnreadSource()) {
            TokenRepresentation representation = this.read();

            if (representation.getLine() != currentLine) {
                this.restoreCachedSource();
                break;
            }

            residue.add(representation);
        }

        return new PandaTokens(residue);
    }

    @Override
    public PandaSourceStream restoreCachedSource() {
        this.source = cachedSource;
        return this;
    }

    @Override
    public PandaSourceStream updateCachedSource() {
        this.cachedSource = source;
        return this;
    }

    @Override
    public TokenReader toTokenReader() {
        return new PandaTokenReader(this.toTokenizedSource());
    }

    @Override
    public Tokens toTokenizedSource() {
        return new PandaTokens(new ArrayList<>(this.source.getTokensRepresentations()));
    }

    @Override
    public boolean hasUnreadSource() {
        return source.size() > 0;
    }

    @Override
    public String toString() {
        return "PandaSourceStream['" + this.source + "']";
    }

}
