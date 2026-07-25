package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerCommandAction implements Action<Player> {

    @Override
    public void execute(@NotNull Player context, @Nullable String args) {
        if (args == null || args.isBlank()) return;

        final Server server = context.getServer();

        server.dispatchCommand(context, args);
    }
}
