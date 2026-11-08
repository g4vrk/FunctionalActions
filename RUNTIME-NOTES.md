# Runtime changes

SimpleActionParser caches at most 4096 syntax entries under synchronization and
resolves the current action on every parse. Replacing/removing a registry key
therefore affects subsequent parses. Already returned ExecutableAction objects
continue to capture their original action, as before. The syntax cache holds no
extension handlers. SimpleActionRegistry uses atomic concurrent registration and
Locale.ROOT normalization; all() now returns an immutable snapshot.

MessageAction, ActionBarAction and TitleAction provide contextual factories accepting BiFunction<Audience,
String, Component>. Resolve player context during execution, using unparsed tags
for untrusted values. No contextual output is cached. These actions do not move
execution onto a Bukkit scheduler; callers remain responsible for thread affinity.
Blank MessageAction input sends one empty message and never invokes its mapper.

The full Gradle build and five tests pass, including concurrent parsing of 20000
distinct inputs and contextual default registration in a non-replacing registry.
DefaultActions.Adventure.registerContextualDefaults accepts the contextual mapper and resolves broadcasts
separately per recipient, including the console for broadcast messages.
