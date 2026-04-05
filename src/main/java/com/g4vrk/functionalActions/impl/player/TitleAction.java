package com.g4vrk.functionalActions.actions.impl.player;

import com.g4vrk.fastTextFormatter.TextFormatter;
import com.g4vrk.functionalActions.actions.AbstractAction;
import com.g4vrk.functionalActions.actions.util.SendUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class TitleAction extends AbstractAction<Player> {

    private final TextFormatter textFormatter = TextFormatter.textFormatter();

    public TitleAction() {
        super(new NamespacedKey("functionallib", "title"));
    }

    @Override
    public void execute(Player player, String args) {
        if (args == null || args.isBlank() || player == null) return;

        String[] parts = args.split(";");

        Component title = textFormatter.format(parts[0]);
        Component subtitle = parts.length > 1 ? textFormatter.format(parts[1]) : Component.empty();

        SendUtil.sendTitle(player, title, subtitle, 3, 1, 3);
    }
}
