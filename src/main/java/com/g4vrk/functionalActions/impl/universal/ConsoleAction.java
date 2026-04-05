package com.g4vrk.functionalActions.impl.emptyContext;

import com.g4vrk.functionalActions.AbstractAction;
import com.g4vrk.functionalActions.EmptyContext;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ConsoleAction extends AbstractAction<EmptyContext> {

    private final Server server = Bukkit.getServer();

    public ConsoleAction() {
        super("console");
    }

    @Override
    public void execute(@NotNull EmptyContext context, @NotNull String args) {
        if (args == null || args.isBlank()) return;

        server.dispatchCommand(server.getConsoleSender(), args);
    }
}
