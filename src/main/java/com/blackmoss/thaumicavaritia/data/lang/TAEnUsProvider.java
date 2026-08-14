package com.blackmoss.thaumicavaritia.data.lang;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class TAEnUsProvider extends LanguageProvider {

    public TAEnUsProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.thaumicavaritia", "Thaumic Avaritia");

        add("item.thaumicavaritia.extremely_primordial_pearl", "Extremely Primordial Pearl");
        add("item.thaumicavaritia.akashic_record", "Akashic Records");
        add("item.thaumicavaritia.wand_cap_crystal", "Charged Crystal Matrix Cap");
        add("item.thaumicavaritia.wand_cap_crystal_inter", "Inter Crystal Matrix Cap");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium", "Studded Cosmic Neutronium Rod");
        add("item.thaumicavaritia.arcane_singularity_focus", "Arcane Singularity Focus");

        add("research_category.thaumicavaritia.thaumicavaritia", "Thaumic Avaritia");

        add("research.thaumicavaritia.unlock_thaumicavaritia.title", "Towards Infinity");
//        add("research.thaumicavaritia.unlock_thaumicavaritia.stage_0", "Ancient knowledge stirs at the edge of my consciousness. To reach the Arcane Infinity, I must first prove my mastery over golemcraft and artifice.");
//        add("research.thaumicavaritia.unlock_thaumicavaritia.stage_1", "The path is open. My creations now answer a deeper call, and a new realm of infinite possibilities awaits.");

        add("wand.thaumaturge.cap.crystal", "Crystal Matrix");
        add("wand.thaumaturge.rod.infinity", "Studded Cosmic Neutronium");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");
    }
}
