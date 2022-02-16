package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.config.IConfigBase;
import me.ivan1f.carpetclient.config.option.carpet.CarpetRuleConfigBooleanHotkeyed;
import me.ivan1f.carpetclient.config.option.carpet.CarpetRuleConfigString;
import me.ivan1f.carpetclient.util.MinecraftClientUtil;

public class CarpetRule extends CarpetClientOption {
    public CarpetRule(String[] categories, String name, String translatedName, String description, String type, String defaultValue, String value) {
        super(categories, CarpetRule.parseCarpetRule(name, translatedName, description, type, defaultValue, value));
    }

    private static IConfigBase parseCarpetRule(String name, String translatedName, String description, String type, String defaultValue, String value) {
        if (type.equals("boolean")) {
            CarpetRuleConfigBooleanHotkeyed config = new CarpetRuleConfigBooleanHotkeyed(name, translatedName, description, Boolean.parseBoolean(defaultValue), "");
            config.setBooleanValue(Boolean.parseBoolean(value));
            config.setValueChangeCallback((newValue) -> MinecraftClientUtil.modifyCarpetRule(name, config.getStringValue()));
            return config;
        } else {
            CarpetRuleConfigString config = new CarpetRuleConfigString(name, translatedName, description, defaultValue);
            config.setValueFromString(value);
            config.setValueChangeCallback((newValue) -> MinecraftClientUtil.modifyCarpetRule(name, config.getStringValue()));
            return config;
        }
    }
}
