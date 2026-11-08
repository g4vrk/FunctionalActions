package com.g4vrk.functionalActions.registry.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The actions of one kind, by key.
 *
 * <p>Keys are trimmed and lower-cased once, at registration, so a lookup of a key that is already
 * lower-case allocates nothing. The map is concurrent: lines are parsed on several threads.</p>
 *
 * @param <T> the context the actions run for
 */
public class SimpleActionRegistry<T> implements ActionRegistry<T> {

    private final boolean replaceIfExists;

    private final Map<String, Action<? super T>> actions = new ConcurrentHashMap<>();

    public SimpleActionRegistry() {
        this(false);
    }

    /**
     * @param replaceIfExists whether registering an existing key replaces the action instead of
     *                        throwing
     */
    public SimpleActionRegistry(final boolean replaceIfExists) {
        this.replaceIfExists = replaceIfExists;
    }

    @Override
    public void register(final @NotNull String key, final @NotNull Action<? super T> action) {
        final String normalized = normalize(key);

        if (this.replaceIfExists) {
            this.actions.put(normalized, action);
            return;
        }

        final Action<? super T> previous = this.actions.putIfAbsent(normalized, action);

        if (previous != null) {
            throw new IllegalStateException(
                    "Key '" + normalized + "' already registered by " + previous.getClass().getSimpleName()
            );
        }
    }

    @Override
    public void register(
            final @NotNull Action<? super T> action,
            final @NotNull String @NotNull ... keys
    ) {
        if (keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be specified.");
        }

        for (final String key : keys) {
            this.register(key, action);
        }
    }

    @Override
    public void unregister(final @NotNull String key) {
        this.actions.remove(normalize(key));
    }

    @Override
    public @Nullable Action<? super T> getAction(final @NotNull String key) {
        return this.actions.get(normalize(key));
    }

    @Override
    public boolean contains(final @NotNull String key) {
        return this.actions.containsKey(normalize(key));
    }

    @Override
    public boolean contains(final @NotNull Action<? super T> action) {
        return this.actions.containsValue(action);
    }

    @Override
    public @NotNull Map<String, Action<? super T>> all() {
        return Collections.unmodifiableMap(this.actions);
    }

    @Override
    public int size() {
        return this.actions.size();
    }

    /**
     * Trims and lower-cases a key, answering the argument itself when it needs no change.
     *
     * @param key the key
     * @return the key the map is read with
     */
    private static @NotNull String normalize(final @NotNull String key) {
        final int length = key.length();

        int start = 0;
        int end = length;
        boolean upper = false;

        while (start < end && key.charAt(start) <= ' ') {
            start++;
        }

        while (end > start && key.charAt(end - 1) <= ' ') {
            end--;
        }

        for (int index = start; index < end; index++) {
            if (key.charAt(index) >= 'A' && key.charAt(index) <= 'Z') {
                upper = true;
                break;
            }
        }

        if (start == 0 && end == length && !upper) {
            return key;
        }

        return key.substring(start, end).toLowerCase(Locale.ROOT);
    }

}
