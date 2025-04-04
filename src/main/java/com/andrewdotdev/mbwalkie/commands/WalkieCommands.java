package com.andrewdotdev.mbwalkie.commands;

import com.andrewdotdev.mbwalkie.config.ConfigManager;
import com.andrewdotdev.mbwalkie.mumble.MumbleClient;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class WalkieCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("reconnect")
                    .executes(context -> {
                        reconnectToMumble(context.getSource());
                        return Command.SINGLE_SUCCESS;
                    })
            );
        });
    }

    private static void reconnectToMumble(ServerCommandSource source) {
        source.sendFeedback(() -> Text.literal("🔄 Reconectando a Mumble..."), false);

        // Desconectar y recargar la configuración
        MumbleClient.disconnect();
        ConfigManager.loadConfig();
        MumbleClient.connect();

        source.sendFeedback(() -> Text.literal("✅ Reconectado a " + ConfigManager.getMumbleIP() + ":" + ConfigManager.getMumblePort()), false);
    }
}
