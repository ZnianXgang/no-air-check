package me.znianxgang.noaircheck.neoforge;

import me.znianxgang.noaircheck.NoAirCheck;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(NoAirCheckNeoForge.MOD_ID)
public final class NoAirCheckNeoForge {
	/** Must match the modId in META-INF/neoforge.mods.toml. */
	public static final String MOD_ID = "no_air_check";

	public NoAirCheckNeoForge(IEventBus modEventBus) {
		NoAirCheck.init();
	}
}
