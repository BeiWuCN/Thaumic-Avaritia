package com.blackmoss.thaumicavaritia.client.screen;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.blockentity.CosmicNeutroniumEssentiaSmelteryBlockEntity;
import com.blackmoss.thaumicavaritia.content.menu.CosmicNeutroniumEssentiaSmelteryMenu;
import com.leclowndu93150.thaumaturge.client.screen.AbstractTCContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class CosmicNeutroniumEssentiaSmelteryScreen extends AbstractTCContainerScreen<CosmicNeutroniumEssentiaSmelteryMenu> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "textures/gui/cosmic_neutronium_essentia_smeltery.png");

    public CosmicNeutroniumEssentiaSmelteryScreen(CosmicNeutroniumEssentiaSmelteryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, TEXTURE, 176, 166);
    }

    @Override
    protected void extractLabels(@NonNull GuiGraphicsExtractor graphics, int xm, int ym) {
        // 标题居中（界面宽 176，中线 88）、物品栏名，参照 avaritia NeutronCollectorScreen 的 renderLabels。
        String title = this.getTitle().getString();
        graphics.text(this.font, title, 88 - this.font.width(title) / 2, 6, -12566464, false);
        graphics.text(this.font, this.playerInventoryTitle, 8, 72, -12566464, false);
    }

    @Override
    protected void extractBackgroundOverlay(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        if (menu.blockEntity() != null) {
            CosmicNeutroniumEssentiaSmelteryBlockEntity smeltery = menu.blockEntity();
            // 源质进度条：容器 (62,21)-(69,67) 高 46 宽 8；纹理素材区 (177,1)-(184,47) 宽 8 高 46。
            // 从下往上填充——源顶 = (47 - visScaled)，目标顶 = (67 - visScaled)，取 8 宽、visScaled 高的底部素材。
            int visScaled = 0;
            if (smeltery != null) {
                visScaled = smeltery.getVisScaled(46);
            }
            if (visScaled > 0) {
                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        TEXTURE,
                        x + 62,
                        y + 67 - visScaled,
                        177,
                        47 - visScaled,
                        8,
                        visScaled,
                        256,
                        256);
            }
        }
    }
}
