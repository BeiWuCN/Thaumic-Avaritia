# CODEBUDDY.md This file provides guidance to CodeBuddy when working with code in this repository.

## 项目概述

**Thaumic Avaritia**（`mod_id = thaumicavaritia`，包名 `com.blackmoss.thaumicavaritia`）是将**无尽贪婪：重生（Re:Avaritia，mod_id = avaritia）**与**Thaumaturge（mod_id = thaumaturge）**联动的 NeoForge addon 模组。目标平台：MC 26.1.2 / NeoForge 26.1.2.95，Java 工具链固定 25（`build.gradle` 中 `java.toolchain.languageVersion = 25`）。

历史背景：1.7.10 时代的无尽贪婪中有许多 TC4 联动内容，基于 26.1.2 的新内容可扩展性很大。

参考项目源码（只读参考，禁止直接复制代码，需逐一对标 NeoForge 26.1.2 的等价 API）：
- Thaumaturge 本体：`E:\MossProject\MinecraftJava\Thaumaturge`（API 权威参考）
- ThaumaturgeTweaks：`E:\MossProject\MinecraftJava\ThaumaturgeTweaks`（本项目的代码来源，很多实现可直接参考其写法）

## 构建与运行命令

- `.\gradlew runClient` — 启动客户端 dev sandbox（同时加载 Thaumaturge 与 Avaritia）。Windows 下统一用 `.\gradlew`（即 gradlew.bat）。
- `.\gradlew runServer` — 启动无头服务端（`--nogui`）。
- `.\gradlew build` — 产出可分发的 jar 到 `build/libs/`。
- `.\gradlew runData` — 重新生成数据包到 `src/generated/resources/`。**注意：runData 会先清空该目录**，不要手动删除 generated 文件；要移除某个生成物只需删掉对应 DataGen 条目。
- `.\gradlew --stop` — 杀掉 Gradle daemon（run 卡住时用）。
- 无测试代码：`gameTestServer` 运行配置已在 `build.gradle` 声明，但未注册任何 gametest。
- 依赖源码/文档由 `idea` 插件自动下载；`neoForge.ideSyncTask generateModMetadata` 保证 IDE 内元数据最新。

## 架构总览

### 代码组织

12 个 Java 文件分三块，包结构即分层：
- `registry/` — 注册入口：`TAItems`（物品）、`TAWandParts`（法杖部件）、`TATags`（标签常量）。
- `content/item/` — 物品行为：目前仅 `AkashicRecordsItem`（其余物品无自定义行为，直接用注册工厂）。
- `data/` — DataGen 体系：`TADataGenerators` 为唯一入口，下辖 `lang/`、`recipe/`、`model/`、`tag/`、`datamap/`。

### 主入口与注册流程

`ThaumicAvaritia`（`@Mod`）构造函数按顺序注册：
1. `TAWandParts.register(modEventBus)` — 两个 `DeferredRegister`（`WandCap.REGISTRY_KEY` / `WandRod.REGISTRY_KEY`）把杖端/杖柄写进 **Thaumaturge 的动态注册表**，不是物品注册表。
2. `TAItems.register(modEventBus)` — 标准 `DeferredRegister.Items` 注册 4 个物品。其中杖端/杖柄物品用 Thaumaturge 的 `ItemWandCap` / `ItemWandRod` 类，构造时绑定上面的 `WandCap` / `WandRod` 部件。

理解这条双注册链是关键：**部件（能力）在 Thaumaturge 注册表，物品（载体）在本 mod 注册表**，二者通过构造函数参数绑定。

### DataGen 体系

`TADataGenerators.onGatherData`（`GatherDataEvent.Client`）做两件事：
1. 构造 `RegistrySetBuilder` 并注入 `IAspect.REGISTRY_KEY`（用 `AspectBootstrap::bootstrap`），然后 `createDatapackRegistryObjects(registries)`。**这步不可省**——recipe/datamap provider 里都调用 `registries.lookupOrThrow(IAspect.REGISTRY_KEY)`，不注入会直接崩 `Registry thaumaturge:aspect not found`。
2. 依次注册 provider：`TAEnUsProvider` / `TAZhCnProvider` / `TARecipeProvider.Runner` / `TAModelProvider` / `TATagsProvider`。

配方走 `InfusionRecipeBuilder`（Thaumaturge 的 builder），语言用 `LanguageProvider`，模型用 `ModelProvider` 的 `generateFlatItem`，标签用匿名内部 `IntrinsicHolderTagsProvider` 包装成独立 `DataProvider`。

