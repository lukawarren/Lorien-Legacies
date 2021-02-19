# Lorien-Legacies

## Description
Lorien Legacies seeks so recreate Lorien Legacies in Minecraft! It adds legacies such as Avex and Lumen, as well as telekinesis and a host of other awesome abilities and items.

## Legacies

### Active legacies
| Legacy      | Level 1                   | Level 2                       | Level 3                                   | Level 4             | Complete |
| ----------- | ------------------------- | ----------------------------- | ----------------------------------------- | ------------------- | -------- |
| Lumen       | Flammable hands           | Fire and lava resistance      | Fireballs                                 | Fire wave           | ✅ |
| Glacen      | Frost touch               | Greater range                 | Ice bolt                                  | Frost wave          | ✅ |
| Avex        | Flight                    | Greater speed                 | Flight instinct negating all fall damage  | Air updraft         | ✅ |
| Submari     | Water breathing           | Water vision                  | Greater speed                             | Water vortex        | ✅ |
| Pondus      | Water walking             | Lava walking                  | Air walking                               | Obsidian skin       | ✅ |
| Telekinesis | Levitate mobs             | Launch mobs                   | Move blocks                               | Move players        | ✅ |

### Passive legacies
| Legacy      | Effect (scales with level) | Complete |
| ----------- | -------------------------- | ---------|
| Fortem      | Stength                    | ✅ |
| Novis       | Invisibility               | ✅ |
| Regeneras   | Stength                    | ✅ |
| Accelix     | Swiftness                  | ✅ |
| Noxen       | Night vision               | ✅ |


### Stamina and XP
You gain XP any time a legacy is active, most often by using stamina (unless in creative mode, where stamina does not apply, in which case you only gain XP). The exact rate at which the player gains and loses XP and stamina can be changed in the config.

## Items and blocks
* Loralite / Loralite Ore - ore used in lorien recipes
* Loralite block - decorative block made from loralite
* Loric stone - used to grant players more legacies
* Loric book - given by the Garde to all Lorien, containing descriptions of all given legacies, your current level, and an in-game FAQ.
* Concealed knife - a weak, concealed blade (part of WIP Loric items)

## Commands
* /legacies - displays legacies the player has
* /regiveLegacies - gives a player new legacies (and takes away the old ones)
* /giveLegacy <legacy> - gives player a legacy
* /legacyLevels <legacy> - displays levels for legacy
* /maxLegacyLevels - maxes out levels of all current legacies

## Keybinds
* Some legacies are always active, like Lumen. Others, like Avex, can be toggled. The default key for this is left alt.
* Some legacies have abilities. The ability menu can be brought up with Z.
* The last used legacy can be toggled by holding down ` (the key above tab).

## Legacy generation
The mod is balanced so that each player will have a different experience. Legacies are randomly assigned to each player, and the exact process is detailed below.

* Upon joining a new world, each player has a chance to recieve legacies. By default this is 100%, though this can be changed in the config, as can all other values used in legacy generation. 
* If legacies are to be recieved, the player is given a starting number of "legacy points" (5 by default).
* Each legacy (excluding Telekinesis) is placed into a hat X times (where X corresponds to the liklihood of recieving that legacy).
* Legacies are drawn from the hat, and each has an associated "cost", which is taken from the player's running legacy points.
* When all points have been exhausted, the player is left with what is usually 3-5 legacies.
* The player is then given telekinesis.

### Default legacy rarities
| Legacy      | Liklihood (times put into the hat) | Cost (in terms of "legacy points") |
| ----------- | ---------------------------------- | ---------------------------------- | 
| Lumen       | 1                                  | 3                                  |
| Glacen      | 1                                  | 3                                  |
| Avex        | 1                                  | 3                                  |
| Submari     | 2                                  | 3                                  |
| Pondus      | 2                                  | 3                                  |
| Telekinesis | N/A                                | N/A                                |
| Fortem      | 3                                  | 1                                  |
| Novis       | 3                                  | 1                                  |
| Regeneras   | 3                                  | 1                                  |
| Accelix     | 3                                  | 1                                  |
| Noxen       | 3                                  | 1                                  |

## Project roadmap

### Initial release
* Set of 11 legacies ✔️
* Loralite Ore and loralite ✔️
* Some lorien items ✔️

### Release two
* Second batch of legacies ❌
* Player stamina progression ❌
* Other lorien items, particularly from chests ❌
* Lorien mobs ❌
* Mogodorian armour and blasters ❌

### Release three
* Third batch of legacies ❌
* Garde and Cepan ❌
* Chimaera ❌
* Generated structures (eg mogodorian bases) ❌

### Other possible features
* Garde's various idiosyncrasies (eg Nine's super hearing)

## Authors
* [Kerbo](https://github.com/Kerbo)
* [Blue20Boy17](https://github.com/Blue20Boy17)
* [tebreca](https://github.com/Tebreca)
* [Phantom](https://github.com/PhantomTheDev)
* [5](https://github.com/walter-afk)

## Contact us
Join [our discord](https://discord.gg/rADuzGsGdY)!
