package com.blackmoss.thaumicavaritia.client.screen;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
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
            Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "textures/gui/cosmic_neutronium_essentia_smeltery.png");

    public CosmicNeutroniumEssentiaSmelteryScreen(MenuCosmicNeutroniumEssentiaSmeltery menu, Inventory inventory, Component title) {
        super(menu, inventory, title, TEXTURE, 176, 166);
    }

    @Override
    protected void extractLabels(@NonNull GuiGraphicsExtractor graphics, int xm, int ym) {
    }

    @Override
    protected void extractBackgroundOverlay(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        if (menu.blockEntity() != null) {
            BlockEntityCosmicNeutroniumEssentiaSmeltery smeltery = menu.blockEntity();
            int visScaled = 0;
            if (smeltery != null) {
                visScaled = smeltery.getVisScaled(46);
            }
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    TEXTURE,
                    x + 61,
                    y + 12 + 38 - visScaled,
                    200,
                    38 - visScaled,
                    8,
                    visScaled,
                    256,
                    256);
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    TEXTURE,
                    x + 60,
                    y + 8,
                    232,
                    0,
                    10,
                    55,
                    256,
                    256);
        }
    }
}
