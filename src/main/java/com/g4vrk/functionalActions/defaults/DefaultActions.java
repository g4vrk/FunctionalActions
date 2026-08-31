package com.g4vrk.functionalActions.defaults;

import com.g4vrk.functionalActions.impl.BroadcastActionBarAction;
import com.g4vrk.functionalActions.impl.BroadcastMessageAction;
import com.g4vrk.functionalActions.impl.BroadcastTitleAction;
import com.g4vrk.functionalActions.impl.ConsoleCommandAction;
import com.g4vrk.functionalActions.impl.audience.ActionBarAction;
import com.g4vrk.functionalActions.impl.audience.MessageAction;
import com.g4vrk.functionalActions.impl.audience.SoundAction;
import com.g4vrk.functionalActions.impl.audience.TitleAction;
import com.g4vrk.functionalActions.impl.player.*;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class DefaultActions {

    private DefaultActions() {}

    private static void registerCommon(
            final @NotNull ActionRegistry<? extends Audience> registry,
            final @NotNull Function<String, Component> textMapper,
            final @NotNull String splitter
    ) {

        registry.register(new MessageAction(textMapper),
                "message", "msg");

        registry.register(new ActionBarAction(textMapper),
                "actionbar", "bar");

        registry.register(new TitleAction(splitter, textMapper),
                "title", "show-title");

        registry.register(new SoundAction(splitter),
                "sound", "play-sound");

        registry.register(new BroadcastMessageAction(textMapper),
                "broadcast", "bc");

        registry.register(new BroadcastActionBarAction(textMapper),
                "broadcast-actionbar", "broadcast-bar", "bcbar");

        registry.register(new BroadcastTitleAction(splitter, textMapper),
                "broadcast-title", "bctitle");

        registry.register(new ConsoleCommandAction(),
                "console-command", "console", "cmd");

    }

    public static final class Player {

        public static void registerDefaults(
                final @NotNull ActionRegistry<? extends org.bukkit.entity.Player> registry,
                final @NotNull Function<String, Component> textMapper,
                final @NotNull String splitter,
                final @NotNull Function<String, Long> tickDurationParser
        ) {
            registerCommon(registry, textMapper, splitter);

            registry.register(new PlayerCommandAction(),
                    "player", "command-as-player", "player-command", "player-cmd");

            registry.register(new TeleportAction(splitter),
                    "teleport", "tp");

            registry.register(new PotionEffectAction(tickDurationParser),
                    "effect", "potion");

            registry.register(new SetHealthAction(),
                    "health", "hp", "set-health", "set-hp");

            registry.register(new SetFoodLevelAction(),
                    "food", "feed", "set-food", "set-food-level");

            registry.register(new GiveExpAction(),
                    "exp", "experience", "give-exp");

            registry.register(new SetGameModeAction(),
                    "gamemode", "gm", "set-game-mode", "set-gm");

            registry.register(new SetVelocityAction(),
                    "velocity", "vel", "set-velocity");

            registry.register(new SetFireAction(tickDurationParser),
                    "fire", "burn", "set-fire", "set-fire-time");

            registry.register(new UpdateInventoryAction(),
                    "update-inventory", "updateinv", "update-inv");

            registry.register(new CloseInventoryAction(),
                    "close-inventory", "closeinv",  "close-inv");
        }

    }

    public static final class Adventure {

        public static void registerDefaults(
                final @NotNull ActionRegistry<Audience> registry,
                final @NotNull Function<String, Component> textMapper,
                final @NotNull String splitter
        ) {

            registerCommon(registry, textMapper, splitter);

        }

    }

}