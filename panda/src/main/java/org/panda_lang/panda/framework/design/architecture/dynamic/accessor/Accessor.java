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

package org.panda_lang.panda.framework.design.architecture.dynamic.accessor;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototypeReference;
import org.panda_lang.panda.framework.design.architecture.value.Value;
import org.panda_lang.panda.framework.design.architecture.value.Variable;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.memory.MemoryContainer;
import org.panda_lang.panda.framework.design.architecture.dynamic.assigner.Assigner;

public interface Accessor<T extends Variable> {

    @Nullable Value perform(Flow flow, AccessorVisitor callback);

    MemoryContainer fetchMemoryContainer(Flow flow);

    Assigner<T> toAssigner(Expression expression);

    default ClassPrototypeReference getTypeReference() {
        return getVariable().getType();
    }

    @Nullable Value getValue(Flow flow);

    int getMemoryPointer();

    T getVariable();

}