### 跨模组联动方式

- **注魔配方**：同时引用 `TCItems`（Thaumaturge 物品）与 `ModItems`（avaritia 物品）作为材料/催化剂。
- **标签**：把本 mod 物品加进 avaritia 的 `ModTags.IMMORTAL_ITEM`（`#avaritia:endless`）；把 Thaumaturge 的 7 个珍宝加进本 mod 声明的 `TATags.THAUMATURGE_CURIOS`（`#thaumaturge:curios`）。
- **要素 datamap**：`ItemAspectsProvider` 用 `AspectDataMaps.BASE_ASPECTS` 为 avaritia 物品补要素构成（当前未注册，见"当前状态"）。
- **研究点**：`AkashicRecordsItem` 遍历 `IAspect.REGISTRY_KEY` 全部要素，`AspectPoolAccess.grantAll` 一次性授予。
- **法杖 Vis**：`WandAccess.getAllVis` 返回副本，`WandAccess` 的副本不能直接写回，必须 `wand.set(TCDataComponents.WAND_VIS.get(), full)`。

### 依赖与 API 边界

`build.gradle` 两个关键依赖（**不要移除**）：
1. Thaumaturge（flatDir 指向 `E:/MossProject/MinecraftJava/Thaumaturge/build/libs`，`compileOnly` + `localRuntime`）——本项目唯一直接依赖的 Thaumaturge API 来源，编译靠该 jar。
2. Avaritia（Modrinth Maven，`compileOnly` + `localRuntime`）——`committee.nova.mods.avaritia` 包，入口 `init.registry.ModItems` / `ModTags` / `ModApi`。

`src/main/templates/META-INF/neoforge.mods.toml` 是元数据模板（唯一权威来源，别改生成物）。依赖声明：`neoforge`（required）、`minecraft`（required）、`thaumaturge`（required，ordering AFTER）、`avaritia`（required，ordering AFTER）。

**API 边界**：依赖 Thaumaturge 时只读 `com.leclowndu93150.thaumaturge.api.*` facade；可例外使用注册类本身（`ItemWandCap` / `ItemWandRod` / `TCItems` / `TCDataComponents`）与 `data.recipe.builders` / `data.worldgen.aspect`，不要碰 `content`/`registry` 内部实现。

## 已实现内容（迁移自 ThaumaturgeTweaks）

### 物品（`registry/TAItems.java`）
| 注册名                               | 类                             | 说明                                  |
|--------------------------------------|--------------------------------|---------------------------------------|
| `extremely_primordial_pearl`         | `ExtremelyPrimordialPearlItem` | 极元始珍珠，注魔材料（EPIC）          |
| `akashic_record`                     | `AkashicRecordsItem`           | 阿卡西记录，使用后授予全部要素研究点  |
| `wand_cap_crystal`                   | `ItemWandCap`                  | 水晶矩阵杖端，90% Vis 减免            |
| `wand_cap_crystal_inter`             | `Item`                         | 惰性水晶矩阵杖端（无 halo，普通扁平模型）|
| `wand_rod_studded_cosmic_neutronium` | `ItemWandRod`                  | 宇宙中子态素杖柄，超高容量 + 自动回满 |
| `arcane_singularity_focus`           | `ArcaneSingularityFocusItem`   | 奥法奇点核心，复杂度上限 `Integer.MAX_VALUE`（无限）|

### 法术核心（`content/item/ArcaneSingularityFocusItem.java`）
- 继承 Thaumaturge 的 `ItemFocus`，构造传 `Integer.MAX_VALUE` 实现无限复杂度（原版 FOCUS_1/2/3 为 15/25/50）。
- 模型与原版核心一致：`FLAT_ITEM` + `FocusColorTint`（`thaumaturge:focus_color`，颜色随核心内法术元素）。当前 layer0 临时引用 `thaumaturge:item/focus_3`，若用户自绘 `textures/item/arcane_singularity_focus.png` 后改回 `TextureMapping.layer0(item)`。

