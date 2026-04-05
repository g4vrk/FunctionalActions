package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.AbstractAction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UpdateInventoryAction extends AbstractAction<Player> {

    public UpdateInventoryAction() {
        super("update-inventory");
    }

    @Override
    public void execute(@NotNull Player player, @NotNull String args) {
        player.updateInventory();
    }
}
