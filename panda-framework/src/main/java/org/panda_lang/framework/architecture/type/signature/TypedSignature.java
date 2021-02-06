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

package org.panda_lang.framework.architecture.type.signature;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.architecture.type.Reference;
import org.panda_lang.framework.architecture.type.Type;
import org.panda_lang.framework.interpreter.token.Snippetable;
import org.panda_lang.utilities.commons.text.Joiner;

public final class TypedSignature extends AbstractSignature<Reference> {

    public TypedSignature(@Nullable Signature parent, Reference subject, Signature[] generics, Relation relation, Snippetable source) {
        super(parent, subject, generics, relation, source);
    }

    @Override
    public Signature apply(Signed context) {
        return new TypedSignature(getParent().getOrNull(), getSubject(), applyGenerics(context), getRelation(), getSource());
    }

    @Override
    public boolean isTyped() {
        return true;
    }

    @Override
    public TypedSignature toTyped() {
        return this;
    }

    public Type fetchType() {
        return getReference().fetchType();
    }

    @Override
    public Type toType() {
        return fetchType();
    }

    public Reference getReference() {
        return getSubject();
    }

    @Override
    public String toString() {
        return getSubject() + "<" + Joiner.on(", ").join(getGenerics(), generic -> generic.getRelation() + " " + generic) + ">";
    }

}