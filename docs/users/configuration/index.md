---
title: Configuring Constructra
tags:
  - User Guides
  - Configuration
---

If you happen to be stuck, or don't like a particular part of the gameplay, you are probably happy to hear this mod is highly configurable!

There are {==two==} types of config files:

- Server Side [:octicons-arrow-right-16:](server-config.md)
- Client Side [:octicons-arrow-right-16:](client-config.md)

Both of these files can be found in the `run/config` directory, and are json5 files, meaning they support comments, which aim to be as informative as possible

!!! info 
    Changes on either end will only affect that end, meaning that a changed made on the server won't affect the client side configs, and vice versa.

## Live examples:
If you want a live example of the configs being used, take a gander at the dev environment configurations!

<div class="annotate" markdown>
=== "Client"
    ```json5 title="constructra-client.json5",linenums="1"
    -8<- "run/config/constructra-client.json5"
    ```
=== "Server"
    ```json5 title="constructra-server.json5",linenums="1"
    -8<- "run/config/constructra-server.json5"
    ```
</div>
1.  [Click to View extensive info about this Config.](client-config.md)
2.  [Click to View extensive info about this Config.](server-config.md)