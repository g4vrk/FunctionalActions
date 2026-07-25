package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SetHealthAction implements Action<Player> {

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) return;

        final double health = Double.parseDouble(args);

        final @Nullable AttributeInstance maxHealth = player
                .getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (maxHealth == null) return;

        player.setHealth(Math.min(maxHealth.getValue(), Math.max(0D, health)));
    }
}