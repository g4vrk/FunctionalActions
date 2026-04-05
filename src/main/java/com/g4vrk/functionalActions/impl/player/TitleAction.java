package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.fastTextFormatter.TextFormatter;
import com.g4vrk.functionalActions.AbstractAction;
import com.g4vrk.functionalActions.util.SendUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TitleAction extends AbstractAction<Player> {

    private final TextFormatter textFormatter = TextFormatter.textFormatter();

    public TitleAction() {
        super("title");
    }

    @Override
    public void execute(@NotNull Player player, @NotNull String args) {
        if (args.isBlank()) return;

        String[] parts = args.split(";");

        Component title = textFormatter.format(parts[0]);
        Component subtitle = parts.length > 1 ? textFormatter.format(parts[1]) : Component.empty();

        SendUtil.sendTitle(player, title, subtitle, 3, 1, 3);
    }
}
