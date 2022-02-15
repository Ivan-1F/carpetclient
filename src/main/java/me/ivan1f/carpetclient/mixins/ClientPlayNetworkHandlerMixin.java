package me.ivan1f.carpetclient.mixins;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.event.InputEventHandler;
import me.ivan1f.carpetclient.CarpetClientMod;
import me.ivan1f.carpetclient.config.CarpetClientConfigs;
import me.ivan1f.carpetclient.config.CarpetRule;
import me.ivan1f.carpetclient.config.KeybindProvider;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("RETURN"))
    private void handlePackets(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if (packet.getChannel().equals(new Identifier(CarpetClientMod.MOD_ID, "setting_list"))) {
            System.out.println("Channel correct, but nothing happens");
        }
        for (Tag tag : Objects.requireNonNull(packet.getData().readCompoundTag()).getList("rules", 10)) {
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
    }
}
