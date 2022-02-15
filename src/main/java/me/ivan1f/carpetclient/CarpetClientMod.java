package me.ivan1f.carpetclient;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import me.ivan1f.carpetclient.config.CarpetClientConfigStorage;
import me.ivan1f.carpetclient.config.CarpetClientConfigs;
import me.ivan1f.carpetclient.config.KeybindProvider;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarpetClientMod implements ModInitializer {
    public static final String MOD_NAME = "CarpetClient";
    public static final String MOD_ID = "carpetclient";
    public static final String VERSION = "unknown";
    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitialize() {
        InitializationHandler.getInstance().registerInitializationHandler(() -> {
            ConfigManager.getInstance().registerConfigHandler(MOD_ID, new CarpetClientConfigStorage());
            InputEventHandler.getKeybindManager().registerKeybindProvider(new KeybindProvider());
            CarpetClientConfigs.initializeCallbacks();
        });
    }
}
