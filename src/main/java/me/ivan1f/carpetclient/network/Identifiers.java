package me.ivan1f.carpetclient.network;

import me.ivan1f.carpetclient.CarpetClientMod;
import net.minecraft.util.Identifier;

public class Identifiers {
    // S2C
    public static Identifier CARPET_RULES = id("carpet_rules");
    public static Identifier VALUE_CHANGED = id("value_changed");
    public static Identifier HI = id("hi");
    // C2S
    public static Identifier HELLO = id("hello");

    private static Identifier id(String path) {
        return new Identifier(CarpetClientMod.MOD_ID, path);
    }
}
