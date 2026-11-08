package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

/**
 * One parsed action line: the action and the arguments that came with it.
 *
 * <p>An instance is created once per distinct line and then reused, so running a line allocates
 * nothing but whatever the action itself creates.</p>
 *
 * @param action the action of the line
 * @param args   the arguments of the line, empty when it had none
 * @param <T>    the context, such as a player or an audience
 */
public record ExecutableAction<T>(
        @NotNull Action<T> action,
        @Nullable String args
) {

    /**
     * Runs the action with the arguments it was parsed with.
     *
     * @param context the context of the line
     */
    public void execute(final @NotNull T context) {
        this.action.execute(context, this.args);
    }

    /**
     * Runs the action after the arguments went through a pre-processor.
     *
     * @param context      the context of the line
     * @param preProcessor what turns the arguments of the line into the ones the action gets,
     *                     usually a placeholder replacer
     */
    public void execute(
            final @NotNull T context,
            final @NotNull UnaryOperator<String> preProcessor
    ) {
        final String args = this.args;

        this.action.execute(context, args == null ? null : preProcessor.apply(args));
    }

}
