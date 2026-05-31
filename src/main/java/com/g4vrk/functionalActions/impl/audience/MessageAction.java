package com.g4vrk.functionalActions.impl.audience;

import com.g4vrk.functionalActions.AbstractAction;
import com.g4vrk.functionalActions.util.SendUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAction extends AbstractAction<Audience> {

    public MessageAction() {
        super("message", List.of("msg"));
    }

    @Override
    public void execute(@NotNull Audience audience, @NotNull String args) {
        if (args.isBlank()) return;

        SendUtil.sendMessage(audience, args);
    }
}
