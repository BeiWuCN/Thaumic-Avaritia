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

包结构即分层：
- `registry/` — 注册入口：`TAItems`（物品）、`TABlocks`（方块+方块物品）、`TABlockEntities`（方块实体类型）、`TAMenus`（菜单类型）、`TAWandParts`（法杖部件）、`TATags`（标签常量）、`TAEntities`（实体）、`TACreativeModeTabs`。
- `content/` — 行为实现：`block/`（方块）、`item/`（物品行为）、`entity/`（实体）、`essentia/smeltery/`（冶炼厂 BE/Menu，**放本 mod 包**）。

### 物理布局约束

- **模块导出硬约束**：自定义类**绝不可**放进 `com.leclowndu93150.thaumaturge.*` 包。flatDir 依赖的 Thaumaturge jar 带有 module-info，跨模块导出冲突会让 runData 崩 `ResolutionException`。因此继承 Thaumaturge 的 protected 父类（如 `BlockEntitySmelter`、`BlockSmelter`）时，子类必须放在本 mod 包（`com.blackmoss.thaumicavaritia.content.essentia.smeltery` 等）而非 `thaumaturge.content.*`。
- **冶炼厂跨包拆分**：宇宙中子态素源质冶炼厂由 4 部分组成：`CosmicNeutroniumEssentiaSmelteryBlock`（`content/block/`）+ `CosmicNeutroniumEssentiaSmelteryBlockEntity`/`CosmicNeutroniumEssentiaSmelteryMenu`（`content/essentia/smeltery/`）+ `CosmicNeutroniumEssentiaSmelteryScreen`（`client/screen/`）。改 UI 或改 BE 逻辑时要同时核对这 4 个文件。
- `mixin/`（+ `mixin/client/`）— mixin 注入；`api/`（`aspect/TAAspects` 要素 key、`interfaces/`）；`data/` — DataGen 体系：`TADataGenerators` 为唯一入口，下辖 `lang/`、`recipe/`、`model/`、`tag/`、`datamap/`、`aspect/`、`worldgen/research/`。

### 主入口与注册流程

`ThaumicAvaritia`（`@Mod`）构造函数按顺序注册：
1. `TACreativeModeTabs.register(modEventBus)`
2. `TAItems.register(modEventBus)` — `DeferredRegister.Items` 注册全部物品（含方块物品 `registerSimpleBlockItem`）。
3. `TABlocks.register(modEventBus)` — 方块注册。
4. `TABlockEntities.register(modEventBus)` — 方块实体类型。
5. `TAMenus.register(modEventBus)` — 菜单类型（`IMenuTypeExtension.create`）。
6. `TAWandParts.register(modEventBus)` — 两个 `DeferredRegister`（`WandCap.REGISTRY_KEY` / `WandRod.REGISTRY_KEY`）把杖端/杖柄写进 **Thaumaturge 的动态注册表**，不是物品注册表。
7. `TAEntities.register(modEventBus)` — 实体。

理解双注册链：**部件（能力）在 Thaumaturge 注册表，物品（载体）在本 mod 注册表**，二者通过构造函数参数绑定。

### DataGen 体系

`TADataGenerators.onGatherData`（`GatherDataEvent.Client`）做两件事：
1. 构造 `RegistrySetBuilder` 并注入 `IAspect.REGISTRY_KEY`（用 `TAAspectBootstrap::bootstrap`，先 `AspectBootstrap.bootstrap(ctx)` 再注册 infinitus），然后 `createDatapackRegistryObjects(registries)`。**这步不可省**——recipe/datamap provider 里都调用 `registries.lookupOrThrow(IAspect.REGISTRY_KEY)`，不注入会直接崩 `Registry thaumaturge:aspect not found`。
2. 依次注册 provider：`EnUsProvider` / `ZhCnProvider` / `TARecipeProvider.Runner` / `ModModelProvider` / `ModBlockTagsProvider` / `ItemAspectsProvider`（全部启用）。另外注册 `LootTableProvider`（`SubProviderEntry(TABlockLootSubProvider::new, LootContextParamSets.BLOCK)`），为本 mod 全部方块生成掉落表（ **`registerBlock` 不会自动生成 loot table**，缺则方块破坏无掉落物）。新增方块务必在 `data/loot/TABlockLootSubProvider.generate()` 补 `dropSelf(...)`。

配方走 `InfusionRecipeBuilder`（Thaumaturge 的 builder），语言用 `LanguageProvider`，模型用 `ModelProvider`，标签用匿名内部 `IntrinsicHolderTagsProvider` 包装成独立 `DataProvider`。

### 跨模组联动方式

- **注魔配方**：同时引用 `TCItems`（Thaumaturge 物品）与 `ModItems`（avaritia 物品）作为材料/催化剂。
- **标签**：把本 mod 物品加进 avaritia 的 `ModTags.IMMORTAL_ITEM`（`#avaritia:endless`）；把 Thaumaturge 的 7 个珍宝加进本 mod 声明的 `TATags.THAUMATURGE_CURIOS`（`#thaumaturge:curios`）。
- **要素 datamap**：`ItemAspectsProvider` 用 `AspectDataMaps.BASE_ASPECTS` 为 avaritia 物品与本 mod 产物补要素构成（已启用，见"当前状态"）。
- **研究点**：`AkashicRecordsItem` 遍历 `IAspect.REGISTRY_KEY` 全部要素，`AspectPoolAccess.grantAll` 一次性授予。
- **法杖 Vis**：`WandAccess.getAllVis` 返回副本，`WandAccess` 的副本不能直接写回，必须 `wand.set(TCDataComponents.WAND_VIS.get(), full)`。

