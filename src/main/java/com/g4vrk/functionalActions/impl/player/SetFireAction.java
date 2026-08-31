package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class SetFireAction implements Action<Player> {

    private final Function<String, Long> tickDurationParser;

    public SetFireAction(
            @NotNull Function<String, Long> tickDurationParser
    ) {
        this.tickDurationParser = tickDurationParser;
    }

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) {
            return;
        }

        player.setFireTicks(
                Math.toIntExact(tickDurationParser.apply(args))
        );
    }
}