package com.g4vrk.functionalActions.registry;

import com.g4vrk.functionalActions.Action;
import com.g4vrk.functionalActions.defaults.DefaultActionRegistry;
import com.g4vrk.functionalActions.defaults.impl.AudienceActionRegistry;
import com.g4vrk.functionalActions.defaults.impl.PlayerActionRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public final class ActionRegistries {

    private static final Map<Class<?>, List<DefaultActionRegistry<?>>> PROVIDERS = new ConcurrentHashMap<>();

    {
        registerProvider(new PlayerActionRegistry());
        registerProvider(new AudienceActionRegistry());
    }

    public static <T> void registerProvider(
            final @NotNull DefaultActionRegistry<T> provider
    ) {
        PROVIDERS.computeIfAbsent(provider.getType(), c -> new ArrayList<>()).add(provider);
    }

    public static  <T> Collection<Action<? super T>> getDefaultActions(
            final @NotNull Class<T> type
    ) {
        List<Action<? super T>> result = new ArrayList<>();

        List<DefaultActionRegistry<?>> list = PROVIDERS.get(type);
        if (list == null) return result;

        for (DefaultActionRegistry<?> provider : list) {
            for (Action<?> action : provider.getActions()) {
                result.add((Action<? super T>) action);
            }
        }

        return result;
    }

    public static <T> boolean hasDefaults(
            final @NotNull Class<T> type
    ) {
        return PROVIDERS.containsKey(type);
    }
}
