package com.g4vrk.functionalActions.actions.impl.player;

import com.g4vrk.functionalActions.actions.AbstractAction;
import com.g4vrk.functionalActions.actions.util.SendUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class SoundAction extends AbstractAction<Player> {

    public SoundAction() {
        super(new NamespacedKey("functionallib", "sound"));
    }

    @Override
    public void execute(Player player, String args) {
        if (args == null || args.isBlank() || player == null) return;

        String[] parts = args.split(";", 3);

        String soundStr = parts.length > 0 ? parts[0].trim() : "";
        float volume = parts.length > 1 ? parseFloat(parts[1]) : 1f;
        float pitch = parts.length > 2 ? parseFloat(parts[2]) : 1f;

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

            sound = Sound.sound(Key.key(keyStr), Sound.Source.MASTER, volume, pitch);

            SendUtil.playSound(player, sound);

        } catch (Throwable t) {
            t.printStackTrace();
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
