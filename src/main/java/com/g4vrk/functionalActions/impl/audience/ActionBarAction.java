package com.g4vrk.functionalActions.impl.audience;

import com.g4vrk.functionalActions.AbstractAction;
import com.g4vrk.functionalActions.util.SendUtil;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActionBarAction extends AbstractAction<Audience> {

    public ActionBarAction() {
        super("action-bar", List.of("actionbar"));
    }

    @Override
    public void execute(@NotNull Audience audience, @NotNull String args) {
        if (args.isBlank()) return;

        SendUtil.sendActionBar(audience, args);
    }
}
