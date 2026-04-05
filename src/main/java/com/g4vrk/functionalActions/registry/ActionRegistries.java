package com.g4vrk.functionalActions.actions.registry;

import com.g4vrk.functionalActions.actions.Action;
import com.g4vrk.functionalActions.actions.defaults.DefaultActionRegistry;
import com.g4vrk.functionalActions.actions.defaults.impl.PlayerActionRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public final class ActionRegistries {

    private final Map<Class<?>, List<DefaultActionRegistry<?>>> providers = new ConcurrentHashMap<>();

    {
        registerProvider(new PlayerActionRegistry());
    }

    public <T> void registerProvider(DefaultActionRegistry<T> provider) {
        providers.computeIfAbsent(provider.getType(), k -> new ArrayList<>()).add(provider);
    }

    public <T> Collection<Action<T>> getDefaultActions(Class<T> type) {
        List<Action<T>> result = new ArrayList<>();

        List<DefaultActionRegistry<?>> list = providers.get(type);
        if (list == null) return result;

        for (DefaultActionRegistry<?> provider : list) {
            for (Action<?> action : provider.getActions()) {
                result.add((Action<T>) action);
            }
        }

        return result;
    }

    public boolean hasDefaults(Class<?> type) {
        return providers.containsKey(type);
    }
}
