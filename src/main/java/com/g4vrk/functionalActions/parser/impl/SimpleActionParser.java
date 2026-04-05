package com.g4vrk.functionalActions.parser.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.ExecutableAction;
import com.g4vrk.functionalActions.parser.ActionParser;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SimpleActionParser<T> implements ActionParser<T> {

    private final ActionRegistry<T> registry;

    public SimpleActionParser(@NotNull ActionRegistry<T> registry) {
        this.registry = registry;
    }

    @Override
    public @NotNull Optional<ExecutableAction<T>> parse(@NotNull String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) return Optional.empty();

        String key;
        String args;

        int len = trimmed.length();
        int index;

        char first = trimmed.charAt(0);

        if (first == '[' || first == '(' || first == '<') {
            char closing = switch (first) {
                case '[' -> ']';
                case '(' -> ')';
                case '<' -> '>';
                default -> throw new IllegalStateException();
            };

            int closeIndex = trimmed.indexOf(closing, 1);
            if (closeIndex == -1) return Optional.empty();

            key = trimmed.substring(1, closeIndex).trim();
            index = closeIndex + 1;
        } else {
            int colonIndex = trimmed.indexOf(':');
            int spaceIndex = trimmed.indexOf(' ');

            if (colonIndex == -1 && spaceIndex == -1) {
                key = trimmed;
                args = "";
                return resolve(key, args);
            }

            int splitIndex;
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

        return resolve(key, args);
    }

    @Override
    public @NotNull Optional<ExecutableAction<T>> parse(
            @NotNull String actionStr,
            @NotNull String args
    ) {
        return resolve(actionStr.trim(), args.trim());
    }

    @Override
    public @NotNull Collection<ExecutableAction<T>> parseAll(@NotNull Collection<String> inputs) {
        List<ExecutableAction<T>> result = new ArrayList<>(inputs.size());

        for (String input : inputs) {
            parse(input).ifPresent(result::add);
        }

        return result;
    }

    private @NotNull Optional<ExecutableAction<T>> resolve(
            @NotNull String key,
            @NotNull String args
    ) {
        Optional<Action<T>> actionOpt = registry.getAction(key);
        return actionOpt.map(action -> new ExecutableAction<>(action, args));
    }
}