package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.config.options.ConfigString;

public class CarpetClientConfigString extends ConfigString implements CarpetClientIConfigBase {
    public CarpetClientConfigString(String name, String defaultValue) {
        super(name, defaultValue, CARPETCLIENT_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }
}
