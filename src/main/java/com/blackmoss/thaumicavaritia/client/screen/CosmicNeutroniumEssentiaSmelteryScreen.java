package com.blackmoss.thaumicavaritia.client.screen;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.CosmicNeutroniumEssentiaSmelteryBlockEntity;
import com.blackmoss.thaumicavaritia.menu.CosmicNeutroniumEssentiaSmelteryMenu;
import com.leclowndu93150.thaumaturge.client.screen.AbstractTTContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class CosmicNeutroniumEssentiaSmelteryScreen extends AbstractTTContainerScreen<CosmicNeutroniumEssentiaSmelteryMenu> {

    private static final Identifier TEXTURE = ThaumicAvaritia.identifier("textures/gui/cosmic_neutronium_essentia_smeltery.png");

    public CosmicNeutroniumEssentiaSmelteryScreen(CosmicNeutroniumEssentiaSmelteryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, TEXTURE, 176, 166);
    }

    @Override
    protected void extractLabels(@NonNull GuiGraphicsExtractor graphics, int xm, int ym) {
        String title = this.getTitle().getString();
        graphics.text(this.font, title, 88 - this.font.width(title) / 2, 6, -12566464, false);
        graphics.text(this.font, this.playerInventoryTitle, 8, 72, -12566464, false);
    }

    @Override
    protected void extractBackgroundOverlay(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        if (menu.blockEntity() != null) {
            CosmicNeutroniumEssentiaSmelteryBlockEntity smeltery = menu.blockEntity();
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
