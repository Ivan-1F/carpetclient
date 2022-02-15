package me.ivan1f.carpetclient.config.option;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import me.ivan1f.carpetclient.config.CarpetClientIConfigBase;

public class CarpetClientConfigBoolean extends ConfigBoolean implements CarpetClientIConfigBase {
    public CarpetClientConfigBoolean(String name, boolean defaultValue) {
        super(name, defaultValue, CARPETCLIENT_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }
}
