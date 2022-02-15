package me.ivan1f.carpetclient.config.option;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import me.ivan1f.carpetclient.config.CarpetClientIConfigBase;

public class CarpetClientConfigHotkey extends ConfigHotkey implements CarpetClientIConfigBase {
    public CarpetClientConfigHotkey(String name, String defaultStorageString) {
        super(name, defaultStorageString, CARPETCLIENT_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    public CarpetClientConfigHotkey(String name, String defaultStorageString, KeybindSettings settings) {
        super(name, defaultStorageString, KeybindSettings.DEFAULT, CARPETCLIENT_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }
}
