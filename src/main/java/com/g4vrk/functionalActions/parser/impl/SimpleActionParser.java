package com.g4vrk.functionalActions.parser.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.ExecutableAction;
import com.g4vrk.functionalActions.list.ExecutableActionList;
import com.g4vrk.functionalActions.parser.ActionParser;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Parses action lines and remembers the result.
 *
 * <p>A line looks like {@code message: Hello}, {@code [message] Hello}, {@code (message) Hello}
 * or {@code <message> Hello}; a line without a key is not an action and parses to {@code null}.
 * Keys are case-insensitive.</p>
 *
 * <p>Parsing happens once per distinct line: the result is kept, so running the same config line
 * for a thousand players looks the key up once and allocates nothing after that. The caches are
 * concurrent because commands, checks and storage threads all parse lines, and bounded so a
 * config that keeps changing cannot grow them without end.</p>
 *
 * @param <T> the context the actions run for
 */
public class SimpleActionParser<T> implements ActionParser<T> {

    private static final int CACHE_LIMIT = 4_096;

    private final ActionRegistry<T> registry;

    private final Map<String, ExecutableAction<? super T>> byLine = new ConcurrentHashMap<>();
    private final Map<String, Map<String, ExecutableAction<? super T>>> byKeyAndArgs = new ConcurrentHashMap<>();

    public SimpleActionParser(final @NotNull ActionRegistry<T> registry) {
        this.registry = registry;
    }

    @Override
    public @Nullable ExecutableAction<? super T> parse(final @NotNull String input) {
        final String line = input.trim();

        if (line.isEmpty()) {
            return null;
        }

        final ExecutableAction<? super T> cached = this.byLine.get(line);

        if (cached != null) {
            return cached;
        }

        return this.parseLine(line);
    }

    @Override
    public @Nullable ExecutableAction<? super T> parse(
            final @NotNull String actionStr,
            final @NotNull String args
    ) {
        final String key = actionStr.trim();
        final String query = args.trim();

        final Map<String, ExecutableAction<? super T>> known = this.byKeyAndArgs.get(key);

        if (known != null) {
            final ExecutableAction<? super T> cached = known.get(query);

            if (cached != null) {
                return cached;
            }
        }

        final Action<? super T> action = this.registry.getAction(key);

        if (action == null) {
            return null;
        }

        return this.remember(key, query, action);
    }

    @Override
    public @NotNull ExecutableActionList<? super T> parseAll(final @NotNull Collection<String> inputs) {
        final List<ExecutableAction<? super T>> parsed = new java.util.ArrayList<>(inputs.size());

        for (final String input : inputs) {
            final ExecutableAction<? super T> action = this.parse(input);

            if (action != null) {
                parsed.add(action);
            }
        }

        return new ExecutableActionList<>(parsed);
    }

    private @Nullable ExecutableAction<? super T> parseLine(final @NotNull String line) {
        final int length = line.length();
        final char first = line.charAt(0);

        final String key;
        int index;

        if (first == '[' || first == '(' || first == '<') {
            final char closing = first == '[' ? ']' : first == '(' ? ')' : '>';

            final int closeIndex = line.indexOf(closing, 1);

            if (closeIndex == -1) {
                return null;
            }

            key = line.substring(1, closeIndex).trim();
            index = closeIndex + 1;

        } else {
            final int colonIndex = line.indexOf(':');
            final int spaceIndex = line.indexOf(' ');

            if (colonIndex == -1 && spaceIndex == -1) {
                final Action<? super T> action = this.registry.getAction(line);

                if (action == null) {
                    return null;
                }

                final ExecutableAction<? super T> executable = this.remember(line, "", action);

                this.byLine.put(line, executable);

                return executable;
            }

            index = colonIndex == -1
                    ? spaceIndex
                    : spaceIndex == -1 ? colonIndex : Math.min(colonIndex, spaceIndex);
            key = line.substring(0, index).trim();
        }

        while (index < length) {
            final char current = line.charAt(index);

            if (current != ' ' && current != ':') {
                break;
            }

            index++;
        }

        final String args = index >= length ? "" : line.substring(index).trim();

        final Action<? super T> action = this.registry.getAction(key);

        if (action == null) {
            return null;
        }

        final ExecutableAction<? super T> executable = this.remember(key, args, action);

        this.byLine.put(line, executable);

        return executable;
    }

    private @NotNull ExecutableAction<? super T> remember(
            final @NotNull String key,
            final @NotNull String args,
            final @NotNull Action<? super T> action
    ) {
        final ExecutableAction<? super T> executable = new ExecutableAction<>(action, args);

        if (this.byKeyAndArgs.size() < CACHE_LIMIT) {
            this.byKeyAndArgs
                    .computeIfAbsent(key, ignored -> new ConcurrentHashMap<>())
                    .putIfAbsent(args, executable);
        }

        if (this.byLine.size() >= CACHE_LIMIT) {
            this.byLine.clear();
        }

        return executable;
    }

}
