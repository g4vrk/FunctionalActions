package com.g4vrk.functionalActions.actions.util;

import com.g4vrk.fastTextFormatter.TextFormatter;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public final class SendUtil {

    private static final LegacyComponentSerializer LEGACY_SERIALIZER =
            LegacyComponentSerializer.legacySection();

    private static final TextFormatter FORMATTER =
            TextFormatter.textFormatter();

    private static final boolean NATIVE_SUPPORT = hasNativeSupport();
    
    private SendUtil() {
        throw new UnsupportedOperationException();
    }

    private static @NotNull Component format(@NotNull String string) {
        return FORMATTER.format(string);
    }

    private static @NotNull String legacy(@NotNull Component component) {
        return LEGACY_SERIALIZER.serialize(component);
    }

    private static @NotNull String legacy(@NotNull String string) {
        return FORMATTER.legacy(string);
    }

    public static void sendMessage(@NotNull Player player, @NotNull Component component) {
        if (NATIVE_SUPPORT) {
            player.sendMessage(component);
            return;
        }

        player.sendMessage(legacy(component));
    }

    public static void sendMessage(@NotNull Player player, @NotNull String string) {
        if (NATIVE_SUPPORT) {
            player.sendMessage(format(string));
            return;
        }

        player.sendMessage(legacy(string));
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull Component component) {
        if (sender instanceof Player player) {
            sendMessage(player, component);
            return;
        }

        sender.sendMessage(legacy(component));
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String string) {
        if (sender instanceof Player player) {
            sendMessage(player, string);
            return;
        }

        sender.sendMessage(legacy(string));
    }

    public static void sendActionBar(@NotNull Player player, @NotNull Component component) {
        if (NATIVE_SUPPORT) {
            player.sendActionBar(component);
            return;
        }

        player.sendActionBar(legacy(component));
    }

    public static void sendActionBar(@NotNull Player player, @NotNull String string) {
        if (NATIVE_SUPPORT) {
            player.sendActionBar(format(string));
            return;
        }

        player.sendActionBar(legacy(string));
    }

    public static void sendTitle(
            @NotNull Player player,
            @NotNull Component title,
            @NotNull Component subtitle,
            final int fadeIn,
            final int stay,
            final int fadeOut
    ) {
        if (NATIVE_SUPPORT) {
            player.showTitle(
                    Title.title(
                            title,
                            subtitle,
                            Title.Times.times(
                                    Duration.ofMillis(fadeIn),
                                    Duration.ofMillis(stay),
                                    Duration.ofMillis(fadeOut)
                            )
                    )
            );
            return;
        }

        player.sendTitle(
                legacy(title),
                legacy(subtitle),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void sendTitle(
            @NotNull Player player,
            @NotNull String title,
            @NotNull String subtitle,
            final int fadeIn,
            final int stay,
            final int fadeOut
    ) {
        sendTitle(
                player,
                format(title),
                format(subtitle),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void playSound(@NotNull Player player, @NotNull Sound sound) {
        if (NATIVE_SUPPORT) {
            player.playSound(sound);
            return;
        }

        player.playSound(
                player.getLocation(),
                sound.name().asString(),
                SoundCategory.MASTER,
                sound.volume(),
                sound.pitch()
        );
    }

    public static boolean hasNativeSupport() {
        Class<Player> clazz = Player.class;

        String name = "sendMessage";
        Class<?> params = Component.class;

        try {
            clazz.getMethod(name, params);
            return true;
        } catch (NoSuchMethodException ignored) {
            try {
                clazz.getDeclaredMethod(name, params);
                return true;
            } catch (NoSuchMethodException ignored2) {
                return false;
            }
        }
    }
}