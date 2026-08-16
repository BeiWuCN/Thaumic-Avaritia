// 宇宙中子态素源质冶炼厂 Screen：复用原版冶炼炉 GUI 纹理（thaumaturge:gui_smelter.png）。
// 0 燃料 / 瞬间产出：不画燃烧条与进度条，只画源质存量条。
package com.blackmoss.thaumicavaritia.client.screen;

import com.blackmoss.thaumicavaritia.content.essentia.smeltery.BlockEntityCosmicNeutroniumEssentiaSmeltery;
import com.blackmoss.thaumicavaritia.content.essentia.smeltery.MenuCosmicNeutroniumEssentiaSmeltery;
import com.leclowndu93150.thaumaturge.client.screen.AbstractTCContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class CosmicNeutroniumEssentiaSmelteryScreen extends AbstractTCContainerScreen<MenuCosmicNeutroniumEssentiaSmeltery> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("thaumaturge", "textures/gui/gui_smelter.png");

    public CosmicNeutroniumEssentiaSmelteryScreen(MenuCosmicNeutroniumEssentiaSmeltery menu, Inventory inventory, Component title) {
        super(menu, inventory, title, TEXTURE, 176, 166);
    }

    @Override
    protected void extractLabels(@NonNull GuiGraphicsExtractor graphics, int xm, int ym) {}

    @Override
    protected void extractBackgroundOverlay(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        if (menu.blockEntity() != null) {
            BlockEntityCosmicNeutroniumEssentiaSmeltery smeltery = menu.blockEntity();
            int visScaled = smeltery.getVisScaled(46);
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    TEXTURE,
                    x + 61,
                    y + 12 + 48 - visScaled,
                    200,
                    48 - visScaled,
                    8,
                    visScaled,
                    256,
                    256);
            graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 60, y + 8, 232, 0, 10, 55, 256, 256);
        }
    }
}
