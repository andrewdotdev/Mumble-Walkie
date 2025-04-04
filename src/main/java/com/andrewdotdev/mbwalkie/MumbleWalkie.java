package com.andrewdotdev.mbwalkie;

import com.andrewdotdev.mbwalkie.commands.WalkieCommands;
import com.andrewdotdev.mbwalkie.config.ConfigManager;
import com.andrewdotdev.mbwalkie.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MumbleWalkie implements ModInitializer {
	public static final String MOD_ID = "mbwalkie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigManager.loadConfig();
		WalkieCommands.register();
		ModItems.registerModItems();
		LOGGER.info("Mumble Walkie initialized!");
	}
}