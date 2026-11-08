package com.g4vrk.functionalActions.list;

import com.g4vrk.functionalActions.ExecutableAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * The parsed action lines of one config entry, run in order.
 *
 * <p>{@link #run(Object)} does not wrap the arguments in an identity function: an action without
 * a pre-processor is called directly, so a list costs one call per line.</p>
 *
 * @param <T> the context the actions run for
 */
public class ExecutableActionList<T> implements Iterable<ExecutableAction<? super T>> {

    private final List<ExecutableAction<? super T>> actions;

    public ExecutableActionList(final @NotNull List<ExecutableAction<? super T>> actions) {
        this.actions = new ArrayList<>(actions);
    }

    /**
     * Runs every line with the arguments it was parsed with.
     *
     * @param ctx the context
     */
    public void run(final @NotNull T ctx) {
        Objects.requireNonNull(ctx, "context is null");

        for (int index = 0; index < this.actions.size(); index++) {
            this.actions.get(index).execute(ctx);
        }
    }

    /**
     * Runs every line after its arguments went through a pre-processor.
     *
     * @param ctx          the context
     * @param preProcessor what turns the arguments of a line into the ones its action gets,
     *                     usually a placeholder replacer
     */
    public void run(final @NotNull T ctx, final @NotNull UnaryOperator<String> preProcessor) {
        Objects.requireNonNull(ctx, "context is null");
        Objects.requireNonNull(preProcessor, "preProcessor is null");

        for (int index = 0; index < this.actions.size(); index++) {
            this.actions.get(index).execute(ctx, preProcessor);
        }
    }

    /**
     * Appends a parsed line.
     *
     * @param action the line
     */
    public void add(final @NotNull ExecutableAction<T> action) {
        this.actions.add(action);
    }

    /**
     * @param index the position
     * @return the line, or {@code null} when the position is out of range
     */
    public @Nullable ExecutableAction<? super T> get(final int index) {
        if (index < 0 || index >= this.actions.size()) {
            return null;
        }

        return this.actions.get(index);
    }

    /**
     * @return the first line, or {@code null} when the list is empty
     */
    public @Nullable ExecutableAction<? super T> getFirst() {
        return this.actions.isEmpty() ? null : this.actions.get(0);
    }

    /**
     * @return the last line, or {@code null} when the list is empty
     */
    public @Nullable ExecutableAction<? super T> getLast() {
        return this.actions.isEmpty() ? null : this.actions.get(this.actions.size() - 1);
    }

    public boolean isEmpty() {
        return this.actions.isEmpty();
    }

    public int size() {
        return this.actions.size();
    }

    public @NotNull Stream<ExecutableAction<? super T>> stream() {
        return this.actions.stream();
    }

    @Override
    public @NotNull Iterator<ExecutableAction<? super T>> iterator() {
        return this.actions.iterator();
    }

    @Override
    public void forEach(final @NotNull Consumer<? super ExecutableAction<? super T>> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");

        for (int index = 0; index < this.actions.size(); index++) {
            consumer.accept(this.actions.get(index));
        }
    }

}
