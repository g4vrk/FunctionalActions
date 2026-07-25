package com.g4vrk.functionalActions.impl.audience;

import com.g4vrk.functionalActions.Action;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoundAction implements Action<Audience> {

    private final String splitter;

    public SoundAction(
            @NotNull String splitter
    ) {
        this.splitter = splitter;
    }

    @Override
    public void execute(@NotNull Audience audience, @Nullable String args) {
        if (args == null || args.isBlank()) return;

        final String[] parts = args.split(splitter, 3);

        final String soundStr = parts.length > 0 ? parts[0].trim() : "";
        final float volume = parts.length > 1 ? parseFloat(parts[1]) : 1f;
        final float pitch = parts.length > 2 ? parseFloat(parts[2]) : 1f;

        try {
            Sound sound;

            org.bukkit.Sound bukkitSound = null;
            try {
                bukkitSound = org.bukkit.Sound.valueOf(soundStr.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }

            String keyStr;
            if (bukkitSound != null) {
                keyStr = bukkitSound.getKey().asString();
            } else {
                keyStr = soundStr.contains(":") ? soundStr : "minecraft:" + soundStr;
            }

            //noinspection PatternValidation
            sound = Sound.sound(Key.key(keyStr), Sound.Source.MASTER, volume, pitch);

            audience.playSound(sound);

        } catch (final Throwable th) {
            //noinspection CallToPrintStackTrace
            th.printStackTrace();
        }
    }

    private float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return (float) 1.0;
        }
    }
}
