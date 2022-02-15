package me.ivan1f.carpetclient.config.option.carpet;

import fi.dy.masa.malilib.config.options.ConfigString;
import me.ivan1f.carpetclient.config.CarpetClientIConfigBase;

public class CarpetRuleConfigString extends ConfigString implements CarpetClientIConfigBase {
    private final String translatedName;

    public CarpetRuleConfigString(String name, String translatedName, String description, String defaultValue) {
        super(name, defaultValue, description);
        this.translatedName = translatedName;
    }

    @Override
    public String getConfigGuiDisplayName() {
        return this.translatedName;
    }
}
