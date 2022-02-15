package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.config.IConfigBase;
import me.ivan1f.carpetclient.config.option.carpet.CarpetRuleConfigBooleanHotkeyed;
import me.ivan1f.carpetclient.config.option.carpet.CarpetRuleConfigString;

public class CarpetRule extends CarpetClientOption {
    public CarpetRule(String[] categories, String name, String translatedName, String description, String type, String defaultValue, String value) {
        super(categories, CarpetRule.parseCarpetRule(name, translatedName, description, type, defaultValue, value));
    }

    private static IConfigBase parseCarpetRule(String name, String translatedName, String description, String type, String defaultValue, String value) {
        if (type.equals("boolean")) {
            return new CarpetRuleConfigBooleanHotkeyed(name, translatedName, description, Boolean.parseBoolean(defaultValue), "");
        } else {
            return new CarpetRuleConfigString(name, translatedName, description, defaultValue);
        }
    }
}
