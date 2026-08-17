# Thaumic Avaritia

[中文指南](README_ZH.md)

An addon mod bridging **[Re:Avaritia](https://github.com/Nova-Committee/Re-Avaritia)** and **[Thaumaturge](https://github.com/Leclowndu93150/Thaumaturge)**, built for **NeoForge 26.1.2** (Minecraft 26.1.2).

Drawing on the crossover content between Avaritia and the original Thaumcraft 4, it brings Avaritia's ultimate materials into the world of Thaumaturge — new items, blocks, essentia machines, wand parts, and research.

## Features

### Items

- **Akashic Record** — instantly unlocks every research point across all registered aspects.
- **Crystal Matrix Wand Cap** — 90% vis cost reduction for your wand.
- **Studded Cosmic Neutronium Wand Rod** — infinite vis capacity.
- **Arcane Singularity Focus** — a spell focus with unlimited complexity (`Integer.MAX_VALUE`).
- **Infinity Scribing Tools** — indestructible writing tools.
- **Causality Collapser Pearl** — a fusion of the Pearl of the Void and Paradox Matter; detonates on impact, collapses the flux rift, and spawns a node at the impact point.

### Blocks

- **Gushing Charger** — provides infinite aura.
- **Cosmic Neutronium Essentia Smeltery** — an instantly-smelting, zero-pollution, zero-loss essentia smeltery with no fuel cost.
- **Node Anchor** — placed above or below an aura node to lock its state; when paired with a Gushing Charger above, it provides every aspect infinitely (except the Infinite aspect).
- **Origin Jar** — contains every registered aspect except the Infinite, each in infinite amounts.

## Requirements

| Dependency                                                   | Type     | Notes                                    |
|--------------------------------------------------------------|----------|------------------------------------------|
| [Thaumaturge](https://github.com/Leclowndu93150/Thaumaturge) | Required | Loads before this mod                    |
| [Re:Avaritia](https://github.com/Nova-Committee/Re-Avaritia) | Required | Loads before this mod                    |
| [Jade](https://www.curseforge.com/minecraft/mc-mods/jade)    | Optional | Adds the Origin Jar "aspect × ∞" tooltip |

## Credits

- **[Thaumaturge](https://github.com/Leclowndu93150/Thaumaturge)** by Leclowndu93150 — the parent mod this addon extends.
- **[Re:Avaritia](https://github.com/Nova-Committee/Re-Avaritia)** by Nova-Committee — the source of the endless materials this addon bridges with Thaumaturge.

### Asset Attributions

The **Infinitus** aspect icon (`textures/aspects/infinitus.png`) is based on artwork from [Game-icons.net](https://game-icons.net/1x1/various-artists/infinity.html/) by [Various](https://commons.wikimedia.org/) artists under [CC0 PDD](http://creativecommons.org/publicdomain/zero/1.0/).

SVG icons have been recolored from their original monochrome versions.

## License

[LGPL-3.0](LICENSE.md)
