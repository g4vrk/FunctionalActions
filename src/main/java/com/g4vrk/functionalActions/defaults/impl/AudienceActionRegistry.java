package com.g4vrk.functionalActions.defaults.impl;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.defaults.DefaultActionRegistry;
import com.g4vrk.functionalActions.impl.audience.ActionBarAction;
import com.g4vrk.functionalActions.impl.audience.MessageAction;
import com.g4vrk.functionalActions.impl.audience.SoundAction;
import com.g4vrk.functionalActions.impl.audience.TitleAction;
import com.g4vrk.functionalActions.impl.universal.ConsoleAction;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class AudienceActionRegistry implements DefaultActionRegistry<Audience> {
    @Override
    public @NotNull Class<Audience> getType() {
        return Audience.class;
    }

    @Override
    public @NotNull Collection<Action<? super Audience>> getActions() {
        return List.of(
                new ActionBarAction(),
                new MessageAction(),
                new SoundAction(),
                new TitleAction(),
                new ConsoleAction()
        );
    }
}
