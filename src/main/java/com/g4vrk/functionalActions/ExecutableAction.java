package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public record ExecutableAction<T>(Action<T> action, String args) {
    public ExecutableAction(
            @NotNull Action<T> action,
            @NotNull String args
    ) {
        this.action = action;
        this.args = args;
    }

    public void execute(@NotNull T context) {
        action.execute(context, args);
    }

    public void execute(@NotNull T context, @NotNull UnaryOperator<String> argsPreProcessor) {
        action.execute(context, argsPreProcessor.apply(args));
    }

    @Override
    public @NotNull Action<T> action() {
        return action;
    }

    @Override
    public @NotNull String args() {
        return args;
    }
}
