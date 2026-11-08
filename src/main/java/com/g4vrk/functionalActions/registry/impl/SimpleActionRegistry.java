package com.g4vrk.functionalActions.registry.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.registry.ActionRegistry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SimpleActionRegistry<T> implements ActionRegistry<T> {

    private final boolean replaceIfExists;

    private final Map<String, Action<? super T>> actionMap = new java.util.concurrent.ConcurrentHashMap<>();

    public SimpleActionRegistry() {
        this(false);
    }

    public SimpleActionRegistry(
            boolean replaceIfExists
    ) {
        this.replaceIfExists = replaceIfExists;
    }

    @Override
    public void register(@NotNull String key, @NotNull Action<? super T> action) {
        final String normalized = normalize(key);

        java.util.Objects.requireNonNull(action, "action");
        if (replaceIfExists) {
            actionMap.put(normalized, action);
        } else if (actionMap.putIfAbsent(normalized, action) != null) {
            throw new IllegalStateException("Key '" + normalized + "' already registered");
        }
    }

    @Override
    public void register(
            @NotNull Action<? super T> action,
            @NotNull String @NotNull ... keys
    ) {
        if (keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be specified.");
        }

        for (final String key : keys) {

            this.register(key, action);

        }
    }

    @Override
    public void unregister(@NotNull String key) {
        actionMap.remove(normalize(key));
    }

    @Override
    public @Nullable Action<? super T> getAction(@NotNull String key) {
        return actionMap.get(normalize(key));
    }

    @Override
    public boolean contains(@NotNull String key) {
        return actionMap.containsKey(normalize(key));
    }

    @Override
    public boolean contains(@NotNull Action<? super T> action) {
        return actionMap.containsValue(action);
    }

    @Override
    public @NotNull Map<String, Action<? super T>> all() {
        return Map.copyOf(actionMap);
    }

    @Override
    public int size() {
        return actionMap.size();
    }

    private @NotNull String normalize(@NotNull String input) {
        return input.trim().toLowerCase(java.util.Locale.ROOT);
    }
}