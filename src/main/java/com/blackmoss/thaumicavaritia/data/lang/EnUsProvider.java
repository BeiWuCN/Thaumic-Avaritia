package com.blackmoss.thaumicavaritia.data.lang;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EnUsProvider extends LanguageProvider {

    public EnUsProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.thaumicavaritia", "Thaumic Avaritia");
        jade();

        addConfiguration("use_thaumicavaritia_recipes", "Reworked Infinity Recipes",
                "Use Thaumic Avaritia's reworked avaritia recipes (true, default), or fall back to the original Avaritia recipes (false).");

        add("aspect.thaumicavaritia.infinitus", "Infinitus");
        add("aspect.thaumicavaritia.infinitus.desc", "Infinite, Boundless, Without end");
        add("aspect.thaumicavaritia.infinitus.help", "Never-ending.");

        addItem(TAItems.EXTREMELY_PRIMORDIAL_PEARL, "Extremely Primordial Pearl");
        addItem(TAItems.AKASHIC_RECORDS, "Akashic Records");
        addItem(TAItems.CRYSTAL_MATRIX_WAND_CAP, "Charged Crystal Matrix Cap");
        addItem(TAItems.INTER_CRYSTAL_MATRIX_WAND_CAP, "Inert Crystal Matrix Cap");
        addItem(TAItems.NEUTRONIUM_WAND_ROD, "Studded Cosmic Neutronium Rod");
        addItem(TAItems.ARCANE_SINGULARITY_FOCUS, "Arcane Singularity Focus");
        addItem(TAItems.INFINITY_SCRIBING_TOOLS, "Divinely Inspired Scribing Tools");
        addItem(TAItems.CAUSALITY_COLLAPSER_PEARL, "Causality Collapser Pearl");
        addItem(TAItems.CRYSTAL_MATRIX_PLATE, "Crystal Matrix Plate");
        addItem(TAItems.NEUTRON_PLATE, "Neutron Plate");
        addItem(TAItems.INFINITY_PLATE, "Infinity Plate");
        addItem(TAItems.INFINITUS_CRYSTAL, "Infinitus Crystal");

        addBlock(TABlocks.LIMIT_ALCHEMICAL_CONSTRUCT, "Limit Alchemical Construct");
        addBlock(TABlocks.LIMIT_FRAME, "Limit Frame");
        addBlock(TABlocks.INFINITY_CHARGER, "Gushing Charger");
        addBlock(TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY, "Cosmic Neutronium Essentia Smeltery");
        addBlock(TABlocks.NODE_ANCHOR, "Node Anchor");
        addBlock(TABlocks.ORIGIN_JAR, "Origin Jar");
        addBlock(TABlocks.INFINITUS_COLLECTOR, "Infinitus Collector");

        addEntityType(TAEntities.CAUSALITY_COLLAPSER_PEARL, "Causality Collapser Pearl");

        addTooltip("extremely_primordial_pearl", "The origin of the world.");
        addTooltip("akashic_record", "The weight of knowledge.");
        addTooltip("infinity_scribing_tools", "A thousand-mile journey begins with a single stroke.");
        addTooltip("wand_cap_crystal_matrix", "Crystal clear.");
        addTooltip("wand_rod_studded_cosmic_neutronium", "A handheld black hole.");
        addTooltip("arcane_singularity_focus", "Do not consume.");
        addTooltip("causality_collapser_pearl", "Remember to wear sunglasses");
        addTooltip("infinity_charger", "A kindness repaid as a gushing spring.");
        addTooltip("cosmic_neutronium_essentia_smeltery", "Maximum efficiency.");
        addTooltip("node_anchor", "You stay right here.");
        addTooltip("origin_jar", "The origin of all aspects.");
        addTooltip("neutron_plate", "All in order.");
        addTooltip("infinity_plate", "Not an invincible board.");
        addTooltip("limit_alchemical_construct", "Sage's material.");
        addTooltip("limit_frame", "Frame and rule.");
        addTooltip("infinitus_crystal", "The two words 'infinity' engraved upon it.");
        addTooltip("infinitus_collector", "Sieving out the world's undertone.");

        add("research_category.thaumicavaritia.thaumicavaritia", "Thaumic Avaritia");

        addResearch("unlock_thaumicavaritia", "Towards Infinity",
                "The primordial pearl — a remnant of creation, holding pure primal essence. So precious, yet most have degenerated into nodules, even motes; left alone, they will fade into nothing.<BR>I take a single fading pearl as my sample, poring over the tomes of auromancy and infusion to dissect the nature of its decay. This is no ordinary rot — it is a slow, utter self-dissolution. If left alone, even the last mote would fade into nothing. I must find a way to reverse this decay.",
                "Through study after study in auromancy and infusion, I have finally come to understand it — what we call decay is merely primal power slowly guttering out across the ages. Then let the fires of my infusion altar rekindle them!<BR>Nine dying remnants will serve as the offering, and my precious sample shall be the catalyst upon the altar. Let them be reborn in my hands, condensed into the purest primal essence that has never before existed.",
                "How beautiful... this is truly wonderful. The purest primal essence reborn in my hands, like a newborn star. Brighter than any gem, closer to the world's source than any creation.<BR>Yet why am I still unsatisfied? Perhaps my insatiable greed for greater power is, little by little, dragging me toward the edge of madness...");

        addResearch("base_thaumicavaritia", "Basic Thaumic Avaritia",
                "I feel... my time is being wasted. It could be spent on far more meaningful things — rather than repeating the same toil over and over, I would rather... obtain... greater power...<BR>Greed is never sated. Perhaps one day I shall crave a thing with no end, no bounds, no limit...");

        addResearch("akashic_record", "Akashic Records",
                "Guided by the ultimate construct of greed, I have at last awakened this book of all knowledge. It holds every secret of thaumaturgy past and present — the nature of all things, the truth of magic, all within.<BR>From this day forth, this book of all knowledge serves me. No wall of knowledge shall bar my way again.",
                "My greedy creations activated the Akashic Records, and countless aspects flooded into my mind like a tide. I can almost feel the weight of knowledge, almost driven mad by it.<BR>Thaumaturgy is now mine to command. But this is not enough... my greed has only just awakened.");

        addResearch("infinity_scribing_tools", "Divinely Inspired Pen and Ink",
                "Bards and common folk alike have surely used the \"pen\" — compared to millennia of knotted cords and carved stone tablets, the pen is undoubtedly a great invention. The only flaw is... how quickly the ink runs out. And upon this research table, that flaw is magnified to the extreme — every stroke of study costs the tools a measure of durability. If only the ink could be inexhaustible...",
                "With infinity ingot as the bone and neutronium as the guide, I have let the infusion altar bestow upon this pen a well of ink that never runs dry. From now on, every stroke flows as if guided by divine inspiration — never again will I fret over ink.");

        addResearch("crystal_matrix_wand_cap", "Charged Crystal Matrix Cap",
                "Salis mundus cleanses the lattice, the primordial pearl pours in the essence, the primal charm anchors the form, and the master focus props up the conduit. Infusion after infusion, the crystal's latent might is coaxed out inch by inch — yet it remains but an inert crystal matrix, taking Vis in but never giving it out. To truly wake it, I must first set its veins in order.",
                "A frame alone is not enough — I press another layer of primordial pearl to steady the foundation, laying primal charms and master foci in turn, until the Vis truly flows along the crystal.",
                "Everything is in place; only a single primal charm is missing. Once I pour it into the inert crystal matrix, the power held within will be fully awakened.",
                "The infusion was far more perilous than I imagined. With the collapser at its core and eight endest pearls circling it, the flood of vacuousness and magic nearly tore the altar apart. I gritted my teeth through the most dangerous moment, and in the last wisp of chaos, I breathed a sliver of \"infinity\" into it.<BR>It worked. The pearl lies quietly in my palm, pitch black, yet a crimson glow churns deep within. It will explode, shatter rifts, yawn into a black hole that devours all — and when the void fades, it will return everything it consumed to this world as a node of aura. This is not destruction; it is creation in another guise.");

        addResearch("neutronium_wand_rod", "Studded Cosmic Neutronium Rod",
                "Neutronium is a fine thing — it can contain anything... but what about Vis? I believe I could store Vis within a neutronium vessel. Perhaps I should start by reworking the primal rod core.",
                "It works well, yet the Vis drains away in no time. Storing it is no lasting solution... So, what if I add a little infinity into it? Perhaps it could truly generate Vis on its own...",
                "I was wrong — not being able to hold the Vis isn't a flaw, it's a blessing! Neutronium encasing infinity looks like nothing but a stick, yet this rod is truly connected to the Vis of the whole universe!<BR>From now on, I will never run dry again!");

        addResearch("arcane_singularity_focus", "Arcane Singularity Focus",
                "Now that I command infinite Vis, why should I still be bound by a focus's limits? It's time to forge a core as mighty as a singularity!<BR>Let's start by carving up this ordinary master focus.",
                "It seems this step hardly requires any study at all. With an extremely primordial pearl at its core, supplemented by an enhancement core or a causality collapser — when I say simple, I mean simple in theory, not in materials.",
                "This core — infinite, paradoxical, born of the void and returning to nothing — is the end of all spellcraft.");

        addResearch("causality_collapser_pearl", "Causality Collapser Pearl",
                "The endest pearl is certainly a fine thing, yet that black pull sets my mind wandering... Perhaps this very pull could be used to condense aspects?",
                "The more I toy with the endest pearl, the more it strikes me how alike it is to the causality collapser — the collapser drives destructive energy into a rift and forces it to collapse upon itself, while the endest pearl devours all things with a black-hole pull, dragging everything into nothingness. One shatters space, the other swallows the world — plainly two faces of the very same power.<BR>So... what if I were to merge the two? A pearl that can shatter rifts, yawn into a black hole, and even spit what it has devoured back out as aspects? The thought keeps me awake at night.",
                "The infusion was far more perilous than I imagined. With the collapser at its core and eight endest pearls circling it, the flood of vacuousness and magic nearly tore the altar apart. I gritted my teeth through the most dangerous moment, and in the last wisp of chaos, I breathed a sliver of \"infinity\" into it.<BR>It worked. The pearl lies quietly in my palm, pitch black, yet a crimson glow churns deep within. It will explode, shatter rifts, yawn into a black hole that devours all — and when the void fades, it will return everything it consumed to this world as a node of aura. This is not destruction; it is creation in another guise.");

        addResearch("cosmic_neutronium_essentia_smeltery", "Cosmic Neutronium Essentia Smelter",
                "The void smelter can indeed refine essentia, yet it still drags along at a maddening pace — the slurry breaks down ever so slowly, and the essentia pollution it bleeds off fouls the whole surroundings besides.<BR>I've had my fill of that inefficiency and filth — to take it apart and recast it is surely the very place to begin my redesign.",
                "Tearing it down alone won't suffice. I need a material that can rewrite the smelting logic entirely — the pure neutronium sealed within the full matter cluster, aided by the neutron collector and neutron compressor, is precisely my answer.<BR>Let neutronium be the core, and scrub away both pollution and loss in one stroke. I mean to build a smelter that wastes not a wisp of essentia, nor exhales a speck of filth.",
                "As the last neutron compressor falls into place, a brand-new essentia smelter takes shape in my hands. It no longer grinds the slurry down at a crawl, nor reeks of essentia pollution — only efficiency, and perfection.<BR>From this day forth, smelting essentia shall be the simplest thing in the world.");

        addResearch("infinity_charger", "Gushing Charger",
                "The arcane workbench charger is convenient, sure — but it has a deadly flaw: it drinks the aura of the whole surrounding area bone dry.<BR>With the aura drained away, my wand is all but useless; worse still, the aura it violently churns up leaves behind more flux than aura, and that sort of build-up can kill. I need a way to keep the charging, yet stop it from starving the land or stirring up flux.",
                "My thoughts turn to those jarred nodes — sealed in glass, yet they still quietly condense and generate their own vis, never once begging the world for a sip of aura.<BR>If I could carry that self-sufficiency over, let the charger make its own vis, wouldn't it stop draining the aura and stop spawning flux altogether? The studded cosmic neutronium rod connects to the vis of the whole universe — this charger ought to share a similar knack.",
                "As the last jarred node sinks into the infusion's swirl, a gushing charger takes shape in my hands. It no longer greedily bleeds the surrounding aura dry, nor leaves behind any dangerous flux — it simply wells up with endless vis of its own.<BR>Set it atop an arcane workbench, and from this day forth, charging comes with no strings attached.");

        addResearch("node_anchor", "Node Anchor",
                "The node stabilizer and the node transducer are elegant creations in their own right, yet both share a glaring flaw: they are so terribly slow. The stabilizer can only hold a node in place, frozen and inert; the transducer must be fed charge for a long while before a node finally awakens.<BR>All this waiting is an affront to my greed. There must be a way to combine them — to anchor a node firmly while pouring power into it all at once, no patience required.",
                "Taking apart the transducer and the stabilizer, I study the mechanism that locks a node's state and the circuit that drives its charge. The frame of one, the circuitry of the other — neither is truly beyond me. The key is a core that can both pin and charge — and neutronium proves the very answer.<BR>Let but a sliver of eternal node be woven into the whole, and the node shall dwell forever in a self-sustaining eternity.",
                "The transducer's heart, the stabiliser's grip — all the magic bound fast within neutronium.<BR>Set beneath a node, it pins it firm as bedrock. No more waiting, no more waste.");
        addResearchAddenda("node_anchor",
                "A fascinating discovery: the aura within the gushing charger can be channelled straight into a node — the instant the purest aura enters, it bursts into all manner of aspects.<BR>The anchor does more than moor the vessel; it feeds it as well.");

        add("wand.thaumicavaritia.cap.crystal_matrix", "Crystal Matrix");
        add("wand.thaumicavaritia.rod.studded_cosmic_neutronium", "Studded Cosmic Neutronium");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");

        addMessage("locked", "You are not yet worthy to wield it");
    }

    private void jade() {
        add("config.jade.plugin_thaumicavaritia.origin_jar", "Origin Jar");
        add("config.jade.plugin_thaumicavaritia.infinitus_collector", "Infinitus Collector");

        add("jade.thaumicavaritia.origin_jar.contents", "Aspects x ∞");
        add("jade.thaumicavaritia.infinitus_collector.formed", "Formed");
        add("jade.thaumicavaritia.infinitus_collector.unformed", "Unformed");
        add("jade.thaumicavaritia.infinitus_collector.node", "Node: %s");
        add("jade.thaumicavaritia.infinitus_collector.progress", "Crystallization: %d%%");
        add("jade.thaumicavaritia.infinitus_collector.remaining_aspects", "Remaining Aspects: %s");
    }

    private void addResearch(String researchId, String title, String... stage) {
        add("research.%s.%s.title".formatted(ThaumicAvaritia.MODID, researchId), title);
        for (int i = 0; i < stage.length; i++) {
            add("research.%s.%s.stage_%d".formatted(ThaumicAvaritia.MODID, researchId, i), stage[i]);
        }
    }

    private void addResearchAddenda(String researchId, String... stage) {
        for (int i = 0; i < stage.length; i++) {
            add("research.%s.%s.addendum_%d".formatted(ThaumicAvaritia.MODID, researchId, i), stage[i]);
        }
    }

    private void addMessage(String id, String translations) {
        add("message.%s.%s".formatted(ThaumicAvaritia.MODID, id), translations);
    }

    private void addTooltip(String id, String translations) {
        add("tooltip.%s.%s".formatted(ThaumicAvaritia.MODID, id), translations);
    }

    private void addConfiguration(String configID, String translations) {
        addConfiguration(configID, translations, null);
    }

    private void addConfiguration(String configID, String translations, String tooltip) {
        add("%s.configuration.%s".formatted(ThaumicAvaritia.MODID, configID), translations);
        if (tooltip != null) {
            add("%s.configuration.%s.tooltip".formatted(ThaumicAvaritia.MODID, configID), tooltip);
        }
    }
}
