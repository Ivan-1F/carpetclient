package me.ivan1f.carpetclient.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.StringUtils;
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

    public static final Map<Integer, List<String>> PAGE_CATEGORY_MAP = Maps.newHashMap();
    public static int currentPage = 1;
    public static int maxPage = 0;

    private void setupPages() {
        PAGE_CATEGORY_MAP.clear();

        int x = 10;
        int page = 1;
        for (String category : CarpetClientConfigs.getCategories()) {
            if (x + this.getStringWidth(category) + 10 + 10 > this.width) {
                page += 1;
                x = 10;
            }
            x += this.getStringWidth(category) + 10 + 2;
            PAGE_CATEGORY_MAP.computeIfAbsent(page, k -> Lists.newArrayList()).add(category);
        }

        maxPage = page;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        this.setupPages();

        for (String category : PAGE_CATEGORY_MAP.get(currentPage)) {
            x += this.createNavigationButton(x, y, category);
        }

        int titleBarX = this.getStringWidth(this.title);

        titleBarX += 12 + 10;
        ButtonGeneric previous = new ButtonGeneric(
                titleBarX, 5, 20, 20, "<",
                StringUtils.translate("carpetclient.gui.previous.hover"));
        titleBarX += 20 + 2;
        ButtonGeneric next = new ButtonGeneric(
                titleBarX, 5, 20, 20, ">",
                StringUtils.translate("carpetclient.gui.next.hover"));

        if (currentPage == maxPage) next.setEnabled(false);
        if (currentPage == 1) previous.setEnabled(false);
        if (maxPage >= 2) {
            this.addButton(next, (button, mouseButton) -> {
                currentPage ++;
                next.setEnabled(true);
                previous.setEnabled(true);
                if (currentPage == maxPage) next.setEnabled(false);
                if (currentPage == 1) previous.setEnabled(false);
                this.reDraw();
                this.initGui();
            });
            this.addButton(previous, (button, mouseButton) -> {
                currentPage --;
                next.setEnabled(true);
                previous.setEnabled(true);
                if (currentPage == maxPage) next.setEnabled(false);
                if (currentPage == 1) previous.setEnabled(false);
                this.reDraw();
                this.initGui();
            });
        }
    }

    private int createNavigationButton(int x, int y, String category) {
        if (category.equals(CarpetClientConfigs.ALL) && !CarpetClientConfigs.isCarpetRuleLoaded) {
            // do not render category `all` when carpet rules are not loaded
            return 0;
        }
        String translatedCategory = category;
        if (category.equals(CarpetClientConfigs.CARPET_CLIENT) || category.equals(CarpetClientConfigs.ALL)) {
            translatedCategory = StringUtils.translate("carpetclient.gui.config_category." + category);
        }
        ButtonGeneric button = new ButtonGeneric(x, y, -1, 20, translatedCategory);
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

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (!CarpetClientConfigs.isCarpetRuleLoaded) {
            drawRightAlignedString(this.mc.textRenderer, StringUtils.translate("carpetclient.gui.carpet_rules_not_loaded.text"), this.width - 12, 10, 0xAA0000);  // dark red
        }
        if (maxPage >= 2) {
            this.drawStringWithShadow(String.format("%d / %d", currentPage, maxPage), this.getStringWidth(this.title) + 12 + 10 + 20 + 2 + 20 + 5, 12, COLOR_WHITE);
        }
    }
}
