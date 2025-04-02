package com.andrewdotdev.mbwalkie.item;

import com.andrewdotdev.mbwalkie.MumbleWalkie;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item WALKIE_TALKIE = register("walkie_talkie", new Item.Settings());

    private static Item register(String name, Item.Settings itemSettings) {
        Identifier id = Identifier.of(MumbleWalkie.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = itemSettings.registryKey(key);

        return Registry.register(Registries.ITEM, key, new Item(settings));
    }

    public static void registerModItems() {
        MumbleWalkie.LOGGER.info("Registering Mod Items for " + MumbleWalkie.MOD_ID);

        // Añade el ítem al grupo de herramientas
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(WALKIE_TALKIE);
        });
    }
}