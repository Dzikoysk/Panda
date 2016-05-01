package org.panda_lang.panda.core.statement.block;

import org.panda_lang.panda.PandaScript;
import org.panda_lang.panda.core.Alice;
import org.panda_lang.panda.core.Inst;
import org.panda_lang.panda.core.memory.Global;
import org.panda_lang.panda.core.memory.Memory;
import org.panda_lang.panda.core.statement.*;
import org.panda_lang.panda.core.statement.util.NamedExecutable;

import java.util.ArrayList;
import java.util.Collection;

public class PandaBlock extends Block {

    private final PandaScript pandaScript;
    private final Collection<Library> libraries;
    private final Collection<Import> imports;
    private final Memory memory;
    private Group group;

    public PandaBlock(PandaScript pandaScript) {
        this.pandaScript = pandaScript;
        this.memory = new Memory(Global.COMMON_MEMORY);
        this.libraries = new ArrayList<>(0);
        this.imports = new ArrayList<>();
        super.setName("Panda Block");
    }

    public void initializeGlobalVariables() {
        Alice alice = new Alice()
                .pandaScript(pandaScript)
                .memory(memory);

        for (Executable executable : getExecutables()) {
            if (executable instanceof Field) {
                executable.execute(alice);
            }
        }
    }

    @Override
    public Inst execute(Alice alice) {
        for (Executable namedExecutable : getExecutables()) {
            if (namedExecutable instanceof Block) {
                continue;
            }
            namedExecutable.execute(alice);
        }
        return null;
    }

    public Inst call(Class<? extends Block> blockType, String name, RuntimeValue... runtimeValues) {
        for (Executable executable : super.getExecutables()) {
            if (executable instanceof NamedExecutable && executable.getClass() == blockType) {
                NamedExecutable namedExecutable = (NamedExecutable) executable;
                if (namedExecutable.getName().equals(name)) {
                    Alice alice = new Alice()
                            .pandaScript(pandaScript)
                            .memory(memory)
                            .factors(runtimeValues);
                    return executable.execute(alice);
                }
            }
        }
        return null;
    }

    public Collection<Structure> extractVials() {
        Collection<Structure> structures = new ArrayList<>(1);
        for (Executable executable : super.getExecutables()) {
            if (executable instanceof VialBlock) {
                VialBlock vialBlock = (VialBlock) executable;
                Structure structure = vialBlock.getStructure();
                structures.add(structure);
            }
        }
        return structures;
    }

    @Override
    public boolean isReturned() {
        return true;
    }

    public Collection<Library> getLibraries() {
        return libraries;
    }

    public Collection<Import> getImports() {
        return imports;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
