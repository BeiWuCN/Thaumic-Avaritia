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
        add("item.thaumicavaritia.wand_cap_crystal", "Wand Cap: Crystal Matrix");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium", "Wand Rod: Studded Cosmic Neutronium");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");
    }
}
