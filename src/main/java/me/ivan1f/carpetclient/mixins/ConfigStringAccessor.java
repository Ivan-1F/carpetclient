package me.ivan1f.carpetclient.mixins;

import fi.dy.masa.malilib.config.options.ConfigString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ConfigString.class, remap = false)
public interface ConfigStringAccessor {
    @Accessor("value")
    void setValue(String value);
}
