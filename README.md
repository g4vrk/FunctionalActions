# FunctionalActions

**Компактный framework действий для Bukkit/Paper-плагинов: конфиг описывает действие, библиотека выполняет его.**

[![Build](https://github.com/g4vrk/FunctionalActions/actions/workflows/build.yml/badge.svg)](https://github.com/g4vrk/FunctionalActions/actions/workflows/build.yml)
[![Java](https://img.shields.io/badge/Java-17+-orange)](.)
[![Paper](https://img.shields.io/badge/Paper-1.18.2+-blue)](.)
[![Version](https://img.shields.io/badge/version-2.1.0-informational)](.)
[![JitPack](https://jitpack.io/v/g4vrk/FunctionalActions.svg)](https://jitpack.io/#g4vrk/FunctionalActions)
[![License](https://img.shields.io/github/license/g4vrk/FunctionalActions)](LICENSE)

---

> [English version](README.en.md)

## Что это

FunctionalActions убирает повторяющийся код вокруг действий из конфигов. Вместо очередного `switch` на двадцать команд вы один раз регистрируете действия, а дальше парсите обычные строки и выполняете их для нужного игрока или `Audience`.

Сообщения, title, actionbar, звуки, команды, телепорт, эффекты, здоровье, еда, опыт, gamemode, velocity и работа с инвентарём уже есть из коробки. Свои действия добавляются через тот же registry.

> [!NOTE]
> FunctionalActions — **библиотека**, а не самостоятельный серверный плагин. Её нужно подключить к своему Paper-проекту.

---

## Подключение через JitPack

Добавьте JitPack в репозитории проекта:

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
```

И саму библиотеку:

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
> Для стабильных сборок используйте тег релиза. Если нужен последний коммит из `master`, замените версию на `master-SNAPSHOT`.

> [!IMPORTANT]
> JitPack собирает библиотеку прямо из GitHub. Перед использованием `2.1.0` в репозитории должен существовать соответствующий Git-тег/релиз.

---

## Как это выглядит

```java
ActionRegistry<Player> registry = new SimpleActionRegistry<>();

DefaultActions.Player.registerDefaults(
        registry,
        MiniMessage.miniMessage()::deserialize,
        "\\|",
        Long::parseLong
);

ActionParser<Player> parser = new SimpleActionParser<>(registry);

parser.parse("[message] <green>Готово!").execute(player);
parser.parse("[sound] entity.player.levelup|1|1").execute(player);
parser.parse("[teleport] world|0|80|0").execute(player);
```

Парсер понимает несколько форматов:

```text
[message] Hello
(message) Hello
<message> Hello
message: Hello
message Hello
```

> [!IMPORTANT]
> `splitter` передаётся в `String#split`, поэтому это **regex**. Для символа `|` используйте `"\\|"`, а не `"|"`.

---

## Действия из коробки

| Категория | Действия |
|---|---|
| Text | `message`, `actionbar`, `title` |
| Broadcast | `broadcast`, `broadcast-actionbar`, `broadcast-title` |
| Commands | `console-command`, `player-command` |
| Player | `teleport`, `effect`, `health`, `food`, `exp`, `gamemode` |
| Utility | `sound`, `velocity`, `fire`, `update-inventory`, `close-inventory` |

У большинства действий есть короткие алиасы: `msg`, `bar`, `cmd`, `tp`, `hp`, `gm`, `vel` и другие.

> [!CAUTION]
> Не передавайте непроверенный пользовательский ввод в `console-command` или `player-command`. Эти actions выполняют команды с реальными правами консоли или игрока.

---

## Несколько действий сразу

```java
ExecutableActionList<? super Player> actions = parser.parseAll(List.of(
        "[message] <green>Награда получена",
        "[sound] entity.player.levelup|1|1",
        "[exp] 100"
));


actions.run(player);
```

Аргументы можно обработать прямо перед выполнением — удобно для плейсхолдеров:

```java
actions.run(player, args ->
        args.replace("%player%", player.getName())
);
```

> [!TIP]
> Text parser и duration parser не зашиты внутрь библиотеки. Можно использовать MiniMessage, свой формат текста и собственный синтаксис времени без изменения FunctionalActions.

---

## Свои действия

```java
registry.register("heal", (player, args) ->
        player.setHealth(player.getMaxHealth())
);
```

После регистрации оно ничем не отличается от встроенных:

```text
[heal]
```

---

## Сборка

Нужны Java 17+ и Gradle Wrapper из репозитория.

```bash
./gradlew build
```

JAR и sources JAR появятся в `build/libs`.

## Лицензия

FunctionalActions распространяется под MIT — см. [LICENSE](LICENSE).
