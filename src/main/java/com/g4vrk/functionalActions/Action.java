package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Action<T> {

    void execute(@NotNull T context, @Nullable String args);

}
