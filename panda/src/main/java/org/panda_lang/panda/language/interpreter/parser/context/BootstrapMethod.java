/*
 * Copyright (c) 2015-2020 Dzikoysk
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

package org.panda_lang.panda.language.interpreter.parser.context;

import org.panda_lang.panda.language.interpreter.parser.context.annotations.Autowired;
import org.panda_lang.panda.language.interpreter.parser.context.data.Delegation;

import java.lang.reflect.Method;

final class BootstrapMethod {

    protected final Method method;
    protected final Autowired autowired;

    BootstrapMethod(Method method) {
        this.method = method;
        this.autowired = method.getAnnotation(Autowired.class);

        if (autowired == null) {
            throw new BootstrapException("Method " + method.getName() + " is not annotated by @Autowired");
        }
    }

    protected Delegation getDelegation() {
        return autowired.delegation();
    }

    protected int getOrder() {
        return autowired.order();
    }

    protected String getCycle() {
        return autowired.cycle();
    }

    protected Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "Bootstrap Method: " + method + " / " + autowired;
    }

}
