package com.g4vrk.functionalActions.impl.audience;

import com.g4vrk.functionalActions.Action;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class MessageAction implements Action<Audience> {

    private final java.util.function.BiFunction<Audience, String, Component> textMapper;

    public MessageAction(
            @NotNull Function<String, Component> textMapper
    ) {
        this.textMapper = (audience, text) -> textMapper.apply(text);
    }

    /** Context is resolved at execution time; player-specific components are never cached. */
    public MessageAction(java.util.function.BiFunction<Audience, String, Component> textMapper) {
        this.textMapper = java.util.Objects.requireNonNull(textMapper);
    }

    @Override
    public void execute(@NotNull Audience audience, @Nullable String args) {
        if (args == null || args.isBlank()) {
            audience.sendMessage(Component.empty());
            return;
        }

        audience.sendMessage(textMapper.apply(audience, args));
    }
}
