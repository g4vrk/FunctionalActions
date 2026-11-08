package com.g4vrk.functionalActions;

import com.g4vrk.functionalActions.impl.audience.MessageAction;
import com.g4vrk.functionalActions.parser.impl.SimpleActionParser;
import com.g4vrk.functionalActions.registry.impl.SimpleActionRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class RuntimeTest {
    @Test void observesReplacementAndRemovalWithoutStaleHandlerCache() {
        var registry = new SimpleActionRegistry<String>(true);
        var parser = new SimpleActionParser<>(registry);
        AtomicInteger count = new AtomicInteger();
        registry.register("message", (context, args) -> count.incrementAndGet());
        parser.parse("[MESSAGE] hi").execute("first");
        registry.register("message", (context, args) -> count.addAndGet(10));
        parser.parse("[MESSAGE] hi").execute("second");
        assertEquals(11, count.get());
        registry.unregister("message");
        assertNull(parser.parse("[MESSAGE] hi"));
    }

    @Test void blankMessagesNeverInvokeMapper() {
        var action = new MessageAction(text -> { fail("blank mapper call"); return Component.empty(); });
        action.execute(Audience.empty(), null);
        action.execute(Audience.empty(), " ");
    }

    @Test void resolvesContextAtExecutionTime() {
        AtomicInteger calls = new AtomicInteger();
        Audience first = Audience.empty();
        var action = new MessageAction((audience, text) -> {
            assertSame(first, audience);
            calls.incrementAndGet();
            return Component.text(text);
        });
        action.execute(first, "hello");
        assertEquals(1, calls.get());
    }

    @Test void supportsConcurrentParsingAndBoundedSyntaxCache() throws Exception {
        var registry = new SimpleActionRegistry<String>();
        registry.register("test", (context, args) -> {});
        var parser = new SimpleActionParser<>(registry);
        try (var executor = java.util.concurrent.Executors.newFixedThreadPool(4)) {
            var futures = new java.util.ArrayList<java.util.concurrent.Future<?>>();
            for (int thread = 0; thread < 4; thread++) {
                final int offset = thread * 5000;
                futures.add(executor.submit(() -> {
                    for (int i = 0; i < 5000; i++) assertNotNull(parser.parse("test: " + (offset + i)));
                }));
            }
            for (var future : futures) future.get();
        }
        var field = SimpleActionParser.class.getDeclaredField("inputCache");
        field.setAccessible(true);
        assertTrue(((java.util.Map<?, ?>) field.get(parser)).size() <= 4096);
    }
}
