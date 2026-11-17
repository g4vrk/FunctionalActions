package com.g4vrk.functionalActions.impl.player;

import com.cryptomorin.xseries.XPotion;
import com.g4vrk.functionalActions.Action;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Applies a potion effect using the names supported by the running server.
 */
public final class PotionEffectAction implements Action<Player> {

    private final Function<String, Long> tickDurationParser;

    /**
     * @param tickDurationParser converts the configured duration into server ticks
     */
    public PotionEffectAction(final @NotNull Function<String, Long> tickDurationParser) {

        this.tickDurationParser = tickDurationParser;

    }

    @Override
    public void execute(final @NotNull Player player, final @Nullable String args) {

        if (args == null || args.isBlank()) {
            return;
        }

        final String[] split = args.split(",");
        if (split.length == 0) {
            return;
        }

        final PotionEffectType type = XPotion.REGISTRY.getByName(split[0].trim()).map(XPotion::get).orElse(null);
        if (type == null) {
            return;
        }

        final int amplifier = split.length >= 2 ? Integer.parseInt(split[1]) : 0;
        final int duration = split.length >= 3 ? Math.toIntExact(this.tickDurationParser.apply(split[2])) : Integer.MAX_VALUE;
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
