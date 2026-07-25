package com.g4vrk.functionalActions.registry;

import com.g4vrk.functionalActions.Action;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ActionRegistry<T> {

    void register(
            @NotNull String key,
            @NotNull Action<? super T> action
    );

    void register(
            @NotNull Action<? super T> action,
            @NotNull String @NotNull ... keys
    );

    void unregister(@NotNull String key);

    @Nullable Action<? super T> getAction(@NotNull String key);

    boolean contains(@NotNull String key);

    boolean contains(@NotNull Action<? super T> action);

    @NotNull Map<String, Action<? super T>> all();

    int size();

}
