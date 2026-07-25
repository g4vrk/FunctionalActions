package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SetFoodLevelAction implements Action<Player> {

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) return;

        final int food = Integer.parseInt(args);

        player.setFoodLevel(Math.max(0, Math.min(20, food)));
    }
}