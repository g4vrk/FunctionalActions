package com.g4vrk.functionalActions.parser.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.ExecutableAction;
import com.g4vrk.functionalActions.list.ExecutableActionList;
import com.g4vrk.functionalActions.parser.ActionParser;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimpleActionParser<T> implements ActionParser<T> {

    private final ActionRegistry<T> registry;

    private final Map<String, Map.Entry<String, String>> cache = new Object2ObjectOpenHashMap<>();

    public SimpleActionParser(
            @NotNull ActionRegistry<T> registry
    ) {
        this.registry = registry;
    }

    @Override
    public @Nullable ExecutableAction<? super T> parse(
            final @NotNull String input
    ) {

        final String trimmed = input.trim();

        if (trimmed.isEmpty()) return null;

        final Map.Entry<String, String> cachedPair = cache.get(trimmed);

        if (cachedPair != null) {
            return resolve(cachedPair.getKey(), cachedPair.getValue());
        }

        final String key;
        final String args;

        final int len = trimmed.length();
        int index;

        final char first = trimmed.charAt(0);

        if (first == '[' || first == '(' || first == '<') {

            final char closing = switch (first) {
                case '[' -> ']';
                case '(' -> ')';
                case '<' -> '>';
                default -> throw new IllegalStateException();
            };

            final int closeIndex = trimmed.indexOf(closing, 1);

            if (closeIndex == -1) return null;

            key = trimmed.substring(1, closeIndex).trim();
            index = closeIndex + 1;

        } else {

            final int colonIndex = trimmed.indexOf(':');
            final int spaceIndex = trimmed.indexOf(' ');

            if (colonIndex == -1 && spaceIndex == -1) {
                key = trimmed;
                args = "";
                return resolve(key, args);
            }

            final int splitIndex;

            if (colonIndex == -1) splitIndex = spaceIndex;
            else if (spaceIndex == -1) splitIndex = colonIndex;
            else splitIndex = Math.min(colonIndex, spaceIndex);

            key = trimmed.substring(0, splitIndex).trim();
            index = splitIndex;

        }

        while (index < len) {
            char c = trimmed.charAt(index);
            if (c != ' ' && c != ':') break;
            index++;
        }

        args = (index >= len) ? "" : trimmed.substring(index).trim();

        cache.put(trimmed, Map.entry(key, args));

        return resolve(key, args);

    }

    @Override
    public ExecutableAction<? super T> parse(
            @NotNull String actionStr,
            @NotNull String args
    ) {
        return resolve(actionStr.trim(), args.trim());
    }

    @Override
    public @NotNull ExecutableActionList<? super T> parseAll(@NotNull Collection<String> inputs) {

        final List<ExecutableAction<? super T>> result = new ObjectArrayList<>(inputs.size());

        for (final String input : inputs) {

            final ExecutableAction<? super T> parsed = parse(input);

            if (parsed != null) result.add(parsed);

        }

        return new ExecutableActionList<>(result);

    }

    private @Nullable ExecutableAction<? super T> resolve(
            final @NotNull String key,
            final @NotNull String args
    ) {

        final Action<? super T> action = registry.getAction(key);

        if (action == null) return null;

        return new ExecutableAction<>(action, args);

    }
}