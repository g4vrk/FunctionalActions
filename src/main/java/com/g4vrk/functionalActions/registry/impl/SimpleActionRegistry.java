package com.g4vrk.functionalActions.registry.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class SimpleActionRegistry<T> implements ActionRegistry<T> {

    private final Map<String, Action<T>> actionMap = new ConcurrentHashMap<>();

    @Override
    public void register(@NotNull Action<T> action) {
        register0(action);
    }

    @Override
    public void registerAll(@NotNull Collection<Action<T>> actions) {
        actions.forEach(this::register);
    }

    private void register0(Action<T> action) {
        registerKey(action.getKey(), action, false);

        for (String alias : action.getAliases()) {
            registerKey(alias, action, false);
        }
    }

    private void registerKey(String key, Action<T> action, boolean override) {
        String normalized = normalize(key);

        if (!override && actionMap.containsKey(normalized)) {
            throw new IllegalStateException("Key '" + normalized + "' already registered by " + actionMap.get(normalized).getClass().getSimpleName());
        }

        actionMap.put(normalized, action);
    }

    @Override
    public void override(@NotNull String key, @NotNull Action<T> action) {
        registerKey(key, action, true);
    }

    @Override
    public void override(@NotNull Action<T> oldAction, @NotNull Action<T> newAction) {
        unregister(oldAction);
        register(newAction);
    }

    @Override
    public void unregister(@NotNull String key) {
        actionMap.remove(normalize(key));
    }

    @Override
    public void unregister(@NotNull Action<T> action) {
        actionMap.entrySet().removeIf(e -> e.getValue() == action);
    }

    @Override
    public @NotNull Optional<Action<T>> getAction(@NotNull String key) {
        return Optional.ofNullable(actionMap.get(normalize(key)));
    }

    @Override
    public boolean contains(@NotNull String key) {
        return actionMap.containsKey(normalize(key));
    }

    @Override
    public boolean contains(@NotNull Action<T> action) {
        return actionMap.containsValue(action);
    }

    @Override
    public @NotNull Collection<Action<T>> getAll() {
        return new HashSet<>(actionMap.values());
    }

    @Override
    public @NotNull Map<String, Action<T>> getAllMapped() {
        return new HashMap<>(actionMap);
    }

    @Override
    public @NotNull Collection<Action<T>> getAll(Predicate<Action<T>> filter) {
        Set<Action<T>> result = new HashSet<>();

        for (Action<T> action : actionMap.values()) {
            if (filter.test(action)) {
                result.add(action);
            }
        }

        return result;
    }

    @Override
    public int size() {
        return new HashSet<>(actionMap.values()).size();
    }

    private String normalize(String input) {
        return input.trim().toLowerCase();
    }
}