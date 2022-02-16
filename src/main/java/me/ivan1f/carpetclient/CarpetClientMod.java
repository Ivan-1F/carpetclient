package me.ivan1f.carpetclient;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import me.ivan1f.carpetclient.config.CarpetClientConfigStorage;
import me.ivan1f.carpetclient.config.CarpetClientConfigs;
import me.ivan1f.carpetclient.config.KeybindProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarpetClientMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_NAME = "CarpetClient";
    public static final String MOD_ID = "carpetclient";
    public static String VERSION = "unknown";
    @Override
    public void onInitialize() {
        VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
        InitializationHandler.getInstance().registerInitializationHandler(() -> {
            ConfigManager.getInstance().registerConfigHandler(MOD_ID, new CarpetClientConfigStorage());
            InputEventHandler.getKeybindManager().registerKeybindProvider(new KeybindProvider());
            CarpetClientConfigs.initializeCallbacks();
        });
    }
}
