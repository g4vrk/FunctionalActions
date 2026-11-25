package com.g4vrk.functionalActions;

import com.g4vrk.functionalActions.defaults.DefaultActionSet;
import com.g4vrk.functionalActions.registry.ActionRegistry;
import com.g4vrk.functionalActions.registry.impl.SimpleActionRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Locks the platform boundary between shared Audience actions and Bukkit actions.
 */
class DefaultActionSetTest {

    @Test
    void adventureDefaultsDoNotRegisterBukkitConsoleCommands() {
        final ActionRegistry<Audience> registry = new SimpleActionRegistry<>();

        DefaultActionSet.Adventure.registerDefaults(registry, Component::text, "|");

        assertTrue(registry.contains("message"));
        assertFalse(registry.contains("console-command"));
        assertFalse(registry.contains("console"));
        assertFalse(registry.contains("cmd"));
    }

}