### 依赖与 API 边界

`build.gradle` 关键依赖（**不要移除**）：
1. Thaumaturge（flatDir 指向 `E:/MossProject/MinecraftJava/Thaumaturge/build/libs`，`compileOnly` + `localRuntime`）——本项目唯一直接依赖的 Thaumaturge API 来源，编译靠该 jar。
2. Avaritia（Modrinth Maven，`compileOnly` + `localRuntime`）——`committee.nova.mods.avaritia` 包，入口 `init.registry.ModItems` / `ModTags` / `ModApi`。
3. Jade（`flatDir run/mods`，`compileOnly` + `localRuntime name: "Jade-mc26.1-NeoForge-26.1.8"`）——可选运行时 mod，供 `compat/jade/` 的罐子显示扩展编译。

`src/main/templates/META-INF/neoforge.mods.toml` 是元数据模板（唯一权威来源，别改生成物）。依赖声明：`neoforge`（required）、`minecraft`（required）、`thaumaturge`（required，ordering AFTER）、`avaritia`（required，ordering AFTER）。

**API 边界**：依赖 Thaumaturge 时只读 `com.leclowndu93150.thaumaturge.api.*` facade；可例外使用注册类本身（`ItemWandCap` / `ItemWandRod` / `TCItems` / `TCDataComponents`）、`data.recipe.builders` / `data.worldgen.aspect`，以及明确指定例外（`NodeGenerator`、`content.essentia.smeltery` 的 `BlockEntitySmelter` 等被继承的父类）。**不要碰** `content`/`registry` 内部实现。

## 已实现内容

### 物品（`registry/TAItems.java`）
| 注册名                               | 类                             | 说明                                  |
|--------------------------------------|--------------------------------|---------------------------------------|
| `extremely_primordial_pearl`         | `ExtremelyPrimordialPearlItem` | 极元始珍珠，注魔材料（EPIC）          |
| `akashic_record`                     | `AkashicRecordsItem`           | 阿卡西记录，使用后授予全部要素研究点  |
| `wand_cap_crystal_matrix`            | `ItemWandCap`                  | 水晶矩阵杖端，90% Vis 减免            |
| `wand_cap_crystal_matrix_inter`      | `Item`                         | 惰性水晶矩阵杖端（无 halo，普通扁平模型）|
| `wand_rod_studded_cosmic_neutronium` | `ItemWandRod`                  | 宇宙中子态素杖柄，超高容量 + 自动回满 |
| `arcane_singularity_focus`           | `ArcaneSingularityFocusItem`   | 奥法奇点核心，复杂度上限 `Integer.MAX_VALUE`（无限）|
| `infinity_scribing_tools`            | `InfinityScribingToolsItem`    | 无尽笔具，研究台内耐久回复 + 自动回满 |
| `causality_collapser_pearl`          | `CausalityCollapserPearlItem`  | 悖论珍珠（可投掷实体）                |
| `infinity_charger`（方块物品）        | `BlockItem`                    | 涌流充能器方块物品                    |
| `cosmic_neutronium_essentia_smeltery`（方块物品）| `BlockItem`        | 宇宙中子态素源质冶炼厂方块物品        |
| `node_anchor`（方块物品）             | `BlockItem`                    | 节点锚（锁定/充能灵气节点）           |
| `origin_jar`（方块物品）              | `BlockItem`                    | 万物起源之罐（除无限外全要素各 32767）|

### 方块（`registry/TABlocks.java` + `content/block/`）

**涌流充能器（`InfinityChargerBlock`，注册名 `infinity_charger`）**
- 显示名「涌流充能器」/ "Gushing Charger"（中文描述"滴水之恩"）。代码类名即"无限充能器"。
- 放在**奥术工作台上方**（`canSurvive` 要求下方非空气），为其提供无限灵气。
- **模型**：手写 tcmesh 模型（复用 `thaumaturge:models/mesh/arcane_workbench_charger.tcmesh`，仅纹理指向 `thaumicavaritia:block/infinity_charger`）；blockstate/items 由 DataGen 生成；物品模型用 **Halo 光环**（内层指向 block 模型，非扁平贴图）。
- **配方**：注魔。催化剂 `TCItems.EVERFULL_URN`（永满之瓮）+ 4×`ModItems.infinity_ingot` + 4×`ModItems.neutron_ingot`，Auram/Praecantatio/Vacuos×640 + 无尽×16，instability 5。
- 纹理：`textures/block/infinity_charger.png`（用户手绘）。

