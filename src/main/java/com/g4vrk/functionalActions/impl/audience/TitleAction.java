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

    private final Function<String, Component> textMapper;

    public TitleAction(
            @NotNull String splitter,
            @NotNull Function<String, Component> textMapper
    ) {
        this.splitter = splitter;
        this.textMapper = textMapper;
    }

    @Override
    public void execute(@NotNull Audience audience, @Nullable String args) {
        if (args == null || args.isBlank()) return;

        final String[] parts = args.split(splitter);

        if (parts.length < 1) return;

        Component title = textMapper.apply(parts[0]);
        Component subtitle = parts.length > 1 ? textMapper.apply(parts[1]) : Component.empty();

        audience.showTitle(Title.title(title, subtitle));
    }
}
