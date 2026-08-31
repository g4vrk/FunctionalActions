package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class PotionEffectAction implements Action<Player> {

    private final Function<String, Long> tickDurationParser;

    public PotionEffectAction(
            @NotNull Function<String, Long> tickDurationParser
    ) {
        this.tickDurationParser = tickDurationParser;
    }

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) {
            return;
        }

        final String[] split = args.split(",");

        final PotionEffectType type = PotionEffectType.getByName(split[0].toUpperCase());

        if (type == null) {
            return;
        }

        int amplifier = 0;
        int duration = Integer.MAX_VALUE;

        if (split.length >= 2) {
            amplifier = Integer.parseInt(split[1]);
        }

        if (split.length >= 3) {
            duration = Math.toIntExact(tickDurationParser.apply(split[2]));
        }

        player.addPotionEffect(new PotionEffect(
                type,
                duration,
                amplifier,
                true,
                true,
                true
        ));
    }
}