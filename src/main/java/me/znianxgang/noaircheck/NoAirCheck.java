package me.znianxgang.noaircheck;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoAirCheck implements ModInitializer {
	public static final String MOD_ID = "no-air-check";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Disabled the vanilla standing-on-air support-block resend check.");
	}
}
