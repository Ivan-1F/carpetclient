package me.ivan1f.carpetclient.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import me.ivan1f.carpetclient.CarpetClientMod;
import me.ivan1f.carpetclient.config.CarpetClientConfigs;
import me.ivan1f.carpetclient.config.CarpetClientOption;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class CarpetClientConfigGui extends GuiConfigsBase {
    @Nullable
    private static CarpetClientConfigGui currentInstance = null;
    private static String category = CarpetClientConfigs.CARPET_CLIENT;

    public CarpetClientConfigGui() {
        super(10, 50, CarpetClientMod.MOD_ID, null, "carpetclient.gui.title", CarpetClientMod.VERSION);
        currentInstance = this;
    }

    @Override
    public void removed() {
        super.removed();
        currentInstance = null;
    }

    public static Optional<CarpetClientConfigGui> getCurrentInstance() {
        return Optional.ofNullable(currentInstance);
    }

    public static boolean onOpenGuiHotkey(KeyAction keyAction, IKeybind iKeybind) {
        GuiBase.openGui(new CarpetClientConfigGui());
        return true;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (String category : CarpetClientConfigs.getCategories()) {
            x += this.createNavigationButton(x, y, category);
        }
    }

    private int createNavigationButton(int x, int y, String category) {
        ButtonGeneric button = new ButtonGeneric(x, y, -1, 20, category);
        button.setEnabled(!CarpetClientConfigGui.category.equals(category));
        this.addButton(button, (b, mouseButton) -> {
            CarpetClientConfigGui.category = category;
            this.reDraw();
        });
        return button.getWidth() + 2;
    }

    public void reDraw() {
        this.reCreateListWidget(); // apply the new config width
        Objects.requireNonNull(this.getListWidget()).resetScrollbarPosition();
        this.initGui();
    }

    @Override
    protected int getConfigWidth() {
        return 160;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<IConfigBase> options = CarpetClientConfigs.
                getOptions(CarpetClientConfigGui.category).
                stream().
                map(CarpetClientOption::getOption).
                sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName())).
                collect(Collectors.toList());
        return ConfigOptionWrapper.createFor(options);
    }
}
