# FunctionalActions

**A compact action framework for Bukkit/Paper plugins: configuration describes the action, the library executes it.**

[![Build](https://github.com/g4vrk/FunctionalActions/actions/workflows/build.yml/badge.svg)](https://github.com/g4vrk/FunctionalActions/actions/workflows/build.yml)
[![Java](https://img.shields.io/badge/Java-17+-orange)](.)
[![Paper](https://img.shields.io/badge/Paper-1.18.2+-blue)](.)
[![Version](https://img.shields.io/badge/version-2.1.0-informational)](.)
[![JitPack](https://jitpack.io/v/g4vrk/FunctionalActions.svg)](https://jitpack.io/#g4vrk/FunctionalActions)
[![License](https://img.shields.io/github/license/g4vrk/FunctionalActions)](LICENSE)

---

> [Русская версия](README.md)

## What this is

FunctionalActions removes the repetitive glue code around config-driven actions. Instead of writing another twenty-case `switch`, register actions once, parse plain strings, and execute them against a player or any Adventure `Audience`.

Messages, titles, action bars, sounds, commands, teleportation, potion effects, health, food, experience, game mode, velocity, and inventory actions are included. Custom actions use the same registry.

> [!NOTE]
> FunctionalActions is a **library**, not a standalone server plugin. It is meant to be embedded into your Paper project.

---

## Installing with JitPack

Add JitPack to your repositories:

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
```

Then add FunctionalActions:

```kotlin
dependencies {
    implementation("com.github.g4vrk:FunctionalActions:2.1.0")
}
```

<details>
<summary><b>Gradle Groovy</b></summary>

```groovy
repositories {
    mavenCentral()
    maven { url = uri('https://jitpack.io') }
}

dependencies {
    implementation 'com.github.g4vrk:FunctionalActions:2.1.0'
}
```

</details>

<details>
<summary><b>Maven</b></summary>

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.g4vrk</groupId>
    <artifactId>FunctionalActions</artifactId>
    <version>2.1.0</version>
</dependency>
```

</details>

> [!TIP]
> Pin a release tag for reproducible builds. To use the latest commit from `master`, replace the version with `master-SNAPSHOT`.

> [!IMPORTANT]
> JitPack builds the library directly from GitHub. The matching Git tag/release must exist before `2.1.0` can be resolved.

---

## What it looks like

```java
ActionRegistry<Player> registry = new SimpleActionRegistry<>();

DefaultActions.Player.registerDefaults(
        registry,
        MiniMessage.miniMessage()::deserialize,
        "\\|",
        Long::parseLong
);

ActionParser<Player> parser = new SimpleActionParser<>(registry);

parser.parse("[message] <green>Done!").execute(player);
parser.parse("[sound] entity.player.levelup|1|1").execute(player);
parser.parse("[teleport] world|0|80|0").execute(player);
```

The parser accepts several forms:

```text
[message] Hello
(message) Hello
<message> Hello
message: Hello
message Hello
```

> [!IMPORTANT]
> `splitter` is passed to `String#split`, so it is a **regex**. For a pipe separator use `"\\|"`, not `"|"`.

---

## Built-in actions

| Category | Actions |
|---|---|
| Text | `message`, `actionbar`, `title` |
| Broadcast | `broadcast`, `broadcast-actionbar`, `broadcast-title` |
| Commands | `console-command`, `player-command` |
| Player | `teleport`, `effect`, `health`, `food`, `exp`, `gamemode` |
| Utility | `sound`, `velocity`, `fire`, `update-inventory`, `close-inventory` |

Most actions also have short aliases such as `msg`, `bar`, `cmd`, `tp`, `hp`, `gm`, and `vel`.

> [!CAUTION]
> Never feed untrusted user input directly into `console-command` or `player-command`. These actions execute commands with the real permissions of the console or player.

---

## Run several actions

```java
ExecutableActionList<? super Player> actions = parser.parseAll(List.of(
        "[message] <green>Reward claimed",
        "[sound] entity.player.levelup|1|1",
        "[exp] 100"
));


actions.run(player);
```

Arguments can be processed immediately before execution, which works well for placeholders:

```java
actions.run(player, args ->
        args.replace("%player%", player.getName())
);
```

> [!TIP]
> Text parsing and duration parsing are supplied by your plugin. Use MiniMessage, your own text format, or your own duration syntax without changing FunctionalActions.

---

## Custom actions

```java
registry.register("heal", (player, args) ->
        player.setHealth(player.getMaxHealth())
);
```

Once registered, it behaves exactly like a built-in action:

```text
[heal]
```

---

## Building

Requirements: Java 17+ and the Gradle Wrapper included in the repository.

```bash
./gradlew build
```

The JAR and sources JAR are written to `build/libs`.

## License

FunctionalActions is MIT-licensed — see [LICENSE](LICENSE).
