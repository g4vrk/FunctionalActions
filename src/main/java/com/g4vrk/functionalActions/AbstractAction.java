package com.g4vrk.functionalActions.actions;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAction<T> implements Action<T> {

    private final NamespacedKey namespacedKey;
    private final List<String> aliases;
    private final boolean runAsync;

    public AbstractAction(NamespacedKey namespacedKey) {
        this(namespacedKey, Collections.emptyList(), false);
    }

    public AbstractAction(NamespacedKey namespacedKey, List<String> aliases) {
        this(namespacedKey, aliases, false);
    }

    protected AbstractAction(NamespacedKey namespacedKey, List<String> aliases, boolean runAsync) {
        this.namespacedKey = namespacedKey;
        this.aliases = aliases;
        this.runAsync = runAsync;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return namespacedKey;
    }

    @Override
    public @NotNull Collection<String> getAliases() {
        return aliases;
    }

    @Override
    public boolean runAsync() {
        return runAsync;
    }
}
