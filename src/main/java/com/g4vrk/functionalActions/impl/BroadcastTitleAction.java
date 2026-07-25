package com.g4vrk.functionalActions.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static org.bukkit.Bukkit.getServer;

public class BroadcastTitleAction extends UncontextualAction {

    private final String splitter;

    private final Function<String, Component> textMapper;

    public BroadcastTitleAction(
            @NotNull String splitter,
            @NotNull Function<String, Component> textMapper
    ) {
        this.splitter = splitter;
        this.textMapper = textMapper;
    }

    @Override
    protected void execute(@Nullable String args) {

        if (args == null || args.isBlank()) return;

        for (final Player player : getServer().getOnlinePlayers()) {

            final String[] parts = args.split(splitter);

            if (parts.length < 1) return;

            Component title = textMapper.apply(parts[0]);
            Component subtitle = parts.length > 1 ? textMapper.apply(parts[1]) : Component.empty();

            player.showTitle(Title.title(title, subtitle));
        }

    }
}
