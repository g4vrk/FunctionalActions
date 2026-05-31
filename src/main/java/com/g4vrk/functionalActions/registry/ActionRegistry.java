package com.g4vrk.functionalActions.registry;

import com.g4vrk.functionalActions.Action;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public interface ActionRegistry<T> {

    void register(@NotNull Action<? super T> action);

    void registerAll(@NotNull Collection<? extends Action<? super T>> actions);

    void override(@NotNull String key, @NotNull Action<? super T> action);

    void override(@NotNull Action<? super T> oldAction, @NotNull Action<? super T> newAction);

    void unregister(@NotNull String key);

    void unregister(@NotNull Action<? super T> action);

    @NotNull Optional<Action<? super T>> getAction(@NotNull String key);

    boolean contains(@NotNull String key);

    boolean contains(@NotNull Action<? super T> action);

    @NotNull Collection<Action<? super T>> getAll();

    @NotNull Collection<Action<? super T>> getAll(Predicate<Action<? super T>> filter);

    @NotNull Map<String, Action<? super T>> getAllMapped();

    int size();
}
