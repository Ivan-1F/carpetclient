package me.ivan1f.carpetclient.gui;

import fi.dy.masa.malilib.gui.widgets.WidgetLabel;

import java.util.List;

public class CarpetClientOptionLabel extends WidgetLabel {
    public static final double TRANSLATION_SCALE = 0.65;

    public final String name;

    public CarpetClientOptionLabel(int x, int y, int width, int height, int textColor, String name, String... text) {
        super(x, y, width, height, textColor, text);
        this.name = name;
    }

    public CarpetClientOptionLabel(int x, int y, int width, int height, int textColor, String name, List<String> lines) {
        super(x, y, width, height, textColor, lines);
        this.name = name;
    }
}
