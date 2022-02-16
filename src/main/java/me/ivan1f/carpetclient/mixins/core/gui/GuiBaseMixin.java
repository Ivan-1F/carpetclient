package me.ivan1f.carpetclient.mixins.core.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import me.ivan1f.carpetclient.gui.CarpetClientConfigGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiBase.class)
public class GuiBaseMixin {
    @ModifyConstant(method = "drawTitle", constant = @Constant(intValue = 20), remap = false)
    private int leftAlignedTitle(int constant) {
        return isCarpetClientConfigGui() ? 12 : constant;
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isCarpetClientConfigGui() {
        return (GuiBase)(Object) this instanceof CarpetClientConfigGui;
    }
}