### Mixin 基础设施（`mixin/` + `thaumicavaritia.mixins.json`）
- `neoforge.mods.toml` 模板已启用 `[[mixins]] config="${mod_id}.mixins.json"`；NeoForge 26.x 用官方映射，mixin **无需 refmap**。
- **`BlockEntityFocalManipulatorMixin`**：`@Redirect` `startCraft` 中的 `ItemFocus.getMaxComplexity()` 调用，奥法奇点核心返回 0 → 组装 Vis 公式 `totalComplexity * 10 + maxComplexity / 5` 的上限基础成本归零（仅剩内容复杂度成本），普通核心不受影响。
- 注意：`BlockEntityFocalManipulator` 是 `final class`，mixin 改字节码不受影响。`getMaxComplexity()` 同时被 UI 复用（`FocalManipulatorScreen` 显示 `totalComplexity/maxComplexity` + 校验 `totalComplexity <= maxComplexity`），所以不能靠覆写它归零——UI 校验会把任何组装判为非法；mixin 只作用于 `startCraft`，不影响 UI 的无限上限显示与永真校验。

### 法杖部件（`registry/TAWandParts.java`）
- 注册到 Thaumaturge 的法杖注册表（`WandCap.REGISTRY_KEY` / `WandRod.REGISTRY_KEY`），不是物品注册表。
- `CRYSTAL_MATRIX`：`baseCostModifier = 0.1F`（90% 减免）。
- `STUDDED_COSMIC_NEUTRONIUM`：`capacity = 9_999_999`（vis，Thaumaturge 内部以 centivis 存储，写入时乘 100），`onUpdate = InfinityOnUpdate` 每 tick 把全部原初要素补满（写入 `TCDataComponents.WAND_VIS`）。
- 纹理路径：部件渲染用 `assets/thaumicavaritia/textures/models/wand_cap_crystal.png`、`wand_rod_studded_cosmic_neutronium.png`；物品形态另有 `textures/item/` 下同名 PNG（**纹理由用户手绘，AI 不要生成图片**）。注意迁移时已修正：旧代码曾引用 `wand_cap_crystal_matrix.png` / `wand_rod_infinity.png`，与用户实际文件不符，已对齐。

### 物品模型（`data/model/TAModelProvider.java`）
- 全部 4 个物品使用 avaritia 的**无尽背景光圈（halo）**：内层模型（`createFlatItemModel`，自动引用 `item/<注册名>.png`）+ `AvaritiaItemModelLoaders.Halo` 物品模型（`pulse=false, size=6, texture=avaritia:misc/halo`）。
- 生成 `items/<注册名>.json` 与 `models/item/<注册名>.json`；**不要**在 `src/main/resources/assets/.../items/` 下手写 items json——DataGen 已生成，手写会导致 `processResources` 重复文件报错。
- 若物品模型由其它来源提供（绕过 DataGen 校验），用 `itemModels.declareCustomModelItem(item)`（仍会生成默认 items json，需注意与手动文件冲突）。

### 配方（`data/recipe/TARecipeProvider.java`）
- 极元始珍珠：8 × `TCItems.PRIMORDIAL_PEARL` 注魔（Alienis×32 / Perditio×16，instability 4）。
- 阿卡西记录：催化剂 `#thaumaturge:curios` + 4 × `ModItems.infinity_ingot` 注魔（Cognitio×64 / Praecantatio×64 / Alienis×32）。
- 全部使用 `InfusionRecipeBuilder`，已带 `.unlockedBy(...)`。

### 研究类别（`data/worldgen/research/TACategoryBootstrap.java`）
- "神秘无尽"（`thaumicavaritia:arcane_infinity`）类别：`IResearchCategory.REGISTRY_KEY` datapack registry，用 `BootstrapContext` 注册（与 aspect 同机制），DataGen 生成到 `data/<ns>/thaumaturge/research_category/`。
- 门控 `required_research = thaumicavaritia:unlock_thaumicavaritia`（**该研究条目尚未创建**，创建后类别才在魔导手册可见）。
- `icon` = `thaumicavaritia:textures/item/akashic_record.png`（阿卡西记录图标），`background` = `avaritia:textures/block/diamond_lattice_block.png`，`overlay_background` = Thaumaturge 的 `gui_research_back_over.png`（跨命名空间直接引用纹理 Identifier，运行时 Thaumaturge/avaritia 必在）。`index=7` 排在 Thaumaturge 原版 6 类之后。
- `formula` 决定扫描要素转知识点的权重（applyFormula 用平方根）。lang 键 `research_category.thaumicavaritia.arcane_infinity`。
- 注意：研究条目本身是**手写 JSON**（`data/<ns>/thaumaturge/research_entry/`），不经过 DataGen；只有类别经 bootstrap+runData 生成。

