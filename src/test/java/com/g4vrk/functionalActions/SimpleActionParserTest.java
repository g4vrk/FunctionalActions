package com.g4vrk.functionalActions;

import com.g4vrk.functionalActions.list.ExecutableActionList;
import com.g4vrk.functionalActions.parser.impl.SimpleActionParser;
import com.g4vrk.functionalActions.registry.impl.SimpleActionRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleActionParserTest {

    private static Action<String> recorder(final AtomicReference<String> sink) {
        return (context, args) -> sink.set(context + "|" + args);
    }

    private static SimpleActionParser<String> parser(final Action<String> message) {
        final SimpleActionRegistry<String> registry = new SimpleActionRegistry<>(true);
        registry.register(message, "message", "msg");
        return new SimpleActionParser<>(registry);
    }

    @Test
    void parsesEveryBracketStyle() {
        final AtomicReference<String> sink = new AtomicReference<>();
        final SimpleActionParser<String> parser = parser(recorder(sink));

        for (final String line : List.of("message: Hello", "[message] Hello", "(message) Hello", "<message> Hello")) {
            final ExecutableAction<? super String> action = parser.parse(line);

            assertNotNull(action, line);
            action.execute("ctx");
            assertEquals("ctx|Hello", sink.get(), line);
        }
    }

    @Test
    void parsesALineWithoutArguments() {
        final AtomicReference<String> sink = new AtomicReference<>();
        final SimpleActionParser<String> parser = parser(recorder(sink));

        final ExecutableAction<? super String> action = parser.parse("message");

        assertNotNull(action);
        action.execute("ctx");
        assertEquals("ctx|", sink.get());
    }

    @Test
    void ignoresAnUnknownKeyAndAnUnclosedBracket() {
        final SimpleActionParser<String> parser = parser(recorder(new AtomicReference<>()));

        assertNull(parser.parse("unknown: Hello"));
        assertNull(parser.parse("[message Hello"));
        assertNull(parser.parse("   "));
    }

    @Test
    void answersTheSameInstanceForTheSameLine() {
        final SimpleActionParser<String> parser = parser(recorder(new AtomicReference<>()));

        final ExecutableAction<? super String> first = parser.parse("message: Hello");
        final ExecutableAction<? super String> second = parser.parse("message: Hello");

        assertSame(first, second);
    }

    @Test
    void answersTheSameInstanceForTheSameKeyAndArguments() {
        final SimpleActionParser<String> parser = parser(recorder(new AtomicReference<>()));

        final ExecutableAction<? super String> first = parser.parse("message", "Hello");
        final ExecutableAction<? super String> second = parser.parse("message", "Hello");

        assertSame(first, second);
    }

    @Test
    void looksKeysUpWithoutCase() {
        final AtomicReference<String> sink = new AtomicReference<>();
        final SimpleActionParser<String> parser = parser(recorder(sink));

        final ExecutableAction<? super String> action = parser.parse("MeSsAgE: Hello");

        assertNotNull(action);
        action.execute("ctx");
        assertEquals("ctx|Hello", sink.get());
    }

    @Test
    void parsesOnlyTheKnownLinesOfAList() {
        final SimpleActionParser<String> parser = parser(recorder(new AtomicReference<>()));

        final ExecutableActionList<? super String> list = parser.parseAll(List.of(
                "message: one",
                "unknown: skipped",
                "message: two"
        ));

        assertEquals(2, list.size());
    }

    @Test
    void runsTheLinesOfAListInOrder() {
        final StringBuilder sink = new StringBuilder();
        final SimpleActionRegistry<String> registry = new SimpleActionRegistry<>(true);
        registry.register((context, args) -> sink.append(args), "append");

        final SimpleActionParser<String> parser = new SimpleActionParser<>(registry);

        parser.parseAll(List.of("append: a", "append: b", "append: c")).run("ctx");

        assertEquals("abc", sink.toString());
    }

    @Test
    void appliesThePreProcessorToTheArguments() {
        final AtomicReference<String> sink = new AtomicReference<>();
        final SimpleActionParser<String> parser = parser(recorder(sink));

        parser.parse("message: <player>").execute("ctx", args -> args.replace("<player>", "Steve"));

        assertEquals("ctx|Steve", sink.get());
    }

    @Test
    void staysConsistentUnderConcurrentUse() {
        final AtomicInteger counter = new AtomicInteger();
        final SimpleActionRegistry<String> registry = new SimpleActionRegistry<>(true);
        registry.register((context, args) -> counter.incrementAndGet(), "count");

        final SimpleActionParser<String> parser = new SimpleActionParser<>(registry);

        IntStream.range(0, 5_000)
                .parallel()
                .forEach(index -> {
                    final ExecutableAction<? super String> action = parser.parse("count: " + (index % 32));

                    assertNotNull(action);
                    action.execute("ctx");
                });

        assertEquals(5_000, counter.get());
    }

    @Test
    void refusesToRegisterTheSameKeyTwice() {
        final SimpleActionRegistry<String> registry = new SimpleActionRegistry<>();

        registry.register(recorder(new AtomicReference<>()), "message");

        assertThrows(IllegalStateException.class, () -> registry.register(recorder(new AtomicReference<>()), "message"));
    }

}
