package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.AbstractAction;
import com.g4vrk.functionalActions.util.SendUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActionBarAction extends AbstractAction<Player> {

    public ActionBarAction() {
        super("action-bar", List.of("actionbar"));
    }

    @Override
    public void execute(@NotNull Player player, @NotNull String args) {
        if (args.isBlank()) return;

        SendUtil.sendActionBar(player, args);
    }
}
