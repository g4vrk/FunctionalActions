package com.g4vrk.functionalActions.impl.universal;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

public class ConsoleAction extends UncontextualAction {

    private final Server server = Bukkit.getServer();

    public ConsoleAction() {
        super("console");
    }

    @Override
    public void execute(@NotNull String args) {
        if (args.isBlank()) return;

        server.dispatchCommand(server.getConsoleSender(), args);
    }
}
