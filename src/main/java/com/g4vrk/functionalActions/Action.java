package com.g4vrk.functionalActions.actions;

import org.bukkit.Keyed;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public interface Action<T> extends Keyed {

    void execute(T context, String args);

    default @NotNull Collection<String> getAliases() {
        return new ArrayList<>();
    }

    default boolean runAsync() {
        return false;
    }
}
