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

package panda.interpreter.architecture.dynamic.accessor;

import org.jetbrains.annotations.Nullable;
import panda.interpreter.architecture.dynamic.assigner.Assigner;
import panda.interpreter.architecture.expression.Expression;
import panda.interpreter.architecture.statement.Variable;
import panda.interpreter.architecture.type.signature.Signature;
import panda.interpreter.architecture.type.signature.Signed;
import panda.interpreter.source.Localizable;
import panda.interpreter.runtime.MemoryContainer;
import panda.interpreter.runtime.ProcessStack;

public interface Accessor<T extends Variable> extends Signed {

    MemoryContainer fetchMemoryContainer(ProcessStack stack, Object instance) throws Exception;

    Assigner<T> toAssigner(Localizable localizable, boolean initialize, Expression value);

    @Nullable <R> R getValue(ProcessStack stack, Object instance) throws Exception;

    @Override
    default Signature getSignature() {
        return getVariable().getSignature();
    }

    int getMemoryPointer();

    T getVariable();

}
