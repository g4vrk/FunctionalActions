package com.g4vrk.functionalActions.actions.impl.player;

import com.g4vrk.functionalActions.actions.AbstractAction;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class CloseMenuAction extends AbstractAction<Player> {

    public CloseMenuAction() {
        super(new NamespacedKey("functionallib", "close-menu"));
    }

    @Override
    public void execute(Player player, String args) {
        if (player == null) return;

        player.closeInventory();
    }
}
