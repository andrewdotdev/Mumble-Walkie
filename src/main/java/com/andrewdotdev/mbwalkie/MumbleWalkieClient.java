package com.andrewdotdev.mbwalkie;

import com.andrewdotdev.mbwalkie.client.ClientEvents;
import com.andrewdotdev.mbwalkie.client.Keybinds;
import net.fabricmc.api.ClientModInitializer;

public class MumbleWalkieClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Keybinds.register();
        ClientEvents.register();
    }
}
