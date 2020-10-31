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

package org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.variable;

import org.panda_lang.language.architecture.dynamic.accessor.AbstractAccessor;
import org.panda_lang.language.architecture.dynamic.assigner.Assigner;
import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.architecture.type.Signature;
import org.panda_lang.language.architecture.type.member.field.TypeField;
import org.panda_lang.language.interpreter.source.Localizable;

public final class FieldAccessor extends AbstractAccessor<TypeField> {

    public FieldAccessor(Expression instance, TypeField field) {
        super(new FieldAccessorFunction(instance), field, field.getPointer());
    }

    @Override
    public Assigner<TypeField> toAssigner(Localizable localizable, boolean initialize, Expression value) {
        return new FieldAssigner(localizable.toLocation(), this, initialize, value);
    }

    @Override
    public Signature getSignature() {
        return super.getVariable().getReturnType();
    }

}
