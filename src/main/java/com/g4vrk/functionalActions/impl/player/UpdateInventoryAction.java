package com.g4vrk.functionalActions.actions.impl.player;

import com.g4vrk.functionalActions.actions.AbstractAction;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class UpdateInventoryAction extends AbstractAction<Player> {

    public UpdateInventoryAction() {
        super(new NamespacedKey("functionallib", "update-inventory"));
    }

    @Override
    public void execute(Player player, String args) {
        if (player == null) return;

        player.updateInventory();
    }
}
