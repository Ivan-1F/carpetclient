package me.ivan1f.carpetclient.util;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigString;
import me.ivan1f.carpetclient.mixins.ConfigBooleanAccessor;
import me.ivan1f.carpetclient.mixins.ConfigStringAccessor;

public class ConfigUtil {
    public static void modifyConfigBooleanSilently(ConfigBoolean configBoolean, boolean value) {
        ((ConfigBooleanAccessor) configBoolean).setValue(value);
    }

    public static void modifyConfigStringSilently(ConfigString configString, String value) {
        ((ConfigStringAccessor) configString).setValue(value);
    }
}
