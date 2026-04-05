package com.g4vrk.functionalActions.parser;

import com.g4vrk.functionalActions.ExecutableAction;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface ActionParser<T> {

    @NotNull Optional<ExecutableAction<T>> parse(@NotNull String input);
    @NotNull Optional<ExecutableAction<T>> parse(@NotNull String actionStr, @NotNull String args);

    @NotNull Collection<ExecutableAction<T>> parseAll(@NotNull Collection<String> inputs);
}
