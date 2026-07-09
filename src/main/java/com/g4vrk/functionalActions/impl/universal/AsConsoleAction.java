package com.g4vrk.functionalActions.impl.universal;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AsConsoleAction extends UncontextualAction {

    private final Server server = Bukkit.getServer();

    public AsConsoleAction() {
        super("console", List.of("console-command", "command-as-console", "console-cmd"));
    }

    @Override
    public void execute(@NotNull String args) {
        if (args.isBlank()) return;

        server.dispatchCommand(server.getConsoleSender(), args);
    }
}
