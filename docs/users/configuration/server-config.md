---
title: Server Config
tags:
  - Configuration
---

??? info annotate "Reference Sheet"
    ### `"value_name": <default_value>`
    - Use: Explains use case.
    - Type: States of what JSON Element Type this value is.
    - May specify a Set (1) or a Range (2).
    - Predicate: If this value requires something else to function, it'll be stated here.
    - Default: `<default_value>`.

1.  `Type` from Set [`choice 1`, `choice 2`,...]
2.  `Type` in Range, followed by an equation. The variable `v` is the value to set.

### `:::json5 "config_version": "1.0"`
- Use: Internal use only, do not change this or your options may be arbitrarily reset.
- Type: `String`
- Default: May Vary
### `:::json5 "resources": {`
#### `:::json5 "default_resource_purity": "RANDOM"`
- Use: Defines what every `ResourceNode`'s purity should be.
- Type: `String` from Set [`NONE`, `IMPURE`, `NORMAL`, `PURE`, `RANDOM`]
- Default: `:::json5 "RANDOM"`
#### `:::json5 "do_obstructions": true`
- Use: Defines whether Nodes can be obstructed. When set to false, Nodes don't need to be destructed first in order to be automatically harvested from.
- Type: `Boolean`
- Default: `:::json5 true`
#### `:::json5 "consecutive_harvests_for_destruction": 3`
- Use: The amount of times you have to consecutively harvest from a Node to be able to destruct it.
- Type: `Integer`
- Default: `:::json5 3`
#### `:::json5 "purity_modifiers": {`
##### `:::json5 "does_purity_affect_rate": true`
- Use: Defines whether the Purity of a `ResourceNode` affects at which rate the resource is given. If false is chosen, we will use the rate of a `NORMAL` node, so feel free to tweak that value. `ResourceNode`s will still have their Purity displayed!
- Type: `Boolean`
- Default: `:::json5 true`
##### `:::json5 "pure": double`
- Use: The Rate at which a PURE node should give its Resource.
- Type: `Double` (in seconds)
- Default: Subject To Change
##### `:::json5 "normal": double`
- Use: The Rate at which a NORMAL node should give its Resource.
- Type: `Double` (in seconds)
- Default: Subject To Change
##### `:::json5 "impure": double`
- Use: The Rate at which an IMPURE node should give its Resource.
- Type: `Double` (in seconds)
- Default: Subject To Change
#### `:::json5 }`
#### `:::json5 "visualize_centres": false`
- Use: Defines whether to display certain visual hints as to what block is the exact `ResourceNode` Centre. e.g. through the use of particles.
- Type: `Boolean`
- Default: `:::json5 false`
### `:::json5 }`
### `:::json5 "commands": {`
#### `:::json5 "command_alias": "ca"`
- Use: The command alias for the Constructra command. Change this if you're having compatibility issues with other mods.
- Type: `String`
- Default: `:::json5 "ca"`
#### `:::json5 "perm_lvl_nodes": 2`
- Use: The permission level required to use the Node commands in general.
- Type: `Integer`
- Default: `:::json5 2`
#### `:::json5 "perm_lvl_nodes_removal": 4`
!!! danger
    This is a dangerous permission to give as it can break the entire gameplay due to missing key resource gathering!
- Use: The permission level required to remove Nodes from the world. (Both nodes flush and nodes purge.)
- Type: `Integer`
- Default: `:::json5 4`
#### `:::json5 "perm_lvl_reload": 2`
- Use: The permission level required to reload the Constructra config.
- Type: `Integer`
- Default: `:::json5 2`
### `:::json5 }`
### `:::json5 "util_config": {`
#### `:::json5 "debug_mode": false`
!!! warning
    This may occasionally spam your logs full of junk that isn't relevant.  
    While we tried to leave tick-based debugging out of the public releases, other, colloquially perceived as random messages may show up a lot of times recursively.  
    Only enable this if you actually need it! You have been warned.
- Use: Defines whether to display debug information in the console.
- Type: `Boolean`
- Default: `:::json5 false`
#### `:::json5 "suppress_warns": false`
!!! warning
    Although completely removing warns and errors from your logs is fair, we still do not recommend using this feature.
- Use: Defines whether to suppress all warnings from this mod.
- Type: `Boolean`
- Default: `:::json5 false`
### `:::json5 }`


