package com.g4vrk.functionalActions.actions.registry.impl;

import com.g4vrk.functionalActions.actions.Action;
import com.g4vrk.functionalActions.actions.registry.ActionRegistry;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class SimpleActionRegistry<T> implements ActionRegistry<T> {

    private final Map<NamespacedKey, Action<T>> byKey = new ConcurrentHashMap<>();
    private final Map<String, Action<T>> byString = new ConcurrentHashMap<>();

    @Override
    public void register(@NotNull Action<T> action) {
        register0(action);
    }

    @Override
    public void registerAll(@NotNull Collection<Action<T>> actions) {
        actions.forEach(this::register);
    }

    private void register0(Action<T> action) {
        NamespacedKey key = action.getKey();

        if (byKey.containsKey(key)) {
            throw new IllegalStateException(
                    "Действие '" + key + "' уже зарегистрировано (" + byKey.get(key).getClass().getSimpleName() + ")"
            );
        }

        byKey.put(key, action);

        registerString(key.toString(), action, false);
        registerString(key.getKey(), action, false);

        for (String alias : action.getAliases()) registerString(alias, action, false);
    }

    private void registerString(String key, Action<T> action, boolean override) {
        String normalized = normalize(key);

        if (!override && byString.containsKey(normalized)) {
            throw new IllegalStateException(
                    "Алиас '" + normalized + "' уже занят (" + byString.get(normalized).getClass().getSimpleName() + ")"
            );
        }

        byString.put(normalized, action);
    }

    @Override
    public void override(@NotNull String key, @NotNull Action<T> action) {
        registerString(key, action, true);
    }

    @Override
    public void override(@NotNull Action<T> oldAction, @NotNull Action<T> newAction) {
        unregister(oldAction);
        register(newAction);
    }

    @Override
    public void unregister(@NotNull String key) {
        byString.remove(normalize(key));
    }

    @Override
    public void unregister(@NotNull Action<T> action) {
        byKey.entrySet().removeIf(e -> e.getValue().equals(action));
        byString.entrySet().removeIf(e -> e.getValue().equals(action));
    }

    @Override
    public @NotNull Optional<Action<T>> getAction(@NotNull String key) {
        return Optional.ofNullable(byString.get(normalize(key)));
    }

    @Override
    public @NotNull Optional<Action<T>> getAction(@NotNull NamespacedKey key) {
        return Optional.ofNullable(byKey.get(key));
    }

    @Override
    public boolean contains(@NotNull String key) {
        return byString.containsKey(normalize(key));
    }

    @Override
    public boolean contains(@NotNull NamespacedKey key) {
        return byKey.containsKey(key);
    }

    @Override
    public boolean contains(@NotNull Action<T> action) {
        return byKey.containsValue(action);
    }

    @Override
    public @NotNull Collection<Action<T>> getAll() {
        return Set.copyOf(byKey.values());
    }

    @Override
    public @NotNull Map<String, Action<T>> getAllMapped() {
        return Collections.unmodifiableMap(byString);
    }

    @Override
    public @NotNull Collection<Action<T>> getAll(Predicate<Action<T>> filter) {
        Set<Action<T>> result = new HashSet<>();

        for (Action<T> action : byKey.values()) {
            if (filter.test(action)) {
                result.add(action);
            }
        }

        return result;
    }

    @Override
    public int size() {
        return byKey.size();
    }

    private String normalize(String input) {
        String key = input.toLowerCase().trim();

        if ((key.startsWith("[") && key.endsWith("]")) ||
                (key.startsWith("(") && key.endsWith(")"))) {
            key = key.substring(1, key.length() - 1);
        }

        return key;
    }
}
