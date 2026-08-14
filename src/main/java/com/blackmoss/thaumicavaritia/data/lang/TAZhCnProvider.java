package com.blackmoss.thaumicavaritia.data.lang;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class TAZhCnProvider extends LanguageProvider {

    public TAZhCnProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.thaumicavaritia", "神秘无尽");

        add("item.thaumicavaritia.extremely_primordial_pearl", "极元始珍珠");
        add("item.thaumicavaritia.akashic_record", "阿卡西记录");
        add("item.thaumicavaritia.wand_cap_crystal", "充能水晶矩阵杖端");
        add("item.thaumicavaritia.wand_cap_crystal_inter", "惰性水晶矩阵杖端");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium", "宇宙中子态素杖柄");
        add("item.thaumicavaritia.arcane_singularity_focus", "奥法奇点核心");

        add("research_category.thaumicavaritia.thaumicavaritia", "神秘无尽");

        // 迈向无限（根节点 = 极元始珍珠，逆熵凝聚 3 段叙事）
        add("research.thaumaturge.unlock_thaumicavaritia.title", "迈向无限");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_0", "元始珍珠，创世之初的残余，蕴藏着纯粹的原始要素。可它们如此珍贵，却大多已退化成结核，甚至尘埃——放任不管，终将归于虚无。我以一颗珍珠为样本，结合神秘学与注魔学的知识，剖析它衰败的本质，寻找逆转这场退化的方法。");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_1", "在神秘学与注魔学的一次次研究中，我终于读懂了它。九颗将死之物已足够作为献祭，我那座注魔祭坛是时候派上用场了——让它们在我手中重生。");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_2", "太美了，这实在是太美妙了……纯粹的原始要素在我手中重生。或许，是我对更强大力量那无法餍足的贪婪，正一点点将我推向疯狂的边缘。");

        // 神秘无尽分类根节点（隐藏，正中间 0,0）
        add("research.thaumaturge.base_thaumicavaritia.title", "神秘无尽");
        add("research.thaumaturge.base_thaumicavaritia.stage_0", "我觉得……我的时间被浪费了，它们明明可以用在更有意义的事情，比起一次次地重复劳动，我更想……获得一些……更强的力量……或者说……无尽的力量……");

        // 阿卡西记录（桥接枢纽）
        add("research.thaumaturge.akashic_record.title", "阿卡西记录");
        add("research.thaumaturge.akashic_record.stage_0", "以极致的贪婪之物为引，我终于唤醒了这本全知之书。它封存着一切神秘知识，如今为我所用。");
        add("research.thaumaturge.akashic_record.stage_1", "贪欲的造物激活了阿卡西记录，万千要素如潮水般涌入我的心智——神秘学，自此成为我掌中之物。");

        // 法杖工业支线
        add("research.thaumaturge.ta_wand_cap_crystal.title", "充能水晶矩阵杖端");
        add("research.thaumaturge.ta_wand_cap_crystal.stage_0", "以水晶矩阵缠绕杖身，Vis 的消耗将被无限压低。贪婪的工业，亦能侍奉奥术。");

        add("research.thaumaturge.ta_wand_cap_crystal_inter.title", "惰性水晶矩阵杖端");
        add("research.thaumaturge.ta_wand_cap_crystal_inter.stage_0", "剥离了光环的水晶杖端，不再喧哗，却依旧承载着那份被无限压缩的奥术效率。");

        add("research.thaumaturge.ta_wand_rod_neutronium.title", "宇宙中子态素杖柄");
        add("research.thaumaturge.ta_wand_rod_neutronium.stage_0", "中子态素的容量近乎无穷，且能自行回满。这柄杖，从此再无枯竭之虞。");

        // 奥法奇点核心（树顶汇合）
        add("research.thaumaturge.ta_arcane_singularity_focus.title", "奥法奇点核心");
        add("research.thaumaturge.ta_arcane_singularity_focus.stage_0", "当水晶杖端与中子杖柄合而为一，奥法的奇点便不再受复杂度的桎梏——无限，本就该如此。");
        add("research.thaumaturge.ta_arcane_singularity_focus.stage_1", "我以贪婪铸成了这枚核心，它吞噬一切法术的边界。神秘学在我手中，终于抵达了无限。");

        add("wand.thaumaturge.cap.crystal", "水晶矩阵杖端");
        add("wand.thaumaturge.rod.infinity", "缠绕宇宙中子态素");

        add("singularity.thaumicavaritia.brass", "黄铜奇点");
        add("singularity.thaumicavaritia.thaumium", "神秘奇点");
        add("singularity.thaumicavaritia.void", "虚空奇点");
    }
}
