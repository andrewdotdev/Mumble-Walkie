package com.andrewdotdev.mbwalkie.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static KeyBinding radioKey;

    public static void register() {
        radioKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Talk",  // Nombre en el archivo de traducción
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,    // Tecla por defecto (puede cambiarse en ajustes)
                "Mumble Walkie Talkie"    // Categoría en los controles
        ));
    }
}
