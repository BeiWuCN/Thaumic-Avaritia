package com.blackmoss.thaumicavaritia.client.screen;

import com.blackmoss.thaumicavaritia.content.menu.InfinitusCollectorMenu;
import committee.nova.mods.avaritia.Res;
import committee.nova.mods.avaritia.api.client.screen.BaseContainerScreen;
import committee.nova.mods.avaritia.client.screen.side.SideConfigButton;
import committee.nova.mods.avaritia.init.registry.ModTooltips;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InfinitusCollectorScreen extends BaseContainerScreen<InfinitusCollectorMenu> {
    private Button configButton;

    public InfinitusCollectorScreen(InfinitusCollectorMenu container, Inventory inventory, Component title) {
        super(container, inventory, title, Res.NEUTRON_COLLECTOR_TEX);
    }

    protected void init() {
        super.init();
        int x = this.leftPos;
        int y = this.topPos;
        this.configButton = new SideConfigButton(this, x - 20, y);
        this.addRenderableWidget(this.configButton);
    }

    protected void renderFg(GuiGraphicsExtractor pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int x = this.leftPos;
        int y = this.topPos;
        if (pMouseX > x + 99 && pMouseX < x + 104 && pMouseY > y + 30 && pMouseY < y + 50) {
            List<Component> tooltip = new ArrayList<>();
            if (this.getProgress() > 0) {
                double i = (double) this.getProgress() / (double) this.getTimeRequired();
                MutableComponent text = ModTooltips.PROGRESS.args(new Object[]{fraction(i)}).build();
                tooltip.add(text);
            }

            pGuiGraphics.setComponentTooltipForNextFrame(this.font, tooltip, pMouseX, pMouseY);
        }

    }

    protected void renderLabels(@NotNull GuiGraphicsExtractor stack, int mouseX, int mouseY) {
        String title = this.getTitle().getString();
        stack.text(this.font, title, 88 - this.font.width(title) / 2, 6, -12566464, false);
        stack.text(this.font, this.playerInventoryTitle, 8, 72, -12566464, false);
    }

    protected void renderBgs(GuiGraphicsExtractor pGuiGraphics, float pPartialTick, int pX, int pY) {
        int i = this.leftPos;
        int j = this.topPos;
        if (this.getProgress() > 0) {
            int i2 = this.getProgressBarScaled(18);
            pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, Res.NEUTRON_COLLECTOR_TEX, i + 99, j + 49 - i2, 176.0F, (float) (18 - i2), 4, i2, 256, 256);
        }

    }

    public int getProgress() {
        return (this.menu).getTileEntity() == null
                ? 0
                : (this.menu).getProgress();
    }

    public int getTimeRequired() {
        return (this.menu).getTileEntity() == null
                ? 0
                : this.menu.getTileEntity().getProductionTicks();
    }

    public int getProgressBarScaled(int pixels) {
        int i = Mth.clamp(this.getProgress(), 0, this.getTimeRequired());
        int j = this.getTimeRequired();
        return (int) (j != 0 && i != 0 ? (long) i * (long) pixels / (long) j : 0L);
    }
}
