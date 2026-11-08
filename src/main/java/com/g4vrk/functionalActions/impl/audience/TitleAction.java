package com.g4vrk.functionalActions.impl.audience;

import com.g4vrk.functionalActions.Action;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TitleAction implements Action<Audience> {

    private final String splitter;

    private final java.util.function.BiFunction<Audience, String, Component> textMapper;

    public TitleAction(
            @NotNull String splitter,
            @NotNull Function<String, Component> textMapper
    ) {
        this.splitter = splitter;
        this.textMapper = (audience, text) -> textMapper.apply(text);
    }

    public static TitleAction contextual(@NotNull String splitter, java.util.function.BiFunction<Audience, String, Component> textMapper) {
        return new TitleAction(splitter, textMapper);
    }

    /** Context is resolved at execution time; player-specific components are never cached. */
    private TitleAction(@NotNull String splitter, java.util.function.BiFunction<Audience, String, Component> textMapper) {
        this.splitter = java.util.Objects.requireNonNull(splitter);
        this.textMapper = java.util.Objects.requireNonNull(textMapper);
    }

    @Override
    public void execute(@NotNull Audience audience, @Nullable String args) {
        if (args == null || args.isBlank()) return;

        final String[] parts = args.split(splitter);

        if (parts.length < 1) return;

        Component title = textMapper.apply(audience, parts[0]);
        Component subtitle = parts.length > 1 ? textMapper.apply(audience, parts[1]) : Component.empty();

        audience.showTitle(Title.title(title, subtitle));
    }
}
