package com.g4vrk.functionalActions.list;

import com.g4vrk.functionalActions.ExecutableAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ExecutableActionList<T> implements Iterable<ExecutableAction<T>> {
    private final List<ExecutableAction<T>> actions;

    public ExecutableActionList(
            @NotNull List<ExecutableAction<T>> actions
    ) {
        this.actions = actions;
    }

    public void run(final @NotNull T ctx) {
        run(ctx, UnaryOperator.identity());
    }

    public void run(final @NotNull T ctx, final @NotNull UnaryOperator<String> preProcessor) {
        Objects.requireNonNull(ctx, "context is null");
        Objects.requireNonNull(preProcessor, "preProcessor is null");

        for (final ExecutableAction<T> action : actions) {
            action.execute(ctx, preProcessor);
        }
    }

    public void add(final @NotNull ExecutableAction<T> action) {
        actions.add(action);
    }

    public @Nullable ExecutableAction<T> get(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }

        return actions.get(index);
    }

    public @Nullable ExecutableAction<T> getFirst() {
        if (isEmpty()) {
            return null;
        }

        return actions.get(0);
    }

    public @Nullable ExecutableAction<T> getLast() {
        if (isEmpty()) {
            return null;
        }

        return actions.get(size() - 1);
    }

    public boolean isEmpty() {
        return actions.isEmpty();
    }

    public int size() {
        return actions.size();
    }

    public @NotNull Stream<ExecutableAction<T>> stream() {
        return actions.stream();
    }

    @Override
    public @NotNull Iterator<ExecutableAction<T>> iterator() {
        return actions.iterator();
    }

    @Override
    public void forEach(final @NotNull Consumer<? super ExecutableAction<T>> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");

        for (final ExecutableAction<T> action : actions) {
            consumer.accept(action);
        }
    }
}
