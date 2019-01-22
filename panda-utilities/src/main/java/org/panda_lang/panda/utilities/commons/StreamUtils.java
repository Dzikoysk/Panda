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

package org.panda_lang.panda.utilities.commons;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class StreamUtils {

    public static <T> int sum(Collection<T> collection, ToIntFunction<? super T> function) {
        return collection.stream().mapToInt(function).sum();
    }

    public static <T> int count(Collection<T> collection, Predicate<T> filter) {
        return (int) collection.stream().filter(filter).count();
    }

    public static <T> Optional<T> findFirst(Collection<T> collection, Predicate<T> filter) {
        return collection.stream().filter(filter).findFirst();
    }

}