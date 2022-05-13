package me.ivan1f.carpetclient.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.util.InfoUtils;
import me.ivan1f.carpetclient.CarpetClientMod;
import me.ivan1f.carpetclient.config.option.CarpetClientConfigHotkey;
import me.ivan1f.carpetclient.gui.CarpetClientConfigGui;
import me.ivan1f.carpetclient.network.NetworkHandler;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarpetClientConfigs {
    public static boolean isCarpetRuleLoaded = false;
    public static final String CARPET_CLIENT = "carpet_client";
    public static final String ALL = "all";

    @Config(categories = {CARPET_CLIENT})
    public static final ConfigHotkey OPEN_CARPETCLIENT_CONFIG_GUI = new CarpetClientConfigHotkey("openCarpetClientConfigGui", "R,C");

    @Config(categories = {CARPET_CLIENT})
    public static final ConfigHotkey REFRESH_CARPET_RULES = new CarpetClientConfigHotkey("refreshCarpetRules", "");

    public static void initializeCallbacks() {
        OPEN_CARPETCLIENT_CONFIG_GUI.getKeybind().setCallback(CarpetClientConfigGui::onOpenGuiHotkey);
        REFRESH_CARPET_RULES.getKeybind().setCallback((action, keybind) -> {
            clearCarpetRules();
            NetworkHandler.requestCarpetRules();
            InfoUtils.printActionbarMessage("carpetclient.config.refreshCarpetRules.refreshed");
            CarpetClientConfigGui.getCurrentInstance().ifPresent(CarpetClientConfigGui::reDraw);
            return true;
        });
    }

    private static List<CarpetClientOption> OPTIONS = Lists.newArrayList();
    private static Set<String> CATEGORIES = Sets.newHashSet(CARPET_CLIENT, ALL);
    private static final Map<String, List<CarpetClientOption>> CATEGORY_TO_OPTION = Maps.newLinkedHashMap();
    private static final Map<IConfigBase, CarpetClientOption> CONFIG_TO_OPTION = Maps.newLinkedHashMap();

    public static void clearCarpetRules() {
        OPTIONS = OPTIONS.stream().filter(CarpetClientConfigs::isCarpetClientSetting).collect(Collectors.toList());
        CATEGORIES = Sets.newHashSet(CARPET_CLIENT, ALL);
        CATEGORY_TO_OPTION.clear();
        OPTIONS.forEach(option -> {
            for (String category : option.getCategories()) {
                addCategory(category);
                CATEGORY_TO_OPTION.computeIfAbsent(category, k -> Lists.newArrayList()).add(option);
            }
        });
        CONFIG_TO_OPTION.clear();
        OPTIONS.forEach(option -> CONFIG_TO_OPTION.put(option.getOption(), option));
        isCarpetRuleLoaded = false;
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
            CONFIG_TO_OPTION.put(rule.getOption(), rule);
        }
    }

    public static List<CarpetClientOption> getOptions(String categoryType) {
        return CATEGORY_TO_OPTION.getOrDefault(categoryType, Collections.emptyList());
    }

    public static Stream<IConfigBase> getAllConfigOptionStream() {
        return OPTIONS.stream().map(CarpetClientOption::getOption);
    }

    public static Stream<CarpetClientOption> getAllOptionStream() {
        return OPTIONS.stream();
    }

    public static Optional<CarpetClientOption> getOptionFromConfig(IConfigBase iConfigBase) {
        return Optional.ofNullable(CONFIG_TO_OPTION.get(iConfigBase));
    }

    public static boolean hasConfig(IConfigBase iConfigBase) {
        return getOptionFromConfig(iConfigBase).isPresent();
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
