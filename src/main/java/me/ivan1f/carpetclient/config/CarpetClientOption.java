package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.config.IConfigBase;

import java.util.List;

/**
 * Represents a config of CarpetClient. Including carpet rules and CarpetClient settings
 */
public class CarpetClientOption {
    private final String[] categories;
    private final IConfigBase option;

    public CarpetClientOption(String[] categories, IConfigBase option) {
        this.categories = categories;
        // option should be initialized before passing to here
        this.option = option;
    }

    public IConfigBase getOption() {
        return option;
    }

    public String[] getCategories() {
        return this.categories;
    }
}
