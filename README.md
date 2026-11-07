# No Air Check

A small server-side mod for Minecraft 26.3 or later that disables the vanilla "standing on air" support-block resend check. This check is unnecessary in my opinion, and causes [MC-311474](https://bugs.mojang.com/browse/MC/issues/MC-311474) (The game can log that a player is standing on air even when they're not) that spams the console and game logs with unnecessary messages. Available for both Fabric and NeoForge.

## What it does

Vanilla 26.3 added a check in `ServerGamePacketListenerImpl#handlePlayerPositionChange`. When the client reports `onGround` is `true` but the server-side player's `verticalCollisionBelow` (inherited from `Entity`) is `false`, the server calls `forceSendPlayerSupportBlocks()`.

That method:

1. logs `Player {} standing on air - force-sending blocks below` (at most once every 200 ticks), and
2. re-sends the block updates (and block-entity updates) at the four corners just below the player.

The condition is prone to false positives: the client's `onGround` flag is a prediction, while the server-side `verticalCollisionBelow` flag is only refreshed when the server-side move has a vertical component. It can therefore be stale while the client legitimately reports `onGround`, and latency/desync makes the mismatch even more likely.

This mod adds a mixin that cancels `forceSendPlayerSupportBlocks()` at its head, so neither the log message nor the forced block resend happens. Nothing else in the movement check (e.g. the `allow-flight` check enabled in `server.properties`) is changed.

## Supported loaders

| Loader | Mod id | Artifact |
| --- | --- | --- |
| Fabric | `no-air-check` | `no-air-check-fabric-<version>.jar` |
| NeoForge | `no_air_check` | `no-air-check-neoforge-<version>.jar` |

The NeoForge mod id uses an underscore because NeoForge only accepts `[a-z][a-z0-9_]{1,63}`. The two ids are otherwise the same mod.

## Requirements

- Minecraft 26.3 or later
- Java 25
- Fabric Loader 0.19.5 or later (Fabric), or NeoForge 26.3.0.101 or later (NeoForge)
- Fabric API is not required, and no other mod is needed on either loader

The mod is server-side only. On NeoForge, `displayTest` is set to `IGNORE_ALL_VERSION`, so clients without the mod are not rejected.

## Building

```bash
./gradlew build
```

The jars are written to:

- `fabric/build/libs/no-air-check-fabric-<version>.jar`
- `neoforge/build/libs/no-air-check-neoforge-<version>.jar`

Building requires JDK 25. NeoForge's ModDevGradle tooling also needs a JDK 21 for its asset downloader; Gradle provisions one automatically through the foojay resolver, or you can point it at a local installation:

```bash
./gradlew build -Dorg.gradle.java.installations.paths=/path/to/jdk-21
```

## Project layout

| Directory | Contents |
| --- | --- |
| `common/` | Loader-agnostic code and the shared mixin configuration |
| `fabric/` | Fabric entry point, `fabric.mod.json`, Fabric Loom build |
| `neoforge/` | NeoForge entry point, `neoforge.mods.toml`, ModDevGradle build |

`common/` is not a Gradle project. Both loader projects add it with `sourceSets.main.srcDir`, so the same sources and mixin configuration are compiled into both jars.

## License

CC0-1.0
