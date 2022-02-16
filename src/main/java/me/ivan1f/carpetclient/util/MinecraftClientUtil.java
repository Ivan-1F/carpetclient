package me.ivan1f.carpetclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.Nullable;

public class MinecraftClientUtil {
    public static MinecraftClient getMinecraftClient() {
        return MinecraftClient.getInstance();
    }

    public static @Nullable ClientPlayerEntity getPlayer() {
        return getMinecraftClient().player;
    }

    public static void modifyCarpetRule(String name, String value) {
        if (getPlayer() != null) {
            getPlayer().sendChatMessage(String.format("/carpet %s %s", name, value));
        }
    }

    public static @Nullable ClientPlayNetworkHandler getNetworkHandler() {
        if (getPlayer() != null) {
            return getPlayer().networkHandler;
        }
        return null;
    }

    public static void sendPacket(Packet<?> packet) {
        if (getNetworkHandler() != null) {
            getNetworkHandler().sendPacket(packet);
        }
    }
}
