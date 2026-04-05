package com.g4vrk.functionalActions.actions;

public final class EmptyContext {

    public static final EmptyContext INSTANCE = new EmptyContext();

    private EmptyContext() {
    }

    public static EmptyContext emptyContext() {
        return INSTANCE;
    }
}