### 无尽工作台配方（`data/recipe/TARecipeProvider.java`）
- 水晶杖端：`avaritia:shaped_table`（9x9，`tier=4` 无尽工作台），当前为铁锭占位 pattern（9×9 边框），正式材料待替换。
- 写法参考：构造 `ShapedRecipePattern.of(Map<Character,Ingredient>, String...)` + `new ShapedTableCraftingRecipe(pattern, new ItemStackTemplate(...), tier, compatible)`，再 `output.accept(ResourceKey.create(Registries.RECIPE, ...), recipe, null)`（advancement 传 null）。
- `tier` 决定所需工作台等级（4=无尽工作台；0=不限制）；`compatible` 不参与合成匹配，传 `false` 使 JSON 与原版一致（无该字段）。
- avaritia 的 `AvaritiaRecipeProvider` 全是 private 方法，addon 无法复用其 helper，只能参考 JSON 格式；`ShapedTableCraftingRecipe` 是唯一可构造的公开配方类。

### 无尽锻造配方（`data/recipe/TARecipeProvider.java`）
- 奥法奇点核心：`avaritia:extreme_smithing`。template=`avaritia:upgrade_smithing_template`，base=`thaumaturge:focus_3`，addition=`CompoundIngredient`（极元始珍珠 / `avaritia:enhancement_core` / `thaumaturge:causality_collapser` 三选一）。
- **必须用 `new ItemStackTemplate(item)` 构造 result**，不能用 `new ItemStack(item)`——后者在 datagen 环境触发物品默认组件解析，抛 `NullPointerException: Components not bound yet`（wand 配方的 `Ingredient.of` 与 `ItemStackTemplate` 均安全，仅 `new ItemStack` 不行）。

### 无尽催化剂配方覆盖（`src/main/resources/data/avaritia/recipe/`）
- 用**数据包覆盖**（同名文件）把 `extremely_primordial_pearl` 追加进 avaritia 原版的两个 `type=avaritia:infinity_catalyst` 配方：`infinity_catalyst.json`（default 组）与 `infinity_catalyst_eternal.json`（`group=eternal_singularity`）。
- 覆盖文件必须放在 **`data/avaritia/`** 命名空间（本 mod `ordering=AFTER avaritia`，数据包后者覆盖前者）；放在 `data/thaumicavaritia/` 只会**新增**独立配方而非修改原版。
- 注意 `InfinityCatalystCraftRecipe` 的 default 组是动态配方：运行时会把所有已注册 singularity 物品追加进 ingredients，且匹配要求输入数量 = singularityCount + originalInputs.size()。珍珠不是 singularity，不会进入动态部分，只作为固定材料。

### 奇点（`src/main/resources/data/thaumicavaritia/singularities/` + `recipe/`）
- 注册 3 个 Thaumaturge 金属奇点：`brass`（`thaumaturge:ingot_brass`）、`thaumium`（`thaumaturge:ingot_thaumium`）、`void`（`thaumaturge:ingot_void`）。
- singularity 由**数据文件**定义（`data/<ns>/singularities/<name>.json`，字段 `name`/`ingredient`/`displayName`/`overlayColor`/`underlayColor`），由 avaritia 的 `SingularityReloadListener` 加载；文件路径按 `name` 字段命名空间输出。必须用自己的命名空间 `thaumicavaritia:`（否则会覆盖/污染 avaritia 命名空间）。
- 合成配方：`data/thaumicavaritia/recipe/<name>_singularity.json`，`type=avaritia:compressor`，`result` 带 `avaritia:singularity_id` component，`timeCost=240`（参照原版 iron_singularity 格式）。
- 注册后奇点自动进入 default 组无尽催化剂配方的**动态材料**部分（`InfinityCatalystCraftRecipe.getIngredients` 会把所有 `hasIngredient` 的 singularity 追加进去）。
- 翻译键：`singularity.thaumicavaritia.<name>`（DataGen lang）。

### 标签（`registry/TATags.java` + `data/tag/TATagsProvider.java`）
- `#avaritia:endless`（`ModTags.IMMORTAL_ITEM`）：珍珠、阿卡西、杖端、杖柄全部加入。
- `#thaumaturge:curios`（`TATags.THAUMATURGE_CURIOS`）：Thaumaturge 的 7 个珍宝加入，供阿卡西配方取用。
- 标签不存在的就直接加，不要反复验证。

