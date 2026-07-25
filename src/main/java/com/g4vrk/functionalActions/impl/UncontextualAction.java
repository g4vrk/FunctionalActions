package com.g4vrk.functionalActions.impl;

import com.g4vrk.functionalActions.Action;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class UncontextualAction implements Action<Object> {

    @Override
    public final void execute(@NotNull Object unused, @Nullable String args) {
        this.execute(args);
    }

    protected abstract void execute(@Nullable String args);

}
