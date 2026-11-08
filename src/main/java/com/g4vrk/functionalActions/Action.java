package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * One thing an action line can do.
 *
 * <p>An implementation is registered under one or more keys and runs with the context the line
 * was executed for, for example a player or an audience, plus the arguments of the line. It is
 * called on whichever thread ran the line, so an implementation that touches a world moves to the
 * right thread itself.</p>
 *
 * @param <C> the context, such as a player or an audience
 */
@FunctionalInterface
public interface Action<C> {

    /**
     * Runs the action.
     *
     * @param context the context of the line
     * @param args    what followed the key of the line, empty when nothing did
     */
    void execute(final @NotNull C context, final @Nullable String args);

}
