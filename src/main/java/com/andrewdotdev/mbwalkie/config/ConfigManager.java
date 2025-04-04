package com.andrewdotdev.mbwalkie.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final File CONFIG_FILE = new File("config/mbwalkie_config.json");
    private static JsonObject configData;
    private static final Gson GSON = new Gson();

    public static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            saveDefaultConfig();
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            configData = GSON.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveDefaultConfig() {
        JsonObject defaultConfig = new JsonObject();
        defaultConfig.addProperty("mumble_ip", "127.0.0.1");
        defaultConfig.addProperty("mumble_port", 64738);

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(defaultConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMumbleIP() {
        return configData.get("mumble_ip").getAsString();
    }

    public static int getMumblePort() {
        return configData.get("mumble_port").getAsInt();
    }
}
