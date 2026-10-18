# 🏹 WoolArcherPlugin

**WoolArcherPlugin** is a lightweight plugin for Paper servers (1.21+) that turns regular bow shooting into a spectacular experience. Any arrow that hits a wool block will destroy it, leaving behind beautiful particles, sounds, and taking the arrow with it.

The plugin is fully customizable directly in-game using convenient commands with `Tab`-completion support.

---

## ✨ Features

- 💥 **Wool Destruction:** Any arrow (regular, spectral, crossbow) breaks any color of wool.
- 🎨 **Visual Effects:** Customizable particles (default: texture of the destroyed wool).
- 🎵 **Sound Effects:** A huge selection of sounds (displayed in Tab) when a block is destroyed.
- 🧹 **Arrow Removal:** The arrow disappears after hitting (with a customizable delay).
- 💾 **Settings Persistence:** All changes are instantly saved to `config.yml` and persist after a server restart.
- ⚡ **Hot Reload:** The `/woolarcher reload` command reloads the configuration file on the fly.
- 🛡️ **Permissions:** Commands are restricted to administrators (`woolarcher.admin`).

---

## 🎮 Commands & Configuration

All commands require operator (OP) status or the `woolarcher.admin` permission.
Syntax: `/woolarcher <parameter> <value>`

| Parameter | Description | Example |
| :--- | :--- | :--- |
| `delay` | Arrow despawn delay (in ticks, 20 = 1 sec). | `/woolarcher delay 5` |
| `count` | Number of spawned particles. | `/woolarcher count 50` |
| `spread` | Particle spread radius. | `/woolarcher spread 0.8` |
| `sound` | Destruction sound (press `Tab` to see all sounds). | `/woolarcher sound ENTITY_GENERIC_EXPLODE` |
| `particle` | Particle type (BLOCK, FLAME, HEART, DUST, etc.). | `/woolarcher particle FLAME` |
| `reload` | Reload settings from `config.yml`. | `/woolarcher reload` |

*💡 **Tip:** If you want to change settings directly in the `config.yml` file, don't forget to run `/woolarcher reload` in-game after saving the file so the plugin applies the changes.*

---

## ⚙️ Configuration File (`config.yml`)

After the first launch, a configuration file with the following default settings will be generated in the `plugins/WoolArcherPlugin/` folder:

```yaml
arrow-remove-delay: 1
particle-count: 20
particle-spread: 0.3
sound: BLOCK_WOOL_BREAK
particle: BLOCK
