package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.AbstractAction;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CloseMenuAction extends AbstractAction<Player> {

    public CloseMenuAction() {
        super("close-menu");
    }

    @Override
    public void execute(@NotNull Player player, @NotNull String args) {
        if (player == null) return;

        player.closeInventory();
    }
}
