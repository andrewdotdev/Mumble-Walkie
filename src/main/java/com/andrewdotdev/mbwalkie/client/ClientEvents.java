package com.andrewdotdev.mbwalkie.client;

import com.andrewdotdev.mbwalkie.mumble.MumbleClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static com.andrewdotdev.mbwalkie.item.ModItems.WALKIE_TALKIE;

public class ClientEvents {
    private static boolean isTalking = false;

    public static void register() {

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> MumbleClient.connect());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (hasWalkieTalkie(client)) {
                if (Keybinds.radioKey.isPressed() && !isTalking) {
                    startTalking(client);
                    isTalking = true;
                } else if (!Keybinds.radioKey.isPressed() && isTalking) {
                    stopTalking(client);
                    isTalking = false;
                }
            } else if (!hasWalkieTalkie(client) && isTalking) {
                stopTalking(client);
                isTalking = false;
            }
        });
    }

    private static boolean hasWalkieTalkie(MinecraftClient client) {
        if (client.player == null) return false;

        ItemStack mainHand = client.player.getMainHandStack();
        ItemStack offHand = client.player.getOffHandStack();

        return mainHand.isOf(WALKIE_TALKIE) || offHand.isOf(WALKIE_TALKIE);
    }

    private static void startTalking(MinecraftClient client) {
        playSound(client, "mbwalkie:walkie_start");
        MumbleClient.startTalking();
        System.out.println("🎙️ Started Talking...");
        // Aquí deberías conectarlo con Mumble o el sistema de voz
    }

    private static void stopTalking(MinecraftClient client) {
        playSound(client, "mbwalkie:walkie_end");
        MumbleClient.stopTalking();
        System.out.println("📻 Stopped Talking.");
        // Aquí desconectas el audio
    }

    private static void playSound(MinecraftClient client, String soundId) {
        if (client.player == null) return;

        SoundEvent soundEvent = new SoundEvent(Identifier.of(soundId), Optional.of(8F));
        if (soundEvent != null) {
            client.getSoundManager().play(
                    PositionedSoundInstance.master(soundEvent, 1.0F)
            );
        }
    }
}
