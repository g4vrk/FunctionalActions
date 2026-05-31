package com.g4vrk.functionalActions.util;

import com.g4vrk.fastTextFormatter.TextFormatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public final class SendUtil {

    private static final TextFormatter FORMATTER =
            TextFormatter.textFormatter();

    public SendUtil() {
        throw new UnsupportedOperationException();
    }

    private static @NotNull Component format(@NotNull String string) {
        return FORMATTER.format(string);
    }

    public static void sendMessage(@NotNull Audience audience, @NotNull Component component) {
        audience.sendMessage(component);
    }

    public static void sendMessage(@NotNull Audience audience, @NotNull String string) {
        audience.sendMessage(format(string));
    }

    public static void sendActionBar(@NotNull Audience audience, @NotNull Component component) {
        audience.sendActionBar(component);
    }

    public static void sendActionBar(@NotNull Audience audience, @NotNull String string) {
        audience.sendActionBar(format(string));
    }

    public static void sendTitle(
            @NotNull Audience audience,
            @NotNull Component title,
            @NotNull Component subtitle,
            final int fadeIn,
            final int stay,
            final int fadeOut
    ) {
        audience.showTitle(
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
    }

    public static void sendTitle(
            @NotNull Audience audience,
            @NotNull String title,
            @NotNull String subtitle,
            final int fadeIn,
            final int stay,
            final int fadeOut
    ) {
        sendTitle(
                audience,
                format(title),
                format(subtitle),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void playSound(@NotNull Audience audience, @NotNull Sound sound) {
        audience.playSound(sound);
    }
}