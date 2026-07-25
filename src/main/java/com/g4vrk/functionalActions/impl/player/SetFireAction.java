package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.util.time.TimeParser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SetFireAction implements Action<Player> {

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) {
            return;
        }

        player.setFireTicks(
                (int) (TimeParser.parse(args).toMillis() / 50L)
        );
    }
}