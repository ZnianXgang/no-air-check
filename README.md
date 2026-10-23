# No Air Check

A small server-side Fabric mod for Minecraft 26.3 or newer that disables the vanilla "standing on air" support-block resend check. This check is unnecessary in my opinion and causes [MC-311474](https://mojira.dev/MC-311474) (The game can log that a player is standing on air even when they're not).

Works on dedicated server, singleplayer, and LAN.

## What it does

Vanilla 26.3 added a check in `ServerGamePacketListenerImpl#handlePlayerPositionChange`. When the client reports `onGround` is `true` but the server-side player's `verticalCollisionBelow` (inherited from `Entity`) is `false`, the server calls `forceSendPlayerSupportBlocks()`.

That method:

1. logs `Player {} standing on air - force-sending blocks below` (at most once every 200 ticks), and
2. re-sends the block updates (and block-entity updates) at the four corners just below the player.

The condition is prone to false positives: the client's `onGround` flag is a prediction, while the server-side `verticalCollisionBelow` flag is only refreshed when the server-side move has a vertical component. It can therefore be stale while the client legitimately reports `onGround`, and latency/desync makes the mismatch even more likely.

This mod adds a mixin that cancels `forceSendPlayerSupportBlocks()` at its head, so neither the log message nor the forced block resend happens. Nothing else in the movement check (e.g. the `allow-flight` check enabled in `server.properties`) is changed.

## Building

```bash
./gradlew build
```

The jar is written to `build/libs/no-air-check-1.0.jar`.

## Requirements

- Minecraft 26.3 or newer
- Fabric Loader 0.19.5 or newer
- Java 25

## License

CC0-1.0
