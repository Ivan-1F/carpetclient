package me.ivan1f.carpetclient.config.option.carpet;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import me.ivan1f.carpetclient.config.CarpetClientIConfigBase;

public class CarpetRuleConfigBooleanHotkeyed extends ConfigBooleanHotkeyed implements CarpetClientIConfigBase {
    private final String translatedName;

    public CarpetRuleConfigBooleanHotkeyed(String name, String translatedName, String description, String prettyName, boolean defaultValue, String defaultHotkey) {
        super(name, defaultValue, defaultHotkey, description, prettyName);
        this.translatedName = translatedName;
    }

    @Override
    public String getConfigGuiDisplayName() {
        return this.translatedName;
    }
}
