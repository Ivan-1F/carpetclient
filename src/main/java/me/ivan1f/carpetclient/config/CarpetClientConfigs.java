package me.ivan1f.carpetclient.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import me.ivan1f.carpetclient.CarpetClientMod;
import me.ivan1f.carpetclient.config.option.CarpetClientConfigHotkey;
import me.ivan1f.carpetclient.gui.CarpetClientConfigGui;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarpetClientConfigs {
    public static final String CARPET_CLIENT = "carpet_client";

    @Config(categories = {CARPET_CLIENT})
    public static final ConfigHotkey OPEN_CARPETCLIENT_CONFIG_GUI = new CarpetClientConfigHotkey("openCarpetClientConfigGui", "R,C");

    public static void initializeCallbacks() {
        OPEN_CARPETCLIENT_CONFIG_GUI.getKeybind().setCallback(CarpetClientConfigGui::onOpenGuiHotkey);
    }

    private static List<CarpetClientOption> OPTIONS = Lists.newArrayList();
    private static Set<String> CATEGORIES = Sets.newHashSet(CARPET_CLIENT);
    private static final Map<String, List<CarpetClientOption>> CATEGORY_TO_OPTION = Maps.newLinkedHashMap();

    public static void clearCarpetRules() {
        OPTIONS = OPTIONS.stream().filter(CarpetClientConfigs::isCarpetClientSetting).collect(Collectors.toList());
        CATEGORIES = Sets.newHashSet(CARPET_CLIENT);
        CATEGORY_TO_OPTION.clear();
        OPTIONS.forEach(option -> {
            for (String category : option.getCategories()) {
                addCategory(category);
                CATEGORY_TO_OPTION.computeIfAbsent(category, k -> Lists.newArrayList()).add(option);
            }
        });
    }

    private static boolean isCarpetClientSetting(CarpetClientOption option) {
        return Arrays.asList(option.getCategories()).contains(CARPET_CLIENT);
    }

    public static Set<String> getCategories() {
        return CATEGORIES;
    }

    public static void addCategory(String category) {
        CATEGORIES.add(category);
    }

    public static void addCarpetRule(CarpetRule rule) {
        OPTIONS.add(rule);
        for (String category : rule.getCategories()) {
            addCategory(category);
            CATEGORY_TO_OPTION.computeIfAbsent(category, k -> Lists.newArrayList()).add(rule);
        }
    }

    public static List<CarpetClientOption> getOptions(String categoryType) {
        return CATEGORY_TO_OPTION.getOrDefault(categoryType, Collections.emptyList());
    }

    public static Stream<IConfigBase> getAllConfigOptionStream() {
        return OPTIONS.stream().map(CarpetClientOption::getOption);
    }

    static {
        for (Field field : CarpetClientConfigs.class.getDeclaredFields()) {
            Config annotation = field.getAnnotation(Config.class);
            if (annotation != null) {
                try {
                    IConfigBase configBase = (IConfigBase) field.get(null);
                    if (!(configBase instanceof CarpetClientIConfigBase)) {
                        CarpetClientMod.LOGGER.warn("[CarpetClient] {} is not a subclass of CarpetClientIConfigBase", configBase);
                    }
                    CarpetClientOption carpetClientOption = new CarpetClientOption(annotation.categories(), configBase);
                    OPTIONS.add(carpetClientOption);
                    for (String category : carpetClientOption.getCategories()) {
                        CATEGORY_TO_OPTION.computeIfAbsent(category, k -> Lists.newArrayList()).add(carpetClientOption);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
