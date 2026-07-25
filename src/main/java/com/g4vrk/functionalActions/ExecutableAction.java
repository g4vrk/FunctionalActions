package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public record ExecutableAction<T>(
        @NotNull Action<T> action,
        @Nullable String args
) {

    public void execute(
            final @NotNull T context
    ) {
        action.execute(context, args);
    }

    public void execute(
            final @NotNull T context,
            final @NotNull Function<String, String> preProcessor
    ) {
        action.execute(context, preProcessor.apply(args));
    }

}
