package org.panda_lang.panda.framework.language.interpreter.parser.generation.pipeline;

import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.pipeline.Generation;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.pipeline.GenerationCallback;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.pipeline.GenerationLayer;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.pipeline.GenerationPipeline;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.pipeline.GenerationUnit;

import java.util.ArrayList;
import java.util.List;

public class PandaGenerationLayer implements GenerationLayer {

    private final Generation generation;
    private final List<GenerationUnit> before;
    private final List<GenerationUnit> delegates;
    private final List<GenerationUnit> after;

    public PandaGenerationLayer(Generation generation) {
        this.generation = generation;
        this.before = new ArrayList<>(1);
        this.delegates = new ArrayList<>();
        this.after = new ArrayList<>(1);
    }

    public void call(ParserData currentData, GenerationLayer nextLayer) throws Throwable {
        call(before, currentData, nextLayer);
        call(delegates, currentData, nextLayer);
        call(after, currentData, nextLayer);
    }

    private void call(List<GenerationUnit> units, ParserData currentInfo, GenerationLayer nextLayer) throws Throwable {
        List<GenerationUnit> unitList = new ArrayList<>(units);
        units.clear();

        for (GenerationUnit unit : unitList) {
            GenerationCallback callback = unit.getCallback();
            ParserData delegatedInfo = unit.getDelegated();
            callback.call(generation, unit.getDelegated());
        }
    }

    @Override
    public void callDelegates(GenerationPipeline pipeline, ParserData data) throws Throwable {
        call(data, null);
    }

    @Override
    public GenerationLayer delegateBefore(GenerationCallback callback, ParserData delegated) {
        return delegate(before, callback, delegated);
    }

    @Override
    public GenerationLayer delegate(GenerationCallback callback, ParserData delegated) {
        return delegate(delegates, callback, delegated);
    }

    @Override
    public GenerationLayer delegateAfter(GenerationCallback callback, ParserData delegated) {
        return delegate(after, callback, delegated);
    }

    private GenerationLayer delegate(List<GenerationUnit> units, GenerationCallback callback, ParserData delegated) {
        units.add(new PandaGenerationUnit(callback, delegated));
        return this;
    }

    @Override
    public int countDelegates() {
        return before.size() + delegates.size() + after.size();
    }

}