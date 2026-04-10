package com.g4vrk.functionalActions;

import com.g4vrk.functionalActions.registry.ActionRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class ActionExecutor<T> {

    private final ActionRegistry<T> registry;
    private final Logger logger;

    public ActionExecutor(@NotNull ActionRegistry<T> registry, @NotNull Logger logger) {
        this.registry = registry;
        this.logger = logger;
    }

    public void runActions(@NotNull T context, @NotNull List<String> actionList, @NotNull UnaryOperator<String> preProcessor) {
        for (String actionStr : actionList) {
            executeSingle(context, preProcessor.apply(actionStr));
        }
    }

    public void runActions(@NotNull T context, @NotNull List<String> actionList) {
        runActions(context, actionList, UnaryOperator.identity());
    }

    public void runAction(@NotNull T context, @NotNull String actionLine) {
        executeSingle(context, actionLine);
    }

    public @NotNull ActionRegistry<T> getRegistry() {
        return registry;
    }

    private void executeSingle(T context, String actionStr) {

        if (actionStr == null || actionStr.isBlank())
            return;

        actionStr = actionStr.trim();

        String[] parts = splitAction(actionStr);

        String rawKey = parts[0];
        String key = normalizeKey(rawKey);
        String args = parts[1];

        Optional<Action<T>> optionalAction = registry.getAction(key);

        if (optionalAction.isEmpty()) {
            logger.error("Неизвестное действие: {}", rawKey);
            return;
        }

        Action<T> action = optionalAction.get();

        action.execute(context, args);
    }

    private String[] splitAction(String input) {

        int colonIndex = input.indexOf(':');
        int spaceIndex = input.indexOf(' ');

        if (colonIndex == -1 && spaceIndex == -1) {
            return new String[]{input, ""};
        }

        int splitIndex;

        if (colonIndex == -1) splitIndex = spaceIndex;
        else if (spaceIndex == -1) splitIndex = colonIndex;
        else splitIndex = Math.min(colonIndex, spaceIndex);

        String key = input.substring(0, splitIndex).trim();
        String args = input.substring(splitIndex + 1).trim();

        return new String[]{key, args};
    }

    private String normalizeKey(String input) {
        String key = input.toLowerCase().trim();

        if (key.endsWith(":")) {
            key = key.substring(0, key.length() - 1);
        }

        if ((key.startsWith("[") && key.endsWith("]")) ||
                (key.startsWith("(") && key.endsWith(")"))) {
            key = key.substring(1, key.length() - 1);
        }

        return key;
    }
}
