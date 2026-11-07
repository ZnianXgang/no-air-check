package me.znianxgang.noaircheck.mixin;

import net.minecraft.server.network.ServerGamePacketListenerImpl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables the vanilla "standing on air" check.
 *
 * <p>In 26.3, {@code ServerGamePacketListenerImpl#handlePlayerPositionChange} compares the
 * client's {@code onGround} flag with the server-side {@code verticalCollisionBelow} flag.
 * When they disagree it calls {@code forceSendPlayerSupportBlocks()}, which logs
 * "Player {} standing on air - force-sending blocks below" and re-sends the blocks under
 * the player. The two flags have different update rules: for a server-side player,
 * {@code Entity#move} only refreshes {@code verticalCollisionBelow} when the move has a
 * vertical component, so it can be stale while the client still reports {@code onGround}.
 * Client/server desync and latency make the mismatch even more likely. This mixin cancels
 * the method entirely.
 */
@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
	@Inject(method = "forceSendPlayerSupportBlocks", at = @At("HEAD"), cancellable = true)
	private void noaircheck$disableSupportBlockResend(CallbackInfo ci) {
		ci.cancel();
	}
}
