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

        add("aspect.thaumicavaritia.infinitus", "Infinitus");
        add("aspect.thaumicavaritia.infinitus.desc", "Infinite, boundless, without end.");
        add("aspect.thaumicavaritia.infinitus.help", "Never-ending.");

        add("item.thaumicavaritia.extremely_primordial_pearl", "Extremely Primordial Pearl");
        add("item.thaumicavaritia.akashic_record", "Akashic Records");
        add("item.thaumicavaritia.wand_cap_crystal_matrix", "Charged Crystal Matrix Cap");
        add("item.thaumicavaritia.wand_cap_crystal_matrix_inter", "Inert Crystal Matrix Cap");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium", "Studded Cosmic Neutronium Rod");
        add("item.thaumicavaritia.arcane_singularity_focus", "Arcane Singularity Focus");
        add("item.thaumicavaritia.infinity_scribing_tools", "Divinely Inspired Scribing Tools");

        // Item descriptions (lore)
        add("item.thaumicavaritia.extremely_primordial_pearl.desc", "The origin of the world.");
        add("item.thaumicavaritia.akashic_record.desc", "The weight of knowledge.");
        add("item.thaumicavaritia.infinity_scribing_tools.desc", "A thousand-mile journey begins with a single stroke.");
        add("item.thaumicavaritia.wand_cap_crystal_matrix.desc", "Crystal clear.");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium.desc", "A handheld black hole.");
        add("item.thaumicavaritia.arcane_singularity_focus.desc", "Do not consume.");

        add("item.thaumicavaritia.causality_collapser_pearl", "Causality Collapser Pearl");
        add("item.thaumicavaritia.causality_collapser_pearl.desc", "Unsolved mystery");

        // Divinely Inspired Pen and Ink
        add("research.thaumaturge.infinity_scribing_tools.title", "Divinely Inspired Pen and Ink");
        add("research.thaumaturge.infinity_scribing_tools.stage_0", "Bards and common folk alike have surely used the \"pen\" — compared to millennia of knotted cords and carved stone tablets, the pen is undoubtedly a great invention. The only flaw is... how quickly the ink runs out. And upon this research table, that flaw is magnified to the extreme — every stroke of study costs the tools a measure of durability. If only the ink could be inexhaustible...");
        add("research.thaumaturge.infinity_scribing_tools.stage_1", "With infinity ingot as the bone and neutronium as the guide, I have let the infusion altar bestow upon this pen a well of ink that never runs dry. From now on, every stroke flows as if guided by divine inspiration — never again will I fret over ink.");

        add("research_category.thaumicavaritia.thaumicavaritia", "Thaumic Avaritia");

        // Towards Infinity (root node = Extremely Primordial Pearl, entropy-reversing 3 stages)
        add("research.thaumaturge.unlock_thaumicavaritia.title", "Towards Infinity");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_0", "The primordial pearl — a remnant of creation, holding pure primal essence. So precious, yet most have degenerated into nodules, even motes; left alone, they will fade into nothing.<BR>I take a single fading pearl as my sample, poring over the tomes of auromancy and infusion to dissect the nature of its decay. This is no ordinary rot — it is a slow, utter self-dissolution. If left alone, even the last mote would fade into nothing. I must find a way to reverse this decay.");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_1", "Through study after study in auromancy and infusion, I have finally come to understand it — what we call decay is merely primal power slowly guttering out across the ages. Then let the fires of my infusion altar rekindle them!<BR>Nine dying remnants will serve as the offering, and my precious sample shall be the catalyst upon the altar. Let them be reborn in my hands, condensed into the purest primal essence that has never before existed.");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_2", "How beautiful... this is truly wonderful. The purest primal essence reborn in my hands, like a newborn star. Brighter than any gem, closer to the world's source than any creation.<BR>Yet why am I still unsatisfied? Perhaps my insatiable greed for greater power is, little by little, dragging me toward the edge of madness...");

        // Thaumic Avaritia category root (hidden, centered at 0,0)
        add("research.thaumaturge.base_thaumicavaritia.title", "Basic Thaumic Avaritia");
        add("research.thaumaturge.base_thaumicavaritia.stage_0", "I feel... my time is being wasted. It could be spent on far more meaningful things — rather than repeating the same toil over and over, I would rather... obtain... greater power...<BR>Greed is never sated. Perhaps one day I shall crave a thing with no end, no bounds, no limit...");

        // Akashic Records (bridging hub)
        add("research.thaumaturge.akashic_record.title", "Akashic Records");
        add("research.thaumaturge.akashic_record.stage_0", "Guided by the ultimate construct of greed, I have at last awakened this book of all knowledge. It holds every secret of thaumaturgy past and present — the nature of all things, the truth of magic, all within.<BR>From this day forth, this book of all knowledge serves me. No wall of knowledge shall bar my way again.");
        add("research.thaumaturge.akashic_record.stage_1", "My greedy creations activated the Akashic Records, and countless aspects flooded into my mind like a tide. I can almost feel the weight of knowledge, almost driven mad by it.<BR>Thaumaturgy is now mine to command. But this is not enough... my greed has only just awakened.");

        // Wand branch: Charged Crystal Matrix Cap
        add("research.thaumaturge.wand_cap_crystal_matrix.title", "Charged Crystal Matrix Cap");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_0", "Diamond, netherite, nether star — crystal fused from all three may well bear immense arcane power. But before that, I need to plan things out carefully...");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_1", "As it turns out, crystal excels at suppressing Vis cost, yet it is poor at channelling Vis out. That brings the void cap to mind — void metal is the finest conductor of all.<BR>Perhaps some salis mundus as a flux would help? Or maybe the crystal matrix's potential simply hasn't been unleashed yet...");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_2", "The test succeeded! Just as I suspected, the crystal matrix must be charged to unleash its full power. I only need to add a few materials — a handful of primal aura to keep it stable, and perhaps... a few primordial pearls?");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_3", "The infusion passed without a hitch, and the latent power of the crystal matrix is finally unleashed! As a cap material it is uniquely mighty — it drains only 10% of my Vis!");

        // Wand branch: Studded Cosmic Neutronium Rod
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.title", "Studded Cosmic Neutronium Rod");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_0", "Neutronium is a fine thing — it can contain anything... but what about Vis? I believe I could store Vis within a neutronium vessel. Perhaps I should start by reworking the primal rod core.");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_1", "It works well, yet the Vis drains away in no time. Storing it is no lasting solution... So, what if I add a little infinity into it? Perhaps it could truly generate Vis on its own...");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_2", "I was wrong — not being able to hold the Vis isn't a flaw, it's a blessing! Neutronium encasing infinity looks like nothing but a stick, yet this rod is truly connected to the Vis of the whole universe!<BR>From now on, I will never run dry again!");

        // Arcane Singularity Focus (apex)
        add("research.thaumaturge.arcane_singularity_focus.title", "Arcane Singularity Focus");
        add("research.thaumaturge.arcane_singularity_focus.stage_0", "Now that I command infinite Vis, why should I still be bound by a focus's limits? It's time to forge a core as mighty as a singularity!<BR>Let's start by carving up this ordinary master focus.");
        add("research.thaumaturge.arcane_singularity_focus.stage_1", "It seems this step hardly requires any study at all. Sheathe it in neutronium as a shell, and forge the core of infinity within — when I say simple, I mean simple in theory, not in materials.");
        add("research.thaumaturge.arcane_singularity_focus.stage_2", "This core — infinite, paradoxical, born of the void and returning to nothing — is the end of all spellcraft.");

        add("wand.thaumaturge.cap.crystal_matrix", "Crystal Matrix");
        add("wand.thaumaturge.rod.studded_cosmic_neutronium", "Studded Cosmic Neutronium");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");
    }
}