**宇宙中子态素源质冶炼厂（`CosmicNeutroniumEssentiaSmelteryBlock`，注册名 `cosmic_neutronium_essentia_smeltery`）**
- 显示名「宇宙中子态素源质冶炼厂」/ "Cosmic Neutronium Essentia Smeltery"（中文描述"效率最大化"）。
- 方块**继承原版 `BlockSmelter`**（复用外观/放置/朝向），覆写 `codec()`/`newBlockEntity`/`getTicker`/`useWithoutItem`。**必须覆写 `codec()`**——父类 codec 用 `BlockSmelter::new`，不覆写会导致反序列化创建父类实例。
- BE `BlockEntityCosmicNeutroniumEssentiaSmeltery extends BlockEntitySmelter`（`content/essentia/smeltery/`，本 mod 包）：`getEfficiency()` 恒 1.0F（0 污染/无丢率），`cosmicTick()` 实现 0 燃料 + 瞬间冶炼。
- **mixin `BlockEntitySmelterMixin`**：原版 `tick()` 是 private 无法继承覆写 → `@Inject(method="tick", at=@At("HEAD"), cancellable=true)`，`instanceof` 自定义 BE 时改调 `cosmicTick()` 并取消原逻辑（原版冶炼炉不受影响）。
- `cosmicTick()`：LIT 状态**跟随输入槽**（槽内有物品才亮，平时熄灭，与瞬间冶炼同步）；每 tick 把输入物品全部要素写入 `aspects`（无效率损失）并消耗 1 个输入 = 瞬间产出；输出路由复刻原版 `processAlembics`（直连上方蒸馏釜 + 四方辅助蒸馏器），只用 public API（`aspectKey`/`amount`/`doesContainerAccept`/`addToContainer`）。
- **模型**：手写 `_off`/`_on` 双模型（parent 原版 `smelter_basic_off`，`north` 分别指 `front_off`/`front_on`，`side`/`up`/`particle` 自绘）；blockstate 用 `MultiVariantGenerator.dispatch` + `createBooleanModelDispatch(LIT, on, off)` + `ROTATION_HORIZONTAL_FACING`（facing 4 向 × lit 2 态）；物品模型用 **Halo 光环**（内层指向 `_off` 模型）。
- **菜单/Screen**：`CosmicNeutroniumEssentiaSmelteryMenu`（单物品槽，无燃料槽）+ `CosmicNeutroniumEssentiaSmelteryScreen`（使用自绘纹理 `textures/gui/cosmic_neutronium_essentia_smeltery.png`，只画源质存量条，不画燃料/进度条）。注册见 `TAMenus`/`TAClientEvents`。
- **UI 坐标契约（改 GUI 纹理时必须同步）**：
  - 主界面 `176×166`，图集尺寸 `256×256`。
  - 物品槽逻辑坐标 `Menu.ITEM_X = 80`、`ITEM_Y = 32`，须与纹理中左侧输入槽中心对齐。
  - 源质条容器位于界面内 `(62,21)-(69,67)`，高 46 宽 8 像素；素材区在图集 `(177,1)-(184,47)`，宽 8 高 46。
  - Screen 进度条：`visScaled = blockEntity.getVisScaled(46)`（`BE.MAX_VIS = 256` 映射到 0-46 像素）；blit 从 `(177, 47 - visScaled)` 取 7×visScaled 的紫色素材，覆盖到 `(x+61, y+67-visScaled)`——**从底部向上填充、垂直裁剪取底部，非缩放**。改纹理或改条尺寸时要同步修改 `Screen`、`Menu` 和 `BE.MAX_VIS`。
- **配方**：注魔。催化剂 `TCItems.SMELTER_VOID`（虚空冶炼炉）+ 4×`ModItems.neutron_ingot` + 4×`ModItems.infinity_ingot`，Auram/Praecantatio/Vacuos×640 + 无尽×16，instability 5。
- 纹理：`textures/block/cosmic_neutronium_essentia_smeltery_{front_off,front_on,side,top}.png`（用户手绘，前两张不同、侧/顶另两版）。

**节点锚（`NodeAnchorBlock`，注册名 `node_anchor`）**
- 显示名「节点锚」/ "Node Anchor"（中文描述"你在此处不要走动"）。放在**灵气节点下方**（同稳定器位置），结构：节点 → 节点锚 → 涌流充能器（自顶向下），`FACING`（UP=爪向上托节点 / DOWN=爪向下压节点，Shift 放置朝下）+ `CHARGED` 双方块状态。
- 双模式（`NodeAnchorBlockEntity.serverTick`，`content/block/`）：**锁定模式**（无充能器或红石）冻结节点状态——`Snapshot` 捕获 aspect/type/modifier，tick 内 `setAspects/setNodeType/setNodeModifier` 还原 + `nodeChange()` 同步；玩家抽走 vis 总量下降时重新快照。**充能模式**（上方两格是涌流充能器且无红石）：`node.setEnergized(true)` 进入原版换能器充能态（要素分解为原初、每 tick 自动从灵气补充、可被魔杖抽取），退出时 `setEnergized(false)` 并用快照还原。级联充能：`hasChargerAbove` 支持锚上方是已充能锚。
- 与 Thaumaturge 原版稳定器/换能器区别：`BlockNodeStabilizer` 是 final 且锁定逻辑在节点私有方法，无法复用 → 自实现，只走节点 public API，不碰内部。
- **模型**：手写 `node_anchor.json` + `node_anchor_charged.json` 双模型（稳定器/换能器几何，共用纹理）；blockstate 由 DataGen `PropertyDispatch.initial(FACING, CHARGED)` 生成 4 组合（DOWN 时 `X_ROT_180`，CHARGED 切换造型）；物品模型用 **Halo 光环**。**`RenderShape.MODEL`**（不能照抄原版稳定器 `INVISIBLE`——那依赖 BER 代码绘制，本方块无 BER 会完全不渲染）。
- **气泡渲染**：`NodeAnchorBlockEntityRenderer` 在节点所在格中心画 billboard 气泡（复用 `thaumaturge:textures/misc/node_bubble.png`，`entityTranslucentEmissive` 发光、脉冲透明度），`NodeAnchorBlockEntityRenderState` 携带 `ticks/light/hasNode/bubbleY`（节点在上=+1.5 / 在下=-0.5）。
- **`NodeRendererMixin`**（mixin 根包，client 列表）：`extractRenderState*` RETURN 时若 `state.energized` 则 `state.size *= 0.5`——充能节点渲染缩小一半（仅视觉，不影响逻辑）。
- **配方**：注魔。催化剂 `ModItems.eternal_singularity` + 2×`TCItems.NODE_STABILIZER` + 2×`TCItems.NODE_TRANSDUCER` + 4×`ModItems.neutron_ingot`，Auram×640 / Potentia×480 / Ordo×480 / Machina×480 + 无尽×16，instability 6。
- 研究：手写 `data/thaumicavaritia/thaumaturge/research_entry/node_anchor.json`（zh「节点锚」）。

