package me.ivan1f.carpetclient.network;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.event.InputEventHandler;
import io.netty.buffer.Unpooled;
import me.ivan1f.carpetclient.CarpetClientMod;
import me.ivan1f.carpetclient.config.CarpetClientConfigs;
import me.ivan1f.carpetclient.config.CarpetRule;
import me.ivan1f.carpetclient.config.KeybindProvider;
import me.ivan1f.carpetclient.util.MinecraftClientUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.List;
import java.util.Objects;

public class NetworkHandler {
    public static boolean onCustomPayload(Identifier channel, PacketByteBuf data) {
        boolean handled = true;
        if (Identifiers.HI.equals(channel)) {
            requestCarpetRules();
        } else if (Identifiers.CARPET_RULES.equals(channel)) {
            onCarpetRules(data);
        } else if (Identifiers.VALUE_CHANGED.equals(channel)) {
            onValueChanged(data);
        } else if (channel.getNamespace().equals(CarpetClientMod.MOD_ID)) {
            CarpetClientMod.LOGGER.warn("Receiving unknown CarpetClient packet. Maybe this version is outdated");
            handled = false;
        }
        return handled;
    }

    public static void requestCarpetRules() {
        MinecraftClientUtil.sendPacket(new CustomPayloadC2SPacket(Identifiers.HELLO, new PacketByteBuf(Unpooled.buffer())));
    }

    private static void onCarpetRules(PacketByteBuf data) {
        for (Tag tag : Objects.requireNonNull(data.readCompoundTag()).getList("rules", 10)) {
            CompoundTag rule = (CompoundTag) tag;
            List<String> categories = Lists.newArrayList();
            rule.getList("categories", 8).forEach(category -> categories.add(category.asString()));
            CarpetRule carpetRule = new CarpetRule(
                    categories.toArray(new String[0]),
                    rule.getString("name"),
                    rule.getString("translatedName"),
                    rule.getString("description"),
                    rule.getString("type"),
                    rule.getString("defaultValue"),
                    rule.getString("value")
            );
            CarpetClientConfigs.addCarpetRule(carpetRule);
        }
        InputEventHandler.getKeybindManager().unregisterKeybindProvider(new KeybindProvider());
        InputEventHandler.getKeybindManager().registerKeybindProvider(new KeybindProvider());
        CarpetClientConfigs.isCarpetRuleLoaded = true;
    }

    private static void onValueChanged(PacketByteBuf data) {
        CompoundTag compoundTag = Objects.requireNonNull(data.readCompoundTag());
        String rule = compoundTag.getString("rule");
        String newValue = compoundTag.getString("newValue");
        CarpetClientConfigs.getAllOptionStream().filter(config -> config.getOption().getName().equals(rule)).findAny().ifPresent(config -> {
            CarpetRule carpetRule = (CarpetRule) config;
            carpetRule.set(newValue);
        });
    }
}
