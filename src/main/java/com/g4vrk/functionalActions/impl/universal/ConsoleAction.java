package com.g4vrk.functionalActions.impl.universal;

import com.g4vrk.functionalActions.AbstractAction;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConsoleAction extends AbstractAction<Player> {

    private final Server server = Bukkit.getServer();

    public ConsoleAction() {
        super("console");
    }

    @Override
    public void execute(@NotNull Player context, @NotNull String args) {
        if (args.isBlank()) return;

        server.dispatchCommand(server.getConsoleSender(), args);
    }
}