### 要素 datamap（`data/datamap/ItemAspectsProvider.java`）
- 为 avaritia 的 `infinity_ingot`、`neutron_ingot`、`endest_pearl`、`infinity_catalyst`、`crystal_matrix_ingot`、`neutron_pile`、`infinity_nugget` 补充 `BASE_ASPECTS` 要素构成，供扫描/研究系统使用。
- **当前在 `TADataGenerators` 中被注释**（见"当前状态"）。

### Avaritia shader 修复（`src/main/resources/assets/avaritia/shaders/core/black_hole.fsh`）
- avaritia 原版 `black_hole.fsh` 含中文注释导致 GLSL 编译崩溃，本项目通过资源覆盖提供修复版：删除中文注释 + 辅助函数改名（`hash3`→`blackHoleHash3`、`noise3`→`blackHoleNoise3`），其余逻辑与原版一致。
- 仅需覆盖 fsh：`black_hole.json` 按 `avaritia:black_hole` 引用同目录 vsh/fsh，vsh 与 json 均无中文，无需覆盖。
- **该文件是跨命名空间资源覆盖（`assets/avaritia/...`），必须保留，不要删。**

### DataGen（`data/TADataGenerators.java`）
- 注册了：`TAEnUsProvider` / `TAZhCnProvider` / `TARecipeProvider.Runner` / `TAModelProvider` / `TATagsProvider`（`ItemAspectsProvider` 当前注释）。
- 注入 `AspectBootstrap` 到 `RegistrySetBuilder`（解决 `Registry thaumaturge:aspect not found`）。
- lang 已迁移为 DataGen（`item.thaumicavaritia.*`），`assets/.../lang/` 不再手写。

## 关键 API 备忘

### Thaumaturge
- 研究点授予：`AspectPoolAccess.grantAll(player, AspectList)`；遍历注册表用 `IAspect.REGISTRY_KEY` lookup。
- 法杖 Vis：`WandAccess.getAllVis(stack)` 返回 `WandVis`，`WandVis.with(aspect, amount)`；写入用 `stack.set(TCDataComponents.WAND_VIS.get(), vis)`（注意 `WandAccess` 的副本不能直接写回）。
- 要素：`TCAspects.PRIMALS`、`TCAspects.ALIENIS` 等为 `ResourceKey<IAspect>`。
- 注魔配方：`InfusionRecipeBuilder`（见 `data/recipe` 用法）。

### Avaritia
- 物品：`ModItems.infinity_ingot.get()`、`ModItems.endest_pearl.get()` 等。
- 标签：`ModTags.IMMORTAL_ITEM`（即 `#avaritia:endless`）。

## 约定与注意事项

- **纹理由用户手绘**，AI 一律不生成图片文件。
- 命名统一："Akashic Records"（`akashic_record`），不要出现其它变体。
- JSON 一律 snake_case；模型通过 DataGen 的 `generateFlatItem` 生成，自动引用 `item/<注册名>.png`。
- 涉及图像渲染（blit 等）时所有放大必须**等比**，不能拉伸或裁剪（目标宽高比必须等于源宽高比，且使用完整源图）。
- 所有公共 API 参数做空值校验（`ArgumentNullException.ThrowIfNull` 风格），返回集合用空集合而非 null；不确定非空的变量必须显式检查（最高优先级）。
- 注释一律使用 `//` 双斜杠，不用 `/* */` 或 Javadoc。
- 依赖 Thaumaturge 时只读 `com.leclowndu93150.thaumaturge.api.*` facade，不要碰 `content`/`registry` 内部（注册类本身除外）。

## 当前状态与已知问题

1. **`ItemAspectsProvider` 当前未注册**：`TADataGenerators.java` 第 38 行 `// event.createProvider(ItemAspectsProvider::new);` 被注释掉。因此 runData 不会生成 avaritia 物品的 `BASE_ASPECTS` datamap。该类本身完整可用，需要时取消注释即可。

## 待办 / 后续方向

无尽内容可以扩展很多，目前暂停，后续继续开发。已明确的方向：
- 法杖部件（杖端/杖柄）的无尽工作台或注魔配方（尚未添加）。
- 水晶缠绕宇宙中子态素法杖的组装验证（cap + rod + 主杖）。
- 更多 avaritia 物品的要素 datamap 扩展（依赖恢复 `ItemAspectsProvider` 注册）。
- 新的无尽联动物品/配方/机制。
