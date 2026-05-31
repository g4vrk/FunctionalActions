package com.g4vrk.functionalActions.registry.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class SimpleActionRegistry<T> implements ActionRegistry<T> {

    private final Map<String, Action<? super T>> actionMap = new ConcurrentHashMap<>();

    @Override
    public void register(@NotNull Action<? super T> action) {
        register0(action);
    }

    @Override
    public void registerAll(@NotNull Collection<? extends Action<? super T>> actions) {
        actions.forEach(this::register);
    }

    private void register0(Action<? super T> action) {
        registerKey(action.getKey(), action, false);

        for (String alias : action.getAliases()) {
            registerKey(alias, action, false);
        }
    }

    private void registerKey(String key, Action<? super T> action, boolean override) {
        String normalized = normalize(key);

        if (!override && actionMap.containsKey(normalized)) {
            throw new IllegalStateException("Key '" + normalized + "' already registered by " + actionMap.get(normalized).getClass().getSimpleName());
        }

        actionMap.put(normalized, action);
    }

    @Override
    public void override(@NotNull String key, @NotNull Action<? super T> action) {
        registerKey(key, action, true);
    }

    @Override
    public void override(@NotNull Action<? super T> oldAction, @NotNull Action<? super T> newAction) {
        unregister(oldAction);
        register(newAction);
    }

    @Override
    public void unregister(@NotNull String key) {
        actionMap.remove(normalize(key));
    }

    @Override
    public void unregister(@NotNull Action<? super T> action) {
        actionMap.entrySet().removeIf(e -> e.getValue() == action);
    }

    @Override
    public @NotNull Optional<Action<? super T>> getAction(@NotNull String key) {
        return Optional.ofNullable(actionMap.get(normalize(key)));
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
    public @NotNull Collection<Action<? super T>> getAll() {
        return new HashSet<>(actionMap.values());
    }

    @Override
    public @NotNull Map<String, Action<? super T>> getAllMapped() {
        return new HashMap<>(actionMap);
    }

    @Override
    public @NotNull Collection<Action<? super T>> getAll(Predicate<Action<? super T>> filter) {
        Set<Action<? super T>> result = new HashSet<>();

        for (Action<? super T> action : actionMap.values()) {
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