package com.andrewdotdev.mbwalkie.mumble;

import com.andrewdotdev.mbwalkie.config.ConfigManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class MumbleClient {
    private static Socket socket;
    private static boolean connected = false;
    private static final double MAX_RANGE = 50.0;
    private static boolean transmitting = false;

    public static void startTalking() {
        transmitting = true;
        updateVolume();
        System.out.println("🎙 Transmitiendo voz...");
    }

    public static void stopTalking() {
        transmitting = false;
        System.out.println("🔇 Voz detenida.");
    }

    private static void updateVolume() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        assert client.world != null;
        boolean isStormy = client.world.isThundering() || client.world.isRaining();

        for (PlayerEntity otherPlayer : client.world.getPlayers()) {
            if (otherPlayer == client.player) continue;

            double distance = client.player.getPos().distanceTo(otherPlayer.getPos());
            float volume = calculateVolume(distance);

            if (isStormy || distance > 30) {
                playInterferenceSound();
            }

            System.out.println("🔊 Ajustando volumen para " + otherPlayer.getName().getString() + ": " + volume);
        }
    }

    private static void playInterferenceSound() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        client.player.playSound(new SoundEvent(Identifier.of("mbwalkie:walkie_static"), Optional.of(8F)), 1.0f, 1.0f);
    }

    private static float calculateVolume(double distance) {
        if (distance > MAX_RANGE) return 0.0f;
        return 1.0f - (float) (distance / MAX_RANGE);
    }


    public static void connect() {
        if (connected) return;

        try {
            String ip = ConfigManager.getMumbleIP();
            int port = ConfigManager.getMumblePort();

            socket = new Socket(ip, port);
            connected = true;
            System.out.println("✅ Connected in " + ip + ":" + port);
        } catch (IOException e) {
            System.err.println("❌ Error while trying to connect Mumble: " + e.getMessage());
        }
    }

    public static void disconnect() {
        if (!connected) return;

        try {
            socket.close();
            connected = false;
            System.out.println("🔴 Mumble Disconnected");
        } catch (IOException e) {
            System.err.println("⚠️ Error while trying to disconnect Mumble: " + e.getMessage());
        }
    }
}
