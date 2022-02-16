package me.ivan1f.carpetclient.mixins;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ConfigBoolean.class, remap = false)
public interface ConfigBooleanAccessor {
    @Accessor("value")
    void setValue(boolean value);
}
