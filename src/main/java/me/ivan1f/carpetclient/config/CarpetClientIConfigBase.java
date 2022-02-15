package me.ivan1f.carpetclient.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.carpetclient.CarpetClientMod;

import java.util.Optional;

/**
 * Translation stuff here is only for CarpetClient settings
 * Carpet rules' translations will be fetched from the server and manually set to IConfigBase
 */
public interface CarpetClientIConfigBase extends IConfigBase {
    String CARPETCLIENT_NAMESPACE_PREFIX = CarpetClientMod.MOD_ID + ".config.";
    String COMMENT_SUFFIX = ".comment";
    String PRETTY_NAME_SUFFIX = ".pretty_name";

    @Override
    default String getConfigGuiDisplayName() {
        return StringUtils.translate(CARPETCLIENT_NAMESPACE_PREFIX + this.getName());
    }
}
