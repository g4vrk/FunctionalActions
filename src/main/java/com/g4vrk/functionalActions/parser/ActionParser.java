package com.g4vrk.functionalActions.parser;

import com.g4vrk.functionalActions.ExecutableAction;
import com.g4vrk.functionalActions.list.ExecutableActionList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ActionParser<T> {

    @Nullable ExecutableAction<? super T> parse(@NotNull String input);
    @Nullable ExecutableAction<? super T> parse(@NotNull String actionStr, @NotNull String args);

    @NotNull ExecutableActionList<? super T> parseAll(@NotNull Collection<String> inputs);
}
