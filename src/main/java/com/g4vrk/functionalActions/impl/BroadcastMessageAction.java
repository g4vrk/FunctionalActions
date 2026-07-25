package com.g4vrk.functionalActions.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static org.bukkit.Bukkit.getServer;

public class BroadcastMessageAction extends UncontextualAction {

    private final Function<String, Component> textMapper;

    public BroadcastMessageAction(
            @NotNull Function<String, Component> textMapper
    ) {
        this.textMapper = textMapper;
    }

    @Override
    protected void execute(@Nullable String args) {

        final Component text = args == null || args.isBlank() ? Component.empty() : textMapper.apply(args);

        for (final Player player : getServer().getOnlinePlayers()) {
            player.sendMessage(text);
        }

    }
}
