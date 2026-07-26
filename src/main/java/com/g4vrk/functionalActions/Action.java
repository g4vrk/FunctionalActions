package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Action<C> {

    void execute(@NotNull C context, @Nullable String args);

}
