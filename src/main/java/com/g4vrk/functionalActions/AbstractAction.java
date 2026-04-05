package com.g4vrk.functionalActions;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAction<T> implements Action<T> {

    private final String key;
    private final List<String> aliases;

    public AbstractAction(@NotNull String key) {
        this(key, Collections.emptyList());
    }

    public AbstractAction(@NotNull String key, @NotNull List<String> aliases) {
        this.key = key;
        this.aliases = aliases;
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public @NotNull Collection<String> getAliases() {
        return aliases;
    }
}