**万物起源之罐（`OriginJarBlock`，注册名 `origin_jar`）**
- 显示名「万物起源之罐」/ "Origin Jar"（中文描述"诸法之本，万源之始"）。内含**除「无限」外所有已注册源质各 32767 点**，**只出不进**（可抽取，不可注入）。
- 方块继承原版 `BlockJar`（复用罐子形状/`IEssentiaJar` 纹理接口/流端口），覆写 `codec()`（返回 **`MapCodec<? extends BlockJar>`**）/`newBlockEntity`/`getTicker`(null)/`useItemOn`/`useWithoutItem`（PASS，避免父类对 `BlockEntityJar` 强转）。
- **BE `OriginJarBlockEntity`（`content/essentia/jar/`，本 mod 包）**：**不继承 `BlockEntityJar`**（其单要素 int 结构无法存多要素），直接 `extends BlockEntity implements IEssentiaTransport, IAspectSource`。懒加载 `AspectList`：遍历 `IAspect.REGISTRY_KEY` 排除 `TAAspects.INFINITUS`，每要素 `PER_ASPECT_AMOUNT = 32767`。只出不进：`canInputFrom`/`addEssentia`/`addToContainer`/`doesContainerAccept` 全部拒绝；`takeEssentia`/`takeFromContainer` 扣减。序列化 `AspectList.CODEC` 存 "Contents"。
- 能力：`content/essentia/jar/OriginJarCapabilities` 注册 `EssentiaCapabilities.TRANSPORT` + `AspectCapabilities.CONTAINER`。
- **∞ 显示**：`mixin/client/AspectTagRendererMixin`（`drawAmount`，amount==32767 渲染 ∞）+ `mixin/client/AspectContainerTooltipHandlerMixin`（`@ModifyArg` 命中 `Component.literal`，`" x32767"`→`" x∞"`）。
- **黑洞渲染**：`client/render/blockentity/OriginJarBlockEntityRenderer` 复刻 avaritia `GapingVoidRender.submit` 的管线——`AvaritiaRenderTypes.BLACK_HOLE` + `AvaritiaShaderUniforms.set(renderType, Effect.BLACK_HOLE, ...)` + `SimpleMesh`（4 顶点 quad）。**静态黑洞**：shader `time`=0（无旋转/噪声动画），scale 固定 `BLACK_HOLE_SCALE=0.20F`，中心 `CENTER_Y=5/16`（罐体中心），不再使用 `GapingVoidEntity.getVoidScale`/`maxLifetime`。注册见 `TAClientEvents`。
- **Jade 扩展**（`compat/jade/`，仅一行「要素 x∞」不枚举各要素）：`OriginJarJadePlugin`（`@WailaPlugin`，`registerBlockComponent(OriginJarComponentProvider.INSTANCE, OriginJarBlock.class)`——传 **Block Class 而非实例**）+ `OriginJarComponentProvider`（enum 单例，`tooltip.add(translatable("jade.thaumicavaritia.origin_jar.contents"))`）+ `mixin/client/EssentiaComponentProviderMixin`（HEAD 判断 BE 是 origin_jar 时 cancel，抑制 Thaumaturge 默认数字行）。**build.gradle 需保留 Jade 依赖**（`flatDir run/mods` + `compileOnly/localRuntime name: "Jade-mc26.1-NeoForge-26.1.8"`）。
- **坑（资源重载失败）**：Jade 打开 GUI 配置界面时会对每个注册的 provider 校验 `config.jade.plugin_<modid>.<uid>` 翻译键（uid 即 `getUid()` 去掉命名空间后的名字），缺失会抛 `AssertionError: Missing config translation` → **整个资源包加载被回滚**（`Caught error loading resourcepacks`），游戏内按 F3+T 重载同样失败。每个 Jade provider 必须同时在 lang 里提供 `config.jade.plugin_<modid>.<uid>`（参照 Thaumaturge 本体的 `config.jade.plugin_thaumaturge.node/essentia/...`）。
- **模型**：手写 block 模型 `origin_jar.json`（复用 jar 几何，纹理 `origin_jar_{side,top,bottom}` 待用户自绘）；blockstate 由 DataGen；物品模型用 **Halo 光环**。
- **bug 修复（挖掘不掉落 + 粒子异常）**： (1) 模型 `origin_jar.json` 缺 `particle` 纹理变量 → 破坏粒子异常，已加 `"particle": "thaumicavaritia:block/origin_jar_side"`。 (2) 不掉落：根因是 **所有方块都缺 loot_table**（NeoForge `registerBlock` 不自动生成）。已在 `ModBlockLootSubProvider` 给 4 个方块 `dropSelf`，`runData` 生成 `loot_table/blocks/origin_jar.json` 等。 **注意**：新增方块务必在 `TABlockLootSubProvider.generate()` 补 `dropSelf`，掉落统一走 loot table DataGen，不要靠 `getCloneItemStack` 覆写。
- **配方**：注魔。催化剂 `TCItems.JAR_VOID`（虚空罐子）+ 无限锭×4/元始珍珠×2/世界盐×2，Auram/Praecantatio/Vacuos/Alienis×640 + 无尽×16，instability 6。
- **Jade 显示**：见上方「黑洞渲染」后的 Jade 扩展。

