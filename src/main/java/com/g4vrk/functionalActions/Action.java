package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public interface Action<T> {

    @NotNull String getKey();

    void execute(@NotNull T context, @NotNull String args);

    default @NotNull Collection<String> getAliases() {
        return Collections.emptyList();
    }
}
