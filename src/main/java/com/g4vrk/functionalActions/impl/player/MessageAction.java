package com.g4vrk.functionalActions.impl.player;

import com.g4vrk.functionalActions.AbstractAction;
import com.g4vrk.functionalActions.util.SendUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAction extends AbstractAction<Player> {

    public MessageAction() {
        super("message", List.of("msg"));
    }

    @Override
    public void execute(@NotNull Player context, @NotNull String args) {
        if (args.isBlank()) return;

        SendUtil.sendMessage(context, args);
    }
}
