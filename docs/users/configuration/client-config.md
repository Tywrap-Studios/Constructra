---
title: Client Config
tags:
  - Configuration
  - Has pre-release info
draft: true
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

### `:::json5 "run_info": "None"`
- Use: This value is irrelevant, and shouldn't/won't be changed by the user.
- Type: `String`
- Default: `:::json5 "None"`
### `:::json5 "measure_units": "METRIC"`
- Use: Changes what unit system the mod will use to display measurements for the client.
- Type: `String` from Set: [`METRIC`, `FREEDOM`]
- Default `:::json5 "METRIC"`
### `:::json5 "play_time_safety": {`
#### `:::json5 "send_2020_reminders": false`
- Use: According to research, looking about 6 metres (20 feet) away from your screen for 20 seconds after looking at it for 20+ minutes can improve eyesight and eye performance. Enabling this setting sends you a reminder to look away for 20 seconds using in game toasts.
- Type: `Boolean`
- Default: `:::json5 false`
#### `:::json5 "interval": 20`
- Use: Defines the interval at which `:::json5 "send_2020_reminders"` will be sent. It is recommended to keep this at the default value or lower.
- Type: `Integer` in Range: `0<v<=60`
- Predicate: `:::json5 "send_2020_reminders"` must be `:::json5 true`
- Default: `:::json5 20`
#### `:::json5 "send_break_reminders": false`
- Use: Similar to `"send_2020_reminders"`, although now it sends toasts warning you about your concurrent playtime, hinting you to take a small break.
- Type: `Boolean`
- Default: `:::json5 false`
### `:::json5 }`
