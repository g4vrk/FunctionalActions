package com.g4vrk.functionalActions.parser;

import com.g4vrk.functionalActions.ExecutableAction;
import com.g4vrk.functionalActions.list.ExecutableActionList;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface ActionParser<T> {

    @NotNull Optional<ExecutableAction<? super T>> parse(@NotNull String input);
    @NotNull Optional<ExecutableAction<? super T>> parse(@NotNull String actionStr, @NotNull String args);

    @NotNull ExecutableActionList<? super T> parseAll(@NotNull Collection<String> inputs);
}
