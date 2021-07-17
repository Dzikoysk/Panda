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

package panda.interpreter.parser.stage;

import org.jetbrains.annotations.Nullable;
import panda.std.Option;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PandaStageManager implements StageManager {

    private final Map<String, StagePhase> cycles = new LinkedHashMap<>();
    private StagePhase currentCycle;

    public PandaStageManager initialize(List<? extends Phase> types) {
        Collections.sort(types);

        for (Phase type : types) {
            cycles.put(type.getName(), new PandaStagePhase(this, type.getName()));
        }

        return this;
    }

    @Override
    public void launch(Runnable mainAction) {
        mainAction.run();

        while (countTasksBefore(null) > 0) {
            executeOnce(mainAction);
        }
    }

    private void executeOnce(Runnable mainAction) {
        for (StagePhase cycle : cycles.values()) {
            currentCycle = cycle;

            boolean full = cycle.execute();
            mainAction.run();

            if (!full) {
                break;
            }

            if (countTasksBefore(cycle) > 0) {
                break;
            }
        }

        currentCycle = null;
    }

    @Override
    public int countTasksBefore(@Nullable StagePhase to) {
        int count = 0;

        for (StagePhase cycle : cycles.values()) {
            count += cycle.countTasks();

            if (cycle.equals(to)) {
                break;
            }
        }

        return count;
    }

    @Override
    public int countTasksBefore() {
        return countTasksBefore(null);
    }

    @Override
    public Option<StagePhase> getCurrentCycle() {
        return Option.of(currentCycle);
    }

    @Override
    public StagePhase getPhase(Phase type) {
        return getPhase(type.getName());
    }

    @Override
    public StagePhase getPhase(String name) {
        StagePhase cycle = cycles.get(name);

        if (cycle == null) {
            throw new IllegalArgumentException("Cycle " + name + " does not exist");
        }

        return cycle;
    }

    @Override
    public String toString() {
        return "Generation { cycles: " + cycles.toString() + " }";
    }

}
