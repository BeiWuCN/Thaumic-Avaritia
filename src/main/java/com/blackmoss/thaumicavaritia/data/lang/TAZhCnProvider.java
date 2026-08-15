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
        add("item.thaumicavaritia.causality_collapser_pearl", "悖论珍珠");
        add("block.thaumicavaritia.infinity_charger", "涌流充能器");
        add("block.thaumicavaritia.cosmic_neutronium_essentia_smeltery", "宇宙中子态素源质冶炼厂");

        // 物品描述
        add("item.thaumicavaritia.extremely_primordial_pearl.desc", "世界的本源");
        add("item.thaumicavaritia.akashic_record.desc", "知识的重量");
        add("item.thaumicavaritia.infinity_scribing_tools.desc", "先行万里路");
        add("item.thaumicavaritia.wand_cap_crystal_matrix.desc", "晶莹剔透");
        add("item.thaumicavaritia.wand_rod_studded_cosmic_neutronium.desc", "手持式黑洞");
        add("item.thaumicavaritia.arcane_singularity_focus.desc", "不宜口服");
        add("item.thaumicavaritia.causality_collapser_pearl.desc", "记得带墨镜");
        add("item.thaumicavaritia.infinity_charger.desc", "滴水之恩");
        add("item.thaumicavaritia.cosmic_neutronium_essentia_smeltery.desc", "效率最大化");

        add("research_category.thaumicavaritia.thaumicavaritia", "神秘无尽");

        // 迈向无限
        add("research.thaumaturge.unlock_thaumicavaritia.title", "迈向无限");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_0", "元始珍珠，创世之初的残余，蕴藏着纯粹的原始要素。可它们如此珍贵，却大多已退化成结核，甚至尘埃——放任不管，终将归于虚无。<BR>我以一颗残存的珍珠为样本，反复比对神秘学与注魔学的典籍，剖析它衰败的本质。这并非寻常的腐朽，而是一种缓慢的、彻底的自我消解。若放任不管，连最后的微尘也会归于虚无。我必须找到逆转这场退化的方法。");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_1", "在神秘学与注魔学的一次次研究中，我终于读懂了它——所谓退化，不过是原始之力在漫长时光中渐渐熄灭。既然如此，就让注魔祭坛的烈焰重新点燃它们！<BR>九颗将死之物已足够作为献祭，我珍贵的样本也将化作祭坛上的引子。让它们在我手中重生，凝出那从未存在过的、最纯粹的原始要素。");
        add("research.thaumaturge.unlock_thaumicavaritia.stage_2", "太美了……这实在是太美妙了。纯粹的原始要素在我手中重生，仿佛一颗崭新的恒星。它比世上任何宝石都要璀璨，也比任何造物都更接近世界的本源。<BR>可为何我仍不满足？或许，是我对更强大力量那无法餍足的贪婪，正一点点将我推向疯狂的边缘……");

        // 神秘无尽分类根节点
        add("research.thaumaturge.base_thaumicavaritia.title", "神秘无尽");
        add("research.thaumaturge.base_thaumicavaritia.stage_0", "我觉得……我的时间被浪费了。它们明明可以用在更有意义的事情上——比起一次次地重复劳动，我更想……获得一些……更强的力量……<BR>贪婪永远得不到满足。也许总有一天，我会渴望一样事物，它没有尽头，没有边界，无穷无尽……");

        // 阿卡西记录
        add("research.thaumaturge.akashic_record.title", "阿卡西记录");
        add("research.thaumaturge.akashic_record.stage_0", "以极致的贪婪之物为引，我终于唤醒了这本全知之书。它封存着古往今来一切神秘知识——万物的本质、魔法的真理，尽在其中。<BR>从今往后，这本全知之书为我所用。知识的围墙，再也挡不住我。");
        add("research.thaumaturge.akashic_record.stage_1", "贪欲的造物激活了阿卡西记录，万千要素如潮水般涌入我的心智。我几乎能感受到知识的重量，几乎要为此发狂。<BR>神秘学，自此成为我掌中之物。可这还不够……我的贪婪，才刚刚苏醒。");

        // 下笔如有神之笔与墨
        add("research.thaumaturge.infinity_scribing_tools.title", "下笔如有神之笔与墨");
        add("research.thaumaturge.infinity_scribing_tools.stage_0", "无论是吟游诗人还是穷苦百姓，想必都用过“笔”这种事物。相比千百年以来的结绳记事与石板雕刻，笔无疑是一件伟大的发明。只是……墨水消耗得太快了，这几乎是笔唯一的缺点。<BR>而在这研究台上，这个缺点被放大到了极致——每一次落笔研习，都要耗去笔具一分耐久。若是墨水也能取之不尽，该有多好……");
        add("research.thaumaturge.infinity_scribing_tools.stage_1", "我以无限锭为骨、中子态素为引，让注魔祭坛赐予这支笔用之不竭的墨水。<BR>从今往后，落笔如有神助，墨痕永续。研究台上，再也不用为墨水发愁了。");

        // 法杖
        add("research.thaumaturge.wand_cap_crystal_matrix.title", "水晶矩阵杖端");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_0", "钻石、下界合金、下界之星——将三者融炼而成的晶体，或许能承载足够强大的魔力。不过在此之前，我需要仔细规划一下……");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_1", "事实证明，水晶在压制魔力消耗上表现优异，却并不擅长导出魔力。这倒是让我想起虚空杖端——虚空金属最擅长的就是传导。<BR>也许该加些世界盐之类的助剂试试？又或许，只是水晶矩阵的潜力还没被激发出来……");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_2", "测试成功了！真如我所料，水晶矩阵需要经过充能才能发挥全部威力。那我只要找些材料加上去就行——先加几枚元始魔力保持稳定，要不……再添几颗元始珍珠？");
        add("research.thaumaturge.wand_cap_crystal_matrix.stage_3", "注魔有惊无险，水晶矩阵潜藏的威力终于被激发出来了！作为杖端，它强得独一无二——只会消耗 10% 的魔力！");

        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.title", "宇宙中子态素杖柄");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_0", "中子态素可是好东西，它能包容万象……那魔力呢？我似乎可以把魔力储存进中子态素的容器里。或许，该从改造元始杖芯开始。");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_1", "效果很好，可魔力没过一会就全跑了。储存并非长久之计……那，我加点无限进去试试？也许它真能自己产生魔力呢……");
        add("research.thaumaturge.wand_rod_studded_cosmic_neutronium.stage_2", "我搞错了——存不下魔力不是坏事，而是天大的好事！中子态素裹住无限，看起来不过是一根棍子，可这根杖柄实际上连通着全宇宙的魔力！<BR>这下子，我永远也不会缺魔力了！");

        // 奥法奇点核心
        add("research.thaumaturge.arcane_singularity_focus.title", "奥法奇点核心");
        add("research.thaumaturge.arcane_singularity_focus.stage_0", "现在我拥有了无限的魔力，为何还要受核心的限制？是时候锻造一颗奇点般强大的核心了！<BR>先从这个普通的大师核心开刀吧。");
        add("research.thaumaturge.arcane_singularity_focus.stage_1", "看起来这一步甚至不需要费劲研究。给它覆上中子态素作护壳，内部以无限为内核——我说的简单，不是材料上的简单，而是理论上的简单。");
        add("research.thaumaturge.arcane_singularity_focus.stage_2", "这颗核心，无限而悖论，生于虚空，归于虚无——它便是所有法术的终点。");

        // 悖论珍珠
        add("research.thaumaturge.causality_collapser_pearl.title", "悖论珍珠");
        add("research.thaumaturge.causality_collapser_pearl.stage_0", "终望珍珠无疑是个好东西，不过那股黑色的吸力让我浮想联翩……似乎可以利用这股吸力来聚合要素？");
        add("research.thaumaturge.causality_collapser_pearl.stage_1", "我越是把玩终望珍珠，就越是觉得它与悖论物质神似——悖论物质将破坏性的能量送入裂隙，迫其自我坍缩；终望珍珠则以黑洞般的吸力吞噬万物，将一切拖入虚无。一个崩碎空间，一个吞没世界，分明是同一种力量的两副面孔。<BR>那么……若是将二者合一，会怎样？一颗既能崩碎裂隙、又能张开黑洞、甚至能将吞噬之物化作要素吐出的珍珠？这个念头，让我夜不能寐。");
        add("research.thaumaturge.causality_collapser_pearl.stage_2", "注魔远比想象中凶险。以悖论物质为核，八颗终望珍珠环绕四周，虚空与魔法的洪流几乎将祭坛撕碎。我咬牙撑过最危险的时刻，在最后一缕混沌中，将一丝“无限”点入其中。<BR>成功了。悖论珍珠静静躺在我掌心，通体漆黑，深处却流转着赤红的光。它会爆炸、会崩碎裂隙、会张开黑洞吞噬一切——而当黑洞消散，它还会把吞噬过的一切化作灵气节点，归还这个世界。这分明不是毁灭，而是另一种意义上的创世。");

        add("wand.thaumaturge.cap.crystal_matrix", "水晶矩阵杖端");
        add("wand.thaumaturge.rod.studded_cosmic_neutronium", "缠绕宇宙中子态素");

        add("singularity.thaumicavaritia.brass", "黄铜奇点");
        add("singularity.thaumicavaritia.thaumium", "神秘奇点");
        add("singularity.thaumicavaritia.void", "虚空奇点");
    }
}
