package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigString;
import me.ivan1f.carpetclient.config.option.carpet.CarpetRuleConfigBooleanHotkeyed;
import me.ivan1f.carpetclient.config.option.carpet.CarpetRuleConfigString;
import me.ivan1f.carpetclient.util.ConfigUtil;
import me.ivan1f.carpetclient.util.MinecraftClientUtil;

public class CarpetRule extends CarpetClientOption {
    private final String type;

    public CarpetRule(String[] categories, String name, String translatedName, String description, String type, String defaultValue, String value) {
        super(categories, CarpetRule.parseCarpetRule(name, translatedName, description, type, defaultValue, value));
        this.type = type;
    }

    private static IConfigBase parseCarpetRule(String name, String translatedName, String description, String type, String defaultValue, String value) {
        if (type.equals("boolean")) {
            CarpetRuleConfigBooleanHotkeyed config = new CarpetRuleConfigBooleanHotkeyed(name, translatedName, description, translatedName, Boolean.parseBoolean(defaultValue), "");
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

    // only used to set the value when the rule is updated on server side
    // aka sync server values to client
    // will not trigger valueChange callbacks, so it won't set the rule again
    public void set(String value) {
        if (this.type.equals("boolean")) {
            ConfigUtil.modifyConfigBooleanSilently(((ConfigBooleanHotkeyed) this.getOption()), Boolean.parseBoolean(value));
        } else {
            ConfigUtil.modifyConfigStringSilently(((ConfigString) this.getOption()), value);
        }
    }
}
