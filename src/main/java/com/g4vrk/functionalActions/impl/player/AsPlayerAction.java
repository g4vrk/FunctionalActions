package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.AbstractAction;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AsPlayerAction extends AbstractAction<Player> {

    public AsPlayerAction() {
        super("player", List.of("as-player", "cmd-as-player", "run-as-player"));
    }

    @Override
    public void execute(@NotNull Player context, @NotNull String args) {
        if (args.isBlank()) return;

        final Server server = context.getServer();

        server.dispatchCommand(context, args);
    }
}
