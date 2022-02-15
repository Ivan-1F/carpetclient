package me.ivan1f.carpetclient.config.option.carpet;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import me.ivan1f.carpetclient.config.CarpetClientIConfigBase;

public class CarpetRuleConfigBooleanHotkeyed extends ConfigBooleanHotkeyed implements CarpetClientIConfigBase {
    private final String translatedName;

    public CarpetRuleConfigBooleanHotkeyed(String name, String translatedName, String description, boolean defaultValue, String defaultHotkey) {
        super(name, defaultValue, defaultHotkey, description, CARPETCLIENT_NAMESPACE_PREFIX + name + PRETTY_NAME_SUFFIX);
        this.translatedName = translatedName;
    }

    @Override
    public String getConfigGuiDisplayName() {
        return this.translatedName;
    }
}
