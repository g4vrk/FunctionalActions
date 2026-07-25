package com.g4vrk.functionalActions.impl;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.Nullable;

public class ConsoleCommandAction extends UncontextualAction {

    private final Server server = Bukkit.getServer();

    @Override
    public void execute(@Nullable String args) {
        if (args == null || args.isBlank()) return;

        server.dispatchCommand(server.getConsoleSender(), args);
    }

}
