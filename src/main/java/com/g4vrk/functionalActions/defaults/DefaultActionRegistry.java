package com.g4vrk.functionalActions.actions.defaults;

import com.g4vrk.functionalActions.actions.Action;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface DefaultActionRegistry<T> {

    @NotNull Class<T> getType();

    @NotNull Collection<Action<T>> getActions();
}
