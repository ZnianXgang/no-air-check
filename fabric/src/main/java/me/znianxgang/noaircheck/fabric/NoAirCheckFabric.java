package me.znianxgang.noaircheck.fabric;

import me.znianxgang.noaircheck.NoAirCheck;
import net.fabricmc.api.ModInitializer;

public final class NoAirCheckFabric implements ModInitializer {
	public static final String MOD_ID = "no-air-check";

	@Override
	public void onInitialize() {
		NoAirCheck.init();
	}
}
