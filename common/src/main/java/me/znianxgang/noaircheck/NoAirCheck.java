package me.znianxgang.noaircheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loader-agnostic mod body. The Fabric and NeoForge entry points both call
 * {@link #init()}; nothing in here references a loader API.
 *
 * <p>The loader-specific mod ids live in those entry points: Fabric uses
 * {@code no-air-check}, NeoForge requires {@code no_air_check} because its id
 * pattern only allows {@code [a-z][a-z0-9_]{1,63}}.
 */
public final class NoAirCheck {
	/** Log identity of the mod. Not a loader mod id. */
	public static final String LOG_ID = "no-air-check";
	public static final Logger LOGGER = LoggerFactory.getLogger(LOG_ID);

	private NoAirCheck() {
	}

	public static void init() {
		LOGGER.info("Disabled the vanilla standing-on-air support-block resend check.");
	}
}
