package me.ivan1f.carpetclient.mixins.core.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.ConfigButtonKeybind;
import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.hotkeys.*;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.carpetclient.gui.CarpetClientConfigGui;
import me.ivan1f.carpetclient.gui.CarpetClientOptionLabel;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import javax.annotation.Nullable;

@Mixin(WidgetConfigOption.class)
public abstract class WidgetListConfigOptionsMixin extends WidgetConfigOptionBase<GuiConfigsBase.ConfigOptionWrapper> {
    @Shadow(remap = false)
    @Final
    protected IKeybindConfigGui host;

    @Shadow(remap = false)
    @Final
    protected GuiConfigsBase.ConfigOptionWrapper wrapper;

    @Mutable
    @Shadow(remap = false)
    @Final
    @Nullable
    protected KeybindSettings initialKeybindSettings;

    @Shadow(remap = false)
    protected abstract void addKeybindResetButton(int x, int y, IKeybind keybind, ConfigButtonKeybind buttonHotkey);

    @Unique
    private boolean initialBoolean;

    public WidgetListConfigOptionsMixin(int x, int y, int width, int height, WidgetListConfigOptionsBase<?, ?> parent, GuiConfigsBase.ConfigOptionWrapper entry, int listIndex) {
        super(x, y, width, height, parent, entry, listIndex);
    }

    @ModifyVariable(
            method = "addConfigOption",
            at = @At("HEAD"),
            argsOnly = true,
            index = 4,
            remap = false
    )
    private int rightAlignedConfigPanel(int labelWidth, int x, int y, float zLevel, int labelWidth_, int configWidth, IConfigBase config) {
        if (this.isCarpetClientConfigGui()) {
            labelWidth = this.width - configWidth - 59;
        }
        return labelWidth;
    }

    @Inject(
            method = "addHotkeyConfigElements",
            at = @At(value = "HEAD"),
            remap = false,
            cancellable = true
    )
    private void carpetClientCustomConfigGui(int x, int y, int configWidth, String configName, IHotkey config, CallbackInfo ci) {
        if (this.isCarpetClientConfigGui()) {
            if ((config).getKeybind() instanceof KeybindMulti) {
                this.addButtonAndHotkeyWidgets(x, y, configWidth, config);
                ci.cancel();
            }
        }
    }

    @ModifyArgs(
            method = "addConfigOption",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;addLabel(IIIII[Ljava/lang/String;)V",
                    remap = false
            ),
            remap = false
    )
    private void useMyBetterOptionLabelForCarpetClient(Args args, int x_, int y_, float zLevel, int labelWidth, int configWidth, IConfigBase config) {
        if (isCarpetClientConfigGui()) {
            int x = args.get(0);
            int y = args.get(1);
            int width = args.get(2);
            int height = args.get(3);
            int textColor = args.get(4);
            String[] lines = args.get(5);
            if (lines.length != 1) {
                return;
            }

            args.set(5, null);  // cancel original call

            CarpetClientOptionLabel label = new CarpetClientOptionLabel(x, y, width, height, textColor, config.getName(), lines);
            this.addWidget(label);
        }
    }

    private void addButtonAndHotkeyWidgets(int x, int y, int configWidth, IHotkey config) {
        IKeybind keybind = config.getKeybind();

        int triggerBtnWidth = 60;
        ButtonGeneric triggerButton = new ButtonGeneric(
                x, y, triggerBtnWidth, 20,
                StringUtils.translate("carpetclient.gui.trigger_button.text"),
                StringUtils.translate("carpetclient.gui.trigger_button.hover", config.getName())
        );
        IHotkeyCallback callback = ((KeybindMultiAccessor) keybind).getCallback();
        this.addButton(triggerButton, (button, mouseButton) -> {
            KeyAction activateOn = keybind.getSettings().getActivateOn();
            if (activateOn == KeyAction.BOTH || activateOn == KeyAction.PRESS) {
                callback.onKeyAction(KeyAction.PRESS, keybind);
            }
            if (activateOn == KeyAction.BOTH || activateOn == KeyAction.RELEASE) {
                callback.onKeyAction(KeyAction.RELEASE, keybind);
            }
        });

        x += triggerBtnWidth + 2;
        configWidth -= triggerBtnWidth + 2 + 22;

        ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
        x += configWidth + 2;

        this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, config.getName(), this.parent, this.host.getDialogHandler()));
        x += 24;

        this.addButton(keybindButton, this.host.getButtonPressListener());
        this.addKeybindResetButton(x, y, keybind, keybindButton);
    }

    private boolean isCarpetClientConfigGui() {
        return this.parent instanceof WidgetListConfigOptions && ((WidgetListConfigOptionsAccessor) this.parent).getParent() instanceof CarpetClientConfigGui;
    }
}
