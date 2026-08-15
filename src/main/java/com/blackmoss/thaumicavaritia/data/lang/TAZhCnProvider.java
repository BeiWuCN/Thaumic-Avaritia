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

        add("aspect.thaumicavaritia.infinitus", "无限");
        add("aspect.thaumicavaritia.infinitus.desc", "无限，无穷，无边无际");
        add("aspect.thaumicavaritia.infinitus.help", "永无止境");

        add("item.thaumicavaritia.extremely_primordial_pearl", "极元始珍珠");
        add("item.thaumicavaritia.akashic_record", "阿卡西记录");
        add("item.thaumicavaritia.wand_cap_crystal_matrix", "充能水晶矩阵杖端");
        add("item.thaumicavaritia.wand_cap_crystal_matrix_inter", "惰性水晶矩阵杖端");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium", "宇宙中子态素杖柄");
        add("item.thaumicavaritia.arcane_singularity_focus", "奥法奇点核心");
        add("item.thaumicavaritia.infinity_scribing_tools", "下笔如有神之笔与墨");

        add("research_category.thaumicavaritia.thaumicavaritia", "神秘无尽");

        // 迈向无限
        add("research.thaumaturge.unlock_thaumicavaritia.title", "迈向无限");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_0", "元始珍珠，创世之初的残余，蕴藏着纯粹的原始要素。可它们如此珍贵，却大多已退化成结核，甚至尘埃——放任不管，终将归于虚无。我以一颗珍珠为样本，结合神秘学与注魔学的知识，剖析它衰败的本质，寻找逆转这场退化的方法。");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_1", "在神秘学与注魔学的一次次研究中，我终于读懂了它。九颗将死之物已足够作为献祭，我那座注魔祭坛是时候派上用场了——让它们在我手中重生。");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_2", "太美了，这实在是太美妙了……纯粹的原始要素在我手中重生。或许，是我对更强大力量那无法餍足的贪婪，正一点点将我推向疯狂的边缘。");

        // 神秘无尽分类根节点
        add("research.thaumaturge.base_thaumicavaritia.title", "神秘无尽");
        add("research.thaumaturge.base_thaumicavaritia.stage_0", "我觉得……我的时间被浪费了，它们明明可以用在更有意义的事情，比起一次次地重复劳动，我更想……获得一些……更强的力量……或者说……无尽的力量……");

        // 阿卡西记录
        add("research.thaumaturge.akashic_record.title", "阿卡西记录");
        add("research.thaumaturge.akashic_record.stage_0", "以极致的贪婪之物为引，我终于唤醒了这本全知之书。它封存着一切神秘知识，如今为我所用。");
        add("research.thaumaturge.akashic_record.stage_1", "贪欲的造物激活了阿卡西记录，万千要素如潮水般涌入我的心智——神秘学，自此成为我掌中之物。");

        // 法杖
        add("research.thaumaturge.wand_cap_crystal_matrix.title", "水晶矩阵杖端");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_0", "我认为相信钻石、下界合金、下界之星混合而成的水晶能承载足够强大的魔力，不过在此之前我需要规划一下……");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_1", "事实证明水晶很适合用于减少魔力的消耗，但并不能很好地导出魔力，这倒是让我想到虚空杖端了，虚空金属很擅长传导魔力，也许可以加些世界盐什么的试试，当然也可能是水晶矩阵的潜力没被激发出来……");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_2", "测试成功了！，真如前言所叙，水晶矩阵需要经过充能才能发挥其全部威力，那我找些材料加上去就行了，元始魔力先加几个保持稳定，要不……加点元始珍珠？");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_3", "注魔有惊无险，水晶矩阵潜藏的威力被激发出来了！这种材料作为杖端是独一份的强力！其只会消耗10%的魔力！");

        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.title", "宇宙中子态素杖柄");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_0", "中子态素可是好东西，它能包容万象……那魔力呢？我似乎可以把魔力存储在中子态素的容器中，或许我可以从改造元始杖芯开始。");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_1", "效果很好，但魔力没过一会就全跑了，我再加点无限进去试试？也许能自己产生魔力呢……");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_2", "我搞错了，存不下不是坏事，而是好事！中子态素包含住无限的结构看起来只是一根棍子，但是这根中子态素杖柄实际上连接着全宇宙的魔力！这下子永远也不缺魔力了！");

        // 奥法奇点核心
        add("research.thaumaturge.arcane_singularity_focus.title", "奥法奇点核心");
        add("research.thaumaturge.arcane_singularity_focus.stage_0", "现在我拥有了无限的魔力，为何还要受到核心的限制？我认为是时候来做一个奇点般强大的核心了！先从这个普通的大师核心开刀吧。");
        add("research.thaumaturge.arcane_singularity_focus.stage_1", "看起来我不需要费劲研究什么，这甚至是最简单的一步，给它上点中子态素保护内部用无限做内核，我指的不是材料上简单而是理论上简单。");
        add("research.thaumaturge.arcane_singularity_focus.stage_2", "这颗无限的、悖论的、虚空的核心，就是所有法术的终点。");

        add("wand.thaumaturge.cap.crystal_matrix", "水晶矩阵杖端");
        add("wand.thaumaturge.rod.studded_cosmic_neutronium", "缠绕宇宙中子态素");

        add("singularity.thaumicavaritia.brass", "黄铜奇点");
        add("singularity.thaumicavaritia.thaumium", "神秘奇点");
        add("singularity.thaumicavaritia.void", "虚空奇点");
    }
}
