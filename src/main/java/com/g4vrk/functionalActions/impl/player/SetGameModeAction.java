package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SetGameModeAction implements Action<Player> {

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) return;

        try {
            player.setGameMode(GameMode.valueOf(args.toUpperCase()));
        } catch (IllegalArgumentException ignored) {
        }
    }
}