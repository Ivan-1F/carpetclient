package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import me.ivan1f.carpetclient.CarpetClientMod;

import java.util.List;
import java.util.stream.Collectors;

public class KeybindProvider implements IKeybindProvider {
    private static List<IHotkey> allCustomHotkeys = CarpetClientConfigs.getAllConfigOptionStream().
            filter(option -> option instanceof IHotkey).
            map(option -> (IHotkey) option).
            collect(Collectors.toList());

    private static void fetch() {
        allCustomHotkeys = CarpetClientConfigs.getAllConfigOptionStream().
                filter(option -> option instanceof IHotkey).
                map(option -> (IHotkey) option).
                collect(Collectors.toList());
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        fetch();
        allCustomHotkeys.forEach(iHotkey -> manager.addKeybindToMap(iHotkey.getKeybind()));
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        fetch();
        manager.addHotkeysForCategory(CarpetClientMod.MOD_NAME, "carpetclient.hotkeys.category.main", allCustomHotkeys);
    }
}
