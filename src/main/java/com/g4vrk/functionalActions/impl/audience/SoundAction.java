package com.g4vrk.functionalActions.impl.audience;

import com.cryptomorin.xseries.XSound;
import com.g4vrk.functionalActions.Action;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Plays a sound by its Bukkit alias or resource-pack key.
 */
public class SoundAction implements Action<Audience> {

    private static final Logger LOGGER = Logger.getLogger(SoundAction.class.getName());

    private final String splitter;

    /**
     * @param splitter regular expression separating the sound, volume, and pitch
     */
    public SoundAction(final @NotNull String splitter) {

        this.splitter = splitter;

    }

    @Override
    public void execute(final @NotNull Audience audience, final @Nullable String args) {

        if (args == null || args.isBlank()) {
            return;
        }

        final String[] parts = args.split(this.splitter, 3);
        final String name = parts[0].trim();
        final float volume = parts.length > 1 ? parseFloat(parts[1]) : 1f;
        final float pitch = parts.length > 2 ? parseFloat(parts[2]) : 1f;

        try {
            audience.playSound(Sound.sound(soundKey(name), Sound.Source.MASTER, volume, pitch));
        } catch (final IllegalArgumentException exception) {
            LOGGER.log(Level.WARNING, "Invalid sound action", exception);
        }

    }

    private static @NotNull Key soundKey(final @NotNull String name) {

        if (name.indexOf(':') < 0) {
            final Keyed sound = XSound.of(name).map(XSound::get).orElse(null);
            if (sound != null) {
                return Key.key(sound.getKey().toString());
            }
        }
        return Key.key(name);

    }

    private static float parseFloat(final @NotNull String value) {

        try {
            return Float.parseFloat(value);
        } catch (final NumberFormatException ignored) {
            return 1f;
        }

    }

}