### 法术核心（`content/item/ArcaneSingularityFocusItem.java`）
- 继承 Thaumaturge 的 `ItemFocus`，构造传 `Integer.MAX_VALUE` 实现无限复杂度（原版 FOCUS_1/2/3 为 15/25/50）。
- 模型与原版核心一致：`FLAT_ITEM` + `FocusColorTint`（`thaumaturge:focus_color`，颜色随核心内法术元素）。当前 layer0 临时引用 `thaumaturge:item/focus_3`，若用户自绘 `textures/item/arcane_singularity_focus.png` 后改回 `TextureMapping.layer0(item)`。

### 无尽笔具（`content/item/InfinityScribingToolsItem.java`）
- 继承 Thaumaturge 的 `ScribingToolsItem`，研究台内（`ResearchTableMenu` 物品槽）自动补耐久 + `inventoryTick` 回满，实现"无限使用"。
- 已加研究（`infinity_scribing_tools`，note_aspects 含物品要素种类 + 无尽）。

### Mixin 基础设施（`mixin/` + `thaumicavaritia.mixins.json`）
- `neoforge.mods.toml` 模板已启用 `[[mixins]] config="${mod_id}.mixins.json"`；NeoForge 26.x 用官方映射，mixin **无需 refmap**。
- **`BlockEntityFocalManipulatorMixin`**：
  - `@Redirect` `startCraft` 中的 `ItemFocus.getMaxComplexity()` 调用，奥法奇点核心返回 0 → 组装 Vis 公式 `totalComplexity * 10 + maxComplexity / 5` 的上限基础成本归零（仅剩内容复杂度成本），普通核心不受影响。
  - 新增：`@Inject(method="spendAura", at=@At("HEAD"), cancellable=true)`，上方为无限充能器时 `cir.setReturnValue(amount)`（灵气全额满足不消耗）。
  - 注意：`BlockEntityFocalManipulator` 是 `final class`，mixin 改字节码不受影响。`getMaxComplexity()` 同时被 UI 复用（`FocalManipulatorScreen` 显示 `totalComplexity/maxComplexity` + 校验 `totalComplexity <= maxComplexity`），所以不能靠覆写它归零——UI 校验会把任何组装判为非法；mixin 只作用于 `startCraft`，不影响 UI 的无限上限显示与永真校验。
- **`BlockEntityArcaneWorkbenchMixin`**（奥术工作台无限灵气）：`@Shadow public int auraVis`；`@Inject(refreshAura, HEAD, cancellable)` 上方为无限充能器时 `auraVis=Integer.MAX_VALUE` 并取消原逻辑；`@Inject(spendAura, HEAD, cancellable)` 上方为无限充能器时直接 cancel（不扣灵气）。
- **`BlockEntitySmelterMixin`**（冶炼厂改判）：见上方方块章节。
- **`client/ArcaneWorkbenchScreenMixin`**（显示 ∞）：`@ModifyArg(method="extractBackgroundOverlay", at=@At(value="INVOKE", ordinal=0, target="...Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"), index=1)`，命中第一个 `translatable` 调用（vis_available），当 `menu.getCachedVis() >= Integer.MAX_VALUE` 时把 varargs 换成 `"\u221E"`。
  - **坑**：`Component.translatable(String, Object...)` 返回类型是 **`MutableComponent`**，target descriptor 必须写 `Lnet/minecraft/network/chat/MutableComponent;`（写 `Component` 会匹配失败导致注入崩溃）。不用字符串 key 比较（`@Redirect` 会把 key 静态关联到某个调用点，IDE 报恒 false），改用 `@ModifyArg` + `ordinal=0`。
- **`BlockEntityResearchTableMixin`**、**`EntityFluxRiftMixin`**（裂隙坍缩）、**`GapingVoidEntityMixin`**（黑洞结束生成节点）、**`GapingVoidRenderMixin`**（黑洞放大，位于 `mixin` 根包而非 `mixin.client`，见 `thaumicavaritia.mixins.json` 的 `client` 列表）——详见后续相关章节。
- **门禁 mixin**：`TierCraftMenuMixin`（无尽工作台）、`ExtremeSmithingMenuMixin`（无尽锻造）+ **accessor `ItemCombinerMenuAccessorMixin`**——`resultSlots`/`player` 在 `ExtremeSmithingMenu` 父类 `ItemCombinerMenu`（protected final），`@Shadow` 无法解析父类字段（报 `Unresolved field`），必须用 accessor 取。
- **`NodeRendererMixin`**（根包）：energized 节点渲染缩小一半（见节点锚章节）。**`client/EssentiaComponentProviderMixin`**（Jade）：跳过 origin_jar 默认数字行。

