package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.util.time.TimeParser;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PotionEffectAction implements Action<Player> {

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
            duration = (int) (TimeParser.parse(split[2]).toMillis() / 50L);
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