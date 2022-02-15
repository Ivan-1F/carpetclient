package me.ivan1f.carpetclient.config.option;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import me.ivan1f.carpetclient.config.CarpetClientIConfigBase;

public class CarpetClientConfigBooleanHotkeyed extends ConfigBooleanHotkeyed implements CarpetClientIConfigBase {
    public CarpetClientConfigBooleanHotkeyed(String name, boolean defaultValue, String defaultHotkey) {
        super(name, defaultValue, defaultHotkey, CARPETCLIENT_NAMESPACE_PREFIX + name + COMMENT_SUFFIX, CARPETCLIENT_NAMESPACE_PREFIX + name + PRETTY_NAME_SUFFIX);
    }
}