### 悖论珍珠（`content/entity/EntityCausalityCollapserPearl.java` + 相关）
- 参数：爆炸 8.0 / 裂隙坍缩 12.0 / 伤害半径 16.0 / 伤害 20。命中：爆炸 → 裂隙坍缩（`IFluxRiftCollapsible.thaumicavaritia$collapse(GapingVoidEntity.maxLifetime)`，经 `EntityFluxRiftMixin` 加速使裂隙 144 tick 内坍缩完与黑洞同步）→ 生成 `GapingVoidEntity`（落点）→ 无伤传送玩家（`player.teleport(new TeleportTransition(level, pos+0.5Y, Vec3.ZERO, yRot, xRot, PLAY_PORTAL_SOUND))` + `resetFallDistance()`）。
- 拖尾：`Effects.fireMoteData` + `ShieldSparkParticleOptions`（复刻悖论物质）。渲染器 `client/render/entity/CausalityCollapserPearlRenderer`（`ThrownItemRenderer`）。
- **`GapingVoidEntityMixin`**：黑洞（`GapingVoidEntity`）结束时生成灵气节点——扫描周围 6³ 方块聚合 `BASE_ASPECTS` 要素，**节点类型随机**（`NodeType.values()[level.random.nextInt(types.length)]`，含 NORMAL/UNSTABLE/DARK/TAINTED/HUNGRY/PURE，不按要素决定），调用 `NodeGenerator.createNodeAt`。不再有 debug 日志。
- **`GapingVoidRenderMixin`**（位于 `mixin` 根包）：`@Redirect` `getVoidScale` 返回值 ×2（黑洞渲染放大，影响所有 GapingVoid）。
- 研究文本：`research.thaumaturge.causality_collapser_pearl.*`（中英已补全，讲悖论物质与终望珍珠同源合一）。

### 法杖部件（`registry/TAWandParts.java`）
- 注册到 Thaumaturge 的法杖注册表（`WandCap.REGISTRY_KEY` / `WandRod.REGISTRY_KEY`），不是物品注册表。
- `CRYSTAL_MATRIX`：`baseCostModifier = 0.1F`（90% 减免）。
- `NEUTRONIUM`：`capacity = 9_999_999`（vis，Thaumaturge 内部以 centivis 存储，写入时乘 100），`onUpdate = InfinityOnUpdate` 每 tick 把全部原初要素补满（写入 `TCDataComponents.WAND_VIS`）。
- 纹理路径：部件渲染用 `assets/thaumicavaritia/textures/models/wand_cap_crystal.png`、`wand_rod_studded_cosmic_neutronium.png`；物品形态另有 `textures/item/` 下同名 PNG（**纹理由用户手绘，AI 不要生成图片**）。
- **资格门禁（`#thaumicavaritia:not_qualified` 标签）**：带此标签的物品必须解锁**同名研究**（研究条目 id = 物品注册 id，含命名空间）才能获得/合成，否则提示「你还不够格掂量它」且不能入包。核心是 **`mixin/TierCraftMenuMixin`**（无尽工作台，`slotsChanged` TAIL 清空结果槽）+ **`mixin/ExtremeSmithingMenuMixin`**（无尽锻造，`createResult` TAIL 清空结果槽）——未解锁时结果槽不产出、材料不消耗。兜底：`content/event/TAServerEvents`（`@EventBusSubscriber` 默认 Dist.BOTH + `instanceof ServerPlayer`，监听 `ItemCraftedEvent`）把其它途径获得的门禁物品移出背包并提示。共享逻辑在 `content/NotQualifiedGate`（`isGated`/`researchId`/`isResearchUnlocked`）。研究解锁判断：`KnowledgeAccess.of(player).hasResearchFlag(researchId, ResearchFlag.RESEARCH)`；`KnowledgeAccess` 未绑定时保守放行。**注意**：事件注册必须用默认 Dist.BOTH（`runClient` 是集成服务器，`Dist.DEDICATED_SERVER` 不注册导致不生效）。已加标签：杖柄（`wand_rod_studded_cosmic_neutronium`）+ 奥法奇点核心（`arcane_singularity_focus`，无尽锻造产物）。

### 物品模型（`data/model/TAModelProvider.java`）
- 普通物品使用 avaritia 的**无尽背景光圈（halo）**：内层模型（`createFlatItemModel`，自动引用 `item/<注册名>.png`）+ `AvaritiaItemModelLoaders.Halo` 物品模型（`pulse=false, size=6, texture=avaritia:misc/halo`），通过 `itemModels.itemModelOutput.accept(item, halo)` 写入 items json。
- **方块物品的 halo**：不用 `createFlatItemModel`（方块无 item png），直接 `itemModels.itemModelOutput.accept(block.asItem(), new AvaritiaItemModelLoaders.Halo(blockModelPath, haloTex, -16777216, 6, false, List.of()))`，内层 `model` 指向 block 模型路径。
- 生成 `items/<注册名>.json` 与 `models/item/<注册名>.json`；**不要**在 `src/main/resources/assets/.../items/` 下手写 items json——DataGen 已生成，手写会导致 `processResources` 重复文件报错。
- 若物品模型由其它来源提供（绕过 DataGen 校验），用 `itemModels.declareCustomModelItem(item)`（仍会生成默认 items json，需注意与手动文件冲突）。

### 配方（`data/recipe/TARecipeProvider.java`）
- 极元始珍珠：8 × `TCItems.PRIMORDIAL_PEARL` 注魔（Alienis×32 / Perditio×16，instability 4）。
- 阿卡西记录：催化剂 `#thaumaturge:curios` + 4 × `ModItems.infinity_ingot` 注魔（Cognitio×64 / Praecantatio×64 / Alienis×32）。
- 涌流充能器 / 宇宙冶炼厂：见上方方块章节。
- 全部使用 `InfusionRecipeBuilder`，已带 `.unlockedBy(...)`。

