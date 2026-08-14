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

        // Towards Infinity (root node = Extremely Primordial Pearl, entropy-reversing 3 stages)
        add("research.thaumaturge.unlock_thaumicavaritia.title", "Towards Infinity");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_0", "The primordial pearl — a remnant of creation, holding pure primal essence. So precious, yet most have degenerated into nodules, even motes; left alone, they will fade into nothing. I shall take a single pearl as a sample and, with the knowledge of auromancy and infusion, dissect the nature of its decay to find a way to reverse it.");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_1", "Through study after study in auromancy and infusion, I have finally come to understand it. Nine dying remnants will serve as the offering — my infusion altar is finally ready to be put to use, to let them be reborn in my hands.");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_2", "How beautiful, this is truly... wonderful. Pure primal essence reborn in my hands. Perhaps my insatiable greed for greater power is, little by little, dragging me toward the edge of madness.");

        // Thaumic Avaritia category root (hidden, centered at 0,0)
        add("research.thaumaturge.base_thaumicavaritia.title", "Basic Thaumic Avaritia");
        add("research.thaumaturge.base_thaumicavaritia.stage_0", "I feel... my time is being wasted. It could be spent on far more meaningful things. Rather than repeating the same toil over and over, I would rather... obtain... greater power...");

        // Akashic Records (bridging hub)
        add("research.thaumaturge.ta_akashic_record.title", "Akashic Records");
        add("research.thaumaturge.ta_akashic_record.stage_0", "Guided by the ultimate construct of greed, I have at last awakened this book of all knowledge. It holds every secret of thaumaturgy, now bent to my will.");
        add("research.thaumaturge.ta_akashic_record.stage_1", "My greedy creations activated the Akashic Records, and countless aspects flooded into my mind — thaumaturgy has become mine to command.");

        // Wand industry branch
        add("research.thaumaturge.ta_wand_cap_crystal.title", "Charged Crystal Matrix Cap");
        add("research.thaumaturge.ta_wand_cap_crystal.stage_0", "Winding crystal matrix about the rod, the cost of Vis is crushed to almost nothing. Greedy industry, too, can serve the arcane.");

        add("research.thaumaturge.ta_wand_cap_crystal_inter.title", "Inter Crystal Matrix Cap");
        add("research.thaumaturge.ta_wand_cap_crystal_inter.stage_0", "A crystal cap stripped of its halo — no longer loud, yet still bearing that infinitely compressed arcane efficiency.");

        add("research.thaumaturge.ta_wand_rod_neutronium.title", "Studded Cosmic Neutronium Rod");
        add("research.thaumaturge.ta_wand_rod_neutronium.stage_0", "The capacity of neutronium is near-infinite, and it refills itself. This wand shall never run dry again.");

        // Arcane Singularity Focus (apex)
        add("research.thaumaturge.ta_arcane_singularity_focus.title", "Arcane Singularity Focus");
        add("research.thaumaturge.ta_arcane_singularity_focus.stage_0", "When the crystal cap and the neutron rod become one, the arcane singularity is no longer bound by complexity — the infinite was always meant to be so.");
        add("research.thaumaturge.ta_arcane_singularity_focus.stage_1", "I forged this core from greed itself; it devours every boundary of spellcraft. Thaumaturgy, in my hands, has at last reached the infinite.");

        add("wand.thaumaturge.cap.crystal", "Crystal Matrix");
        add("wand.thaumaturge.rod.infinity", "Studded Cosmic Neutronium");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");
    }
}
