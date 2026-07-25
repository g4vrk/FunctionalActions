package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.Action;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TeleportAction implements Action<Player> {

    private final String splitter;

    public TeleportAction(
           @NotNull String splitter
    ) {
        this.splitter = splitter;
    }

    @Override
    public void execute(@NotNull Player player, @Nullable String args) {

        if (args == null || args.isBlank()) return;

        final String[] split = args.split(splitter);

        try {

            switch (split.length) {

                case 3 -> player.teleportAsync(new Location(
                        player.getWorld(),
                        Double.parseDouble(split[0]),
                        Double.parseDouble(split[1]),
                        Double.parseDouble(split[2])
                ));

                case 4 -> {

                    final World world = Bukkit.getWorld(split[0]);

                    if (world == null) {
                        return;
                    }

                    player.teleportAsync(new Location(
                            world,
                            Double.parseDouble(split[1]),
                            Double.parseDouble(split[2]),
                            Double.parseDouble(split[3])
                    ));
                }

                case 6 -> {

                    final World world = Bukkit.getWorld(split[0]);

                    if (world == null) {
                        return;
                    }

                    player.teleportAsync(new Location(
                            world,
                            Double.parseDouble(split[1]),
                            Double.parseDouble(split[2]),
                            Double.parseDouble(split[3]),
                            Float.parseFloat(split[4]),
                            Float.parseFloat(split[5])
                    ));
                }
            }

        } catch (final NumberFormatException ignored) {
        }
    }
}