### 研究类别（`data/worldgen/research/TACategoryBootstrap.java`）
- "神秘无尽"（`thaumicavaritia:arcane_infinity`）类别：`IResearchCategory.REGISTRY_KEY` datapack registry，用 `BootstrapContext` 注册（与 aspect 同机制），DataGen 生成到 `data/<ns>/thaumaturge/research_category/`。
- 门控 `required_research = thaumicavaritia:unlock_thaumicavaritia`。
- `icon` = `thaumicavaritia:textures/item/akashic_record.png`（阿卡西记录图标），`background` = `avaritia:textures/block/diamond_lattice_block.png`，`overlay_background` = Thaumaturge 的 `gui_research_back_over.png`（跨命名空间直接引用纹理 Identifier，运行时 Thaumaturge/avaritia 必在）。`index=7` 排在 Thaumaturge 原版 6 类之后。
- `formula` 决定扫描要素转知识点的权重（applyFormula 用平方根）。lang 键 `research_category.thaumicavaritia.arcane_infinity`。
- 注意：研究条目本身是**手写 JSON**（`data/<ns>/thaumaturge/research_entry/`），不经过 DataGen；只有类别经 bootstrap+runData 生成。已创建条目：`base_thaumicavaritia`（隐藏根节点）、`unlock_thaumicavaritia`（√级）、`akashic_record`、`wand_cap_crystal_matrix`、`wand_rod_studded_cosmic_neutronium`、`arcane_singularity_focus`、`infinity_scribing_tools`、`node_anchor`。

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
- **方块标签**：`ModBlockTagsProvider` 同时持有一个 `IntrinsicHolderTagsProvider<Block>`（`Registries.BLOCK` + `builtInRegistryHolder().key()`），`run()` 用 `CompletableFuture.allOf` 并行执行。`#minecraft:mineable/pickaxe`（`BlockTags.MINEABLE_WITH_PICKAXE`）加入涌流充能器/宇宙冶炼厂/节点锚， **排除 origin_jar（玻璃罐体）**。生成到 `data/minecraft/tags/block/mineable/pickaxe.json`。
- 标签不存在的就直接加，不要反复验证。

### 要素（`api/aspect/TAAspects.java` + `data/aspect/TAAspectBootstrap.java`）
- `INFINITUS`（无尽）要素，`thaumicavaritia:infinitus`，颜色 0x1E1E1E，纹理 `textures/aspects/infinitus.png`（用户手绘），blend 用 `Aspect.DEFAULT_BLEND`。
- **无合成配方**：`components = List.of()`（不设父要素）——要素合成器无法用其它要素强行合成"无尽"，只能通过注魔/研究获得。`Aspect.validate` 允许 0 或 2 个 components，空列表合法。
- 跨命名空间注册可行（`thaumicavaritia:infinitus` 注册进 `IAspect.REGISTRY_KEY`）。
- DataGen 会生成 `aspect/infinitus.json`（无 components 字段）。

### 要素 datamap（`data/datamap/ItemAspectsProvider.java`）
- 为 avaritia 的 `infinity_ingot`、`neutron_ingot`、`endest_pearl`、`infinity_catalyst`、`crystal_matrix_ingot`、`neutron_pile`、`infinity_nugget` 补充 `BASE_ASPECTS` 要素构成，供扫描/研究系统使用。
- 已启用（`TADataGenerators` 中 `event.createProvider(ItemAspectsProvider::new)` 处于激活状态）。

### Avaritia shader 修复（`src/main/resources/assets/avaritia/shaders/core/black_hole.fsh`）
- avaritia 原版 `black_hole.fsh` 含中文注释导致 GLSL 编译崩溃，本项目通过资源覆盖提供修复版：删除中文注释 + 辅助函数改名（`hash3`→`blackHoleHash3`、`noise3`→`blackHoleNoise3`），其余逻辑与原版一致。
- 仅需覆盖 fsh：`black_hole.json` 按 `avaritia:black_hole` 引用同目录 vsh/fsh，vsh 与 json 均无中文，无需覆盖。
- **该文件是跨命名空间资源覆盖（`assets/avaritia/...`），必须保留，不要删。**

### DataGen（`data/TADataGenerators.java`）
- 注册了：`EnUsProvider` / `ZhCnProvider` / `TARecipeProvider.Runner` / `ModModelProvider` / `ModBlockTagsProvider` / `ItemAspectsProvider`（均已启用）。
- 注入 `TAAspectBootstrap` 到 `RegistrySetBuilder`（先 `AspectBootstrap.bootstrap` 再注册 infinitus）。
- lang 已迁移为 DataGen：`item.thaumicavaritia.*`、`block.thaumicavaritia.*`、`item.thaumicavaritia.*.desc`（lore，灰色斜体，经 `TATooltipHandler` 附加）、`research.thaumaturge.*`、`research_category.thaumicavaritia.arcane_infinity`、`singularity.thaumicavaritia.*`。`assets/.../lang/` 不再手写。
- **坑**：`LanguageProvider.add` 对重复键抛 `IllegalStateException`，同一个 key 只能 add 一次（曾因 `research_category.thaumicavaritia.thaumicavaritia` 重复导致 runData 失败）。

### 方块描述（`content/item/TATooltipHandler.java`）
- 用 `@SubscribeEvent`（`ItemTooltipEvent`）为指定物品附加描述行（灰色斜体），键 `item.thaumicavaritia.<注册名>.desc`。
- 已支持：6 个物品 + 涌流充能器（滴水之恩）+ 宇宙冶炼厂（效率最大化）。用法：`appendHoverText` 5 参（`ItemStack, Item.TooltipContext, TooltipDisplay, Consumer<Component>, TooltipFlag`）+ `builtInRegistryHolder()` 查 item datamap，均加 `@SuppressWarnings("deprecation")`。

## 关键 API 备忘

