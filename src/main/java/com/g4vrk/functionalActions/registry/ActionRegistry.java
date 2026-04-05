package com.g4vrk.functionalActions.actions.registry;

import com.g4vrk.functionalActions.actions.Action;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public interface ActionRegistry<T> {

    void register(@NotNull Action<T> action);

    void registerAll(@NotNull Collection<Action<T>> actions);

    void override(@NotNull String key, @NotNull Action<T> action);

    void override(@NotNull Action<T> oldAction, @NotNull Action<T> newAction);

    void unregister(@NotNull String key);

    void unregister(@NotNull Action<T> action);

    @NotNull Optional<Action<T>> getAction(@NotNull String key);

    @NotNull Optional<Action<T>> getAction(@NotNull NamespacedKey key);

    boolean contains(@NotNull String key);

    boolean contains(@NotNull NamespacedKey key);

    boolean contains(@NotNull Action<T> action);

    @NotNull Collection<Action<T>> getAll();

    @NotNull Collection<Action<T>> getAll(Predicate<Action<T>> filter);

    @NotNull Map<String, Action<T>> getAllMapped();

    int size();
}
