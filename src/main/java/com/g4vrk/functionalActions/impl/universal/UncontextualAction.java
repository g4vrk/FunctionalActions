package com.g4vrk.functionalActions.impl.universal;

import com.g4vrk.functionalActions.AbstractAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class UncontextualAction extends AbstractAction<Object> {
    public UncontextualAction(@NotNull String key) {
        super(key);
    }

    public UncontextualAction(@NotNull String key, @NotNull List<String> aliases) {
        super(key, aliases);
    }

    @Override
    public final void execute(@NotNull Object unused, @NotNull String args) {
        execute(args);
    }

    protected abstract void execute(@NotNull String args);
}