### Thaumaturge
- 研究点授予：`AspectPoolAccess.grantAll(player, AspectList)`；遍历注册表用 `IAspect.REGISTRY_KEY` lookup。
- 法杖 Vis：`WandAccess.getAllVis(stack)` 返回 `WandVis`，`WandVis.with(aspect, amount)`；写入用 `stack.set(TCDataComponents.WAND_VIS.get(), vis)`（注意 `WandAccess` 的副本不能直接写回）。
- 要素：`TCAspects.PRIMALS`、`TCAspects.ALIENIS` 等为 `ResourceKey<IAspect>`；本 mod 用 `TAAspects.INFINITUS`。
- 注魔配方：`InfusionRecipeBuilder`（见 `data/recipe` 用法）。
- 蒸馏釜输出：`BlockEntityAlembic.processAlembics(level, pos, aspectHolder)` 是 **protected static**，外部类需用其 public API 复刻：`aspectKey()`/`amount()`/`doesContainerAccept(aspect)`/`addToContainer(aspect, n)`（返回 0 表示成功）。
- **模块导出坑**：自定义类**不能放进 `com.leclowndu93150.thaumaturge.*` 包**（flatDir 依赖的 module-info 导出冲突，runData 崩 `ResolutionException`）。要复用 protected 成员（如 `processAlembics`），用其 public API 复刻或 mixin，而不是挪包。

### Avaritia
- 物品：`ModItems.infinity_ingot.get()`、`ModItems.endest_pearl.get()` 等。
- 标签：`ModTags.IMMORTAL_ITEM`（即 `#avaritia:endless`）。
- halo 物品模型：`committee.nova.mods.avaritia.client.model.loader.AvaritiaItemModelLoaders.Halo`（implements `ItemModel.Unbaked`，`itemModelOutput.accept` 会按 `type()` 输出 `avaritia:halo`）。

## 约定与注意事项

- **纹理由用户手绘**，AI 一律不生成图片文件。
- 命名统一："Akashic Records"（`akashic_record`），不要出现其它变体。方块显示名与注册名可不同（如 `infinity_charger` 显示「涌流充能器」）。
- JSON 一律 snake_case。
- 涉及图像渲染（blit 等）时所有放大必须**等比**，不能拉伸或裁剪（目标宽高比必须等于源宽高比，且使用完整源图）。
- 所有公共 API 参数做空值校验（`ArgumentNullException.ThrowIfNull` 风格），返回集合用空集合而非 null；不确定非空的变量必须显式检查（最高优先级）。
- 注释一律使用 `//` 双斜杠，不用 `/* */` 或 Javadoc。
- 依赖 Thaumaturge 时只读 `com.leclowndu93150.thaumaturge.api.*` facade，不要碰 `content`/`registry` 内部（已特例：`NodeGenerator`、被继承的 `BlockEntitySmelter`/`BlockSmelter`/`BlockEntityAlembic` 等）。
- **mixin 对 flatDir 依赖的类**（Thaumaturge/avaritia jar）：IDE 静态分析可能报 `Could not resolve target`/恒 false，优先用 `@At("HEAD")` + `instanceof`/状态判断，少用 `@At(INVOKE)`（仅当 target 是 Minecraft 类如 `Component` 时可靠）。
- **mixin target descriptor 必须与字节码精确一致**：如 `Component.translatable(String,Object...)` 返回 `MutableComponent`，写错返回类型会注入失败崩溃。
- **runData 失败排查**：`LanguageProvider.add` 重复 key、模块导出冲突、`Missing blockstate definitions`（自定义方块必须由 DataGen 生成 blockstate）、`import java.util.List` 误删导致旧 class 静默使用——改完代码务必确认 `BUILD SUCCESSFUL`。

## 当前状态与已知问题

1. **`ItemAspectsProvider` 已注册并启用**：`TADataGenerators` 中 `event.createProvider(ItemAspectsProvider::new)` 处于激活状态，runData 会生成 avaritia 物品与本 mod 产物的 `BASE_ASPECTS` datamap（详见 `data/datamap/ItemAspectsProvider.java` 中的要素量级规划）。
2. **无尽工作台配方仍为占位**：水晶杖端（`wand_cap_crystal_matrix`）的 `avaritia:shaped_table` 配方当前用铁锭占位（9×9 边框），正式材料待替换。
3. **万物起源之罐纹理待自绘**：`textures/block/origin_jar_{side,top,bottom}.png` 尚未提供（AI 不生成图片），当前模型引用会显示缺失纹理。
4. **黑洞/气泡渲染需实测**：`OriginJarBlockEntityRenderer`（avaritia shader 管线）与 `NodeAnchorBlockEntityRenderer`（Thaumaturge 纹理 + 标准 MC 渲染）运行时效果需 runClient 验证。
5. **`not_qualified` 门禁物品**：当前杖柄 + 奥法奇点核心带 `#thaumicavaritia:not_qualified` 标签；`arcane_singularity_focus` 也需要同名研究条目（`thaumicavaritia:arcane_singularity_focus`）才能解锁，否则合成被拦截。
6. **节点锚需实测**：`NodeAnchorBlockEntity.serverTick` 对节点状态的冻结/充能还原、级联充能、红石关断逻辑需 runClient 验证；`node_anchor` 研究条目已建（手写 JSON）。

## 待办 / 后续方向

- 法杖部件（杖端/杖柄）的无尽工作台或注魔配方的正式材料替换。
- 水晶缠绕宇宙中子态素法杖的组装验证（cap + rod + 主杖）。
- 更多 avaritia 物品的要素 datamap 扩展（`ItemAspectsProvider` 已启用，直接加条目即可）。
- 新的无尽联动物品/配方/机制。
