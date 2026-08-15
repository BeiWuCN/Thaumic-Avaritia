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

        add("research_category.thaumicavaritia.thaumicavaritia", "Thaumic Avaritia");

        // Towards Infinity (root node = Extremely Primordial Pearl, entropy-reversing 3 stages)
        add("research.thaumaturge.unlock_thaumicavaritia.title", "Towards Infinity");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_0", "The primordial pearl — a remnant of creation, holding pure primal essence. So precious, yet most have degenerated into nodules, even motes; left alone, they will fade into nothing. I shall take a single pearl as a sample and, with the knowledge of auromancy and infusion, dissect the nature of its decay to find a way to reverse it.");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_1", "Through study after study in auromancy and infusion, I have finally come to understand it. Nine dying remnants will serve as the offering — my infusion altar is finally ready to be put to use, to let them be reborn in my hands.");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_2", "How beautiful, this is truly... wonderful. Pure primal essence reborn in my hands. Perhaps my insatiable greed for greater power is, little by little, dragging me toward the edge of madness.");

        // Thaumic Avaritia category root (hidden, centered at 0,0)
        add("research.thaumaturge.base_thaumicavaritia.title", "Basic Thaumic Avaritia");
        add("research.thaumaturge.base_thaumicavaritia.stage_0", "I feel... my time is being wasted. It could be spent on far more meaningful things. Rather than repeating the same toil over and over, I would rather... obtain... greater power... or rather... infinite power...");

        // Akashic Records (bridging hub)
        add("research.thaumaturge.akashic_record.title", "Akashic Records");
        add("research.thaumaturge.akashic_record.stage_0", "Guided by the ultimate construct of greed, I have at last awakened this book of all knowledge. It holds every secret of thaumaturgy, now bent to my will.");
        add("research.thaumaturge.akashic_record.stage_1", "My greedy creations activated the Akashic Records, and countless aspects flooded into my mind — thaumaturgy has become mine to command.");

        // Wand branch: Charged Crystal Matrix Cap
        add("research.thaumaturge.wand_cap_crystal_matrix.title", "Charged Crystal Matrix Cap");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_0", "I believe crystal forged from diamond, netherite, and a nether star can bear immense arcane power — but before that, I need to plan things out...");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_1", "It turns out crystal is excellent at reducing Vis cost, but not so good at channeling it out. That makes me think of the void cap — void metal excels at conducting Vis. Perhaps I could try adding some salis mundus... or maybe the crystal matrix's potential simply hasn't been unleashed yet...");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_2", "The test succeeded! As I suspected, the crystal matrix must be charged to unleash its full power. I'll just add a few materials — some primal aura to keep it stable, and maybe... a few primordial pearls?");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_3", "The infusion went off without a hitch, and the latent power of the crystal matrix has been unleashed! As a cap material it is uniquely powerful — it drains only 10% of my Vis!");

        // Wand branch: Studded Cosmic Neutronium Rod
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.title", "Studded Cosmic Neutronium Rod");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_0", "Neutronium is a fine thing — it can contain anything... but what about Vis? I think I could store Vis in a neutronium vessel. Perhaps I should start by reworking the primal rod core.");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_1", "It works well, but the Vis drains away in no time. What if I add a little infinity into it? Maybe it could generate Vis on its own...");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_2", "I was wrong — not being able to hold it isn't a flaw, it's a blessing! The rod that encases infinity looks like nothing but a stick, yet this neutronium rod is truly connected to the Vis of the whole universe! I will never run dry again!");

        // Arcane Singularity Focus (apex)
        add("research.thaumaturge.arcane_singularity_focus.title", "Arcane Singularity Focus");
        add("research.thaumaturge.arcane_singularity_focus.stage_0", "Now that I command infinite Vis, why should I still be bound by a focus's limits? It's time to forge a core as mighty as a singularity! Let's start by carving up this ordinary master focus.");
        add("research.thaumaturge.arcane_singularity_focus.stage_1", "It seems I hardly need to study anything for this — it's the simplest step of all. Sheathe it in neutronium and make the core of infinity within. I mean simple in theory, not in materials.");
        add("research.thaumaturge.arcane_singularity_focus.stage_2", "This core — infinite, paradoxical, void — is the end of all spellcraft.");

        add("wand.thaumaturge.cap.crystal_matrix", "Crystal Matrix");
        add("wand.thaumaturge.rod.studded_cosmic_neutronium", "Studded Cosmic Neutronium");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");
    }
}
