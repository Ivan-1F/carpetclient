package me.ivan1f.carpetclient.mixins;

import me.ivan1f.carpetclient.network.NetworkHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    private void handlePackets(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if (NetworkHandler.onCustomPayload(packet.getChannel(), packet.getData())) {
            ci.cancel();
        }
    }
}
