package me.ivan1f.carpetclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class MinecraftClientUtil {
    public static MinecraftClient getMinecraftClient() {
        return MinecraftClient.getInstance();
    }

    public static ClientPlayerEntity getPlayer() {
        return getMinecraftClient().player;
    }

    public static void modifyCarpetRule(String name, String value) {
        getPlayer().sendChatMessage(String.format("/carpet %s %s", name, value));
    }
}
