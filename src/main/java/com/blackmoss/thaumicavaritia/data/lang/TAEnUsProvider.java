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
        add("item.thaumicavaritia.causality_collapser_pearl", "Causality Collapser Pearl");
        add("block.thaumicavaritia.infinity_charger", "Gushing Charger");
        add("block.thaumicavaritia.cosmic_neutronium_essentia_smeltery", "Cosmic Neutronium Essentia Smeltery");
        add("block.thaumicavaritia.node_anchor", "Node Anchor");

        // Item descriptions (lore)
        add("item.thaumicavaritia.extremely_primordial_pearl.desc", "The origin of the world.");
        add("item.thaumicavaritia.akashic_record.desc", "The weight of knowledge.");
        add("item.thaumicavaritia.infinity_scribing_tools.desc", "A thousand-mile journey begins with a single stroke.");
        add("item.thaumicavaritia.wand_cap_crystal_matrix.desc", "Crystal clear.");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium.desc", "A handheld black hole.");
        add("item.thaumicavaritia.arcane_singularity_focus.desc", "Do not consume.");
        add("item.thaumicavaritia.causality_collapser_pearl.desc", "Remember to wear sunglasses");
        add("item.thaumicavaritia.infinity_charger.desc", "A kindness repaid as a gushing spring.");
        add("item.thaumicavaritia.cosmic_neutronium_essentia_smeltery.desc", "Maximum efficiency.");
        add("item.thaumicavaritia.node_anchor.desc", "You stay right here.");

        add("research_category.thaumicavaritia.thaumicavaritia", "Thaumic Avaritia");

        // Causality Collapser Pearl
        add("research.thaumaturge.causality_collapser_pearl.title", "Causality Collapser Pearl");
        add("research.thaumaturge.causality_collapser_pearl.stage_0", "The endest pearl is certainly a fine thing, yet that black pull sets my mind wandering... Perhaps this very pull could be used to condense aspects?");
        add("research.thaumaturge.causality_collapser_pearl.stage_1", "The more I toy with the endest pearl, the more it strikes me how alike it is to the causality collapser — the collapser drives destructive energy into a rift and forces it to collapse upon itself, while the endest pearl devours all things with a black-hole pull, dragging everything into nothingness. One shatters space, the other swallows the world — plainly two faces of the very same power.<BR>So... what if I were to merge the two? A pearl that can shatter rifts, yawn into a black hole, and even spit what it has devoured back out as aspects? The thought keeps me awake at night.");
        add("research.thaumaturge.causality_collapser_pearl.stage_2", "The infusion was far more perilous than I imagined. With the collapser at its core and eight endest pearls circling it, the flood of vacuousness and magic nearly tore the altar apart. I gritted my teeth through the most dangerous moment, and in the last wisp of chaos, I breathed a sliver of \"infinity\" into it.<BR>It worked. The pearl lies quietly in my palm, pitch black, yet a crimson glow churns deep within. It will explode, shatter rifts, yawn into a black hole that devours all — and when the void fades, it will return everything it consumed to this world as a node of aura. This is not destruction; it is creation in another guise.");

        // Divinely Inspired Pen and Ink
        add("research.thaumaturge.infinity_scribing_tools.title", "Divinely Inspired Pen and Ink");
        add("research.thaumaturge.infinity_scribing_tools.stage_0", "Bards and common folk alike have surely used the \"pen\" — compared to millennia of knotted cords and carved stone tablets, the pen is undoubtedly a great invention. The only flaw is... how quickly the ink runs out. And upon this research table, that flaw is magnified to the extreme — every stroke of study costs the tools a measure of durability. If only the ink could be inexhaustible...");
        add("research.thaumaturge.infinity_scribing_tools.stage_1", "With infinity ingot as the bone and neutronium as the guide, I have let the infusion altar bestow upon this pen a well of ink that never runs dry. From now on, every stroke flows as if guided by divine inspiration — never again will I fret over ink.");

        // Towards Infinity
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

        // Wand branch: Inert Crystal Matrix Cap (mid-tier)
        add("research.thaumaturge.wand_cap_crystal_matrix_inter.title", "Inert Crystal Matrix Cap");
        add("research.thaumaturge.wand_cap_crystal_matrix_inter.stage_0", "With crystal matrix ingot and quicksilver for the frame, a void cap as the mould, and a primordial pearl with salis mundus to set it off — upon the infinity crafting table, an inert crystal matrix cap finally takes shape. Yet it is but an uncharged crystal — Vis pools within it, neither held nor channelled out. To wake it, I must first set its veins in order.");
        // Wand branch: Charged Crystal Matrix Cap
        add("research.thaumaturge.wand_cap_crystal_matrix.title", "Charged Crystal Matrix Cap");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_0", "Salis mundus cleanses the lattice, the primordial pearl pours in the essence, the primal charm anchors the form, and the master focus props up the conduit. Infusion after infusion, the crystal's latent might is coaxed out inch by inch.");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_1", "A frame alone is not enough — I press another layer of primordial pearl to steady the foundation, letting primal charms and master foci alternate in their laying, until the Vis truly flows along the crystal.");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_2", "The infusion passed without a hitch, and the inert crystal matrix is at last fully awakened! As a cap material it is uniquely mighty — it drains only 10% of my Vis!");

        // Wand branch: Studded Cosmic Neutronium Rod
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.title", "Studded Cosmic Neutronium Rod");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_0", "Neutronium is a fine thing — it can contain anything... but what about Vis? I believe I could store Vis within a neutronium vessel. Perhaps I should start by reworking the primal rod core.");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_1", "It works well, yet the Vis drains away in no time. Storing it is no lasting solution... So, what if I add a little infinity into it? Perhaps it could truly generate Vis on its own...");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_2", "I was wrong — not being able to hold the Vis isn't a flaw, it's a blessing! Neutronium encasing infinity looks like nothing but a stick, yet this rod is truly connected to the Vis of the whole universe!<BR>From now on, I will never run dry again!");

        // Arcane Singularity Focus (apex)
        add("research.thaumaturge.arcane_singularity_focus.title", "Arcane Singularity Focus");
        add("research.thaumaturge.arcane_singularity_focus.stage_0", "Now that I command infinite Vis, why should I still be bound by a focus's limits? It's time to forge a core as mighty as a singularity!<BR>Let's start by carving up this ordinary master focus.");
        add("research.thaumaturge.arcane_singularity_focus.stage_1", "It seems this step hardly requires any study at all. With an extremely primordial pearl at its core, supplemented by an enhancement core or a causality collapser — when I say simple, I mean simple in theory, not in materials.");
        add("research.thaumaturge.arcane_singularity_focus.stage_2", "This core — infinite, paradoxical, born of the void and returning to nothing — is the end of all spellcraft.");

        add("wand.thaumaturge.cap.crystal_matrix", "Crystal Matrix");
        add("wand.thaumaturge.rod.studded_cosmic_neutronium", "Studded Cosmic Neutronium");

        // Cosmic Neutronium Essentia Smeltery
        add("research.thaumaturge.cosmic_neutronium_essentia_smeltery.title", "Cosmic Neutronium Essentia Smeltery");
        add("research.thaumaturge.cosmic_neutronium_essentia_smeltery.stage_0", "The void smeltery can indeed refine essentia, yet it still drags along at a maddening pace — the slurry breaks down ever so slowly, and the essentia pollution it bleeds off fouls the whole surroundings besides.<BR>I've had my fill of that inefficiency and filth — to take it apart and recast it is surely the very place to begin my redesign.");
        add("research.thaumaturge.cosmic_neutronium_essentia_smeltery.stage_1", "Tearing it down alone won't suffice. I need a material that can rewrite the smelting logic entirely — the pure neutronium sealed within the full matter cluster, aided by the neutron collector and neutron compressor, is precisely my answer.<BR>Let neutronium be the core, and scrub away both pollution and loss in one stroke. I mean to build a smeltery that wastes not a wisp of essentia, nor exhales a speck of filth.");
        add("research.thaumaturge.cosmic_neutronium_essentia_smeltery.stage_2", "As the last neutron compressor falls into place, a brand-new essentia smeltery takes shape in my hands. It no longer grinds the slurry down at a crawl, nor reeks of essentia pollution — only efficiency, and perfection.<BR>From this day forth, smelting essentia shall be the simplest thing in the world.");

        // Gushing Charger
        add("research.thaumaturge.infinity_charger.title", "Gushing Charger");
        add("research.thaumaturge.infinity_charger.stage_0", "The arcane workbench charger is convenient, sure — but it has a deadly flaw: it drinks the aura of the whole surrounding area bone dry.<BR>With the aura drained away, my wand is all but useless; worse still, the aura it violently churns up leaves behind more flux than aura, and that sort of build-up can kill. I need a way to keep the charging, yet stop it from starving the land or stirring up flux.");
        add("research.thaumaturge.infinity_charger.stage_1", "My thoughts turn to those jarred nodes — sealed in glass, yet they still quietly condense and generate their own vis, never once begging the world for a sip of aura.<BR>If I could carry that self-sufficiency over, let the charger make its own vis, wouldn't it stop draining the aura and stop spawning flux altogether? The studded cosmic neutronium rod connects to the vis of the whole universe — this charger ought to share a similar knack.");
        add("research.thaumaturge.infinity_charger.stage_2", "As the last jarred node sinks into the infusion's swirl, a gushing charger takes shape in my hands. It no longer greedily bleeds the surrounding aura dry, nor leaves behind any dangerous flux — it simply wells up with endless vis of its own.<BR>Set it atop an arcane workbench, and from this day forth, charging comes with no strings attached.");

        add("singularity.thaumicavaritia.brass", "Brass Singularity");
        add("singularity.thaumicavaritia.thaumium", "Thaumium Singularity");
        add("singularity.thaumicavaritia.void", "Void Singularity");

        // Node Anchor
        add("research.thaumaturge.node_anchor.title", "Node Anchor");
        add("research.thaumaturge.node_anchor.stage_0", "The node stabilizer and the node transducer are elegant creations in their own right, yet both share a glaring flaw: they are so terribly slow. The stabilizer can only hold a node in place, frozen and inert; the transducer must be fed charge for a long while before a node finally awakens.<BR>All this waiting is an affront to my greed. There must be a way to combine them — to anchor a node firmly while pouring power into it all at once, no patience required.");
        add("research.thaumaturge.node_anchor.stage_1", "Taking apart the transducer and the stabilizer, I study the mechanism that locks a node's state and the circuit that drives its charge. The frame of one, the circuitry of the other — neither is truly beyond me. The key is a core that can both pin and charge — and neutronium proves the very answer.<BR>Let but a sliver of eternal node be woven into the whole, and the node shall dwell forever in a self-sustaining eternity.");
        add("research.thaumaturge.node_anchor.stage_2", "The transducer's heart, the stabiliser's grip — all the magic bound fast within neutronium.<BR>Set beneath a node, it pins it firm as bedrock. No more waiting, no more waste.");
        add("research.thaumaturge.node_anchor.addendum_0", "A fascinating discovery: the aura within the gushing charger can be channelled straight into a node — the instant the purest aura enters, it bursts into all manner of aspects.<BR>The anchor does more than moor the vessel; it feeds it as well.");

        // 中子态素杖柄合成门禁提示
        add("message.thaumicavaritia.wand_rod_locked", "You are not yet worthy to wield it");

        add("entity.thaumicavaritia.causality_collapser_pearl", "Causality Collapser Pearl");
    }
}
