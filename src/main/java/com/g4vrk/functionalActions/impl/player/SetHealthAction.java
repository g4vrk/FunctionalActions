package com.g4vrk.functionalActions.impl.player;

import com.cryptomorin.xseries.XAttribute;
import com.g4vrk.functionalActions.Action;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sets a player's health up to the maximum health on the running server.
 */
public final class SetHealthAction implements Action<Player> {

    @Override
    public void execute(final @NotNull Player player, final @Nullable String args) {

        if (args == null || args.isBlank()) {
            return;
        }

        final Attribute attribute = XAttribute.MAX_HEALTH.get();
        if (attribute == null) {
            return;
        }

        final double health = Double.parseDouble(args);
        final AttributeInstance maxHealth = player.getAttribute(attribute);
        if (maxHealth == null) {
            return;
        }

        player.setHealth(Math.min(maxHealth.getValue(), Math.max(0D, health)));

    }

}
