package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpdateInventoryAction implements Action<Player> {

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {
        player.updateInventory();
    }

}
