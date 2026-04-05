package com.g4vrk.functionalActions.actions.impl.player;

import com.g4vrk.functionalActions.actions.AbstractAction;
import com.g4vrk.functionalActions.actions.util.SendUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("deprecation")
public class MessageAction extends AbstractAction<Player> {

    public MessageAction() {
        super(new NamespacedKey("functionallib", "message"), List.of("msg"));
    }

    @Override
    public void execute(Player context, String args) {
        if (args == null || args.isBlank() || context == null) return;

        SendUtil.sendMessage(context, args);
    }
}
