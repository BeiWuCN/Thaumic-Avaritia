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

        add("wand.thaumaturge.cap.crystal", "水晶矩阵杖端");
        add("wand.thaumaturge.rod.infinity", "缠绕宇宙中子态素");

        add("singularity.thaumicavaritia.brass", "黄铜奇点");
        add("singularity.thaumicavaritia.thaumium", "神秘奇点");
        add("singularity.thaumicavaritia.void", "虚空奇点");
    }
}
