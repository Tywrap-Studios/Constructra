---
title: Getting Started
tags:
  - User Guides
  - Has pre-release info
---
## Installation
### Dependencies
To install Constructra, you need to have {==Fabric Loader==} installed. You can download it from [here](https://fabricmc.net/use/).
On said page, there is also a download link to the {==Fabric API==}, which is also required for Constructra to run.

At the moment, another API install is needed, {==BlossomBridge API 1.1.1==} or higher, as it's not bundled with the mod's jar. You can get it [here](https://modrinth.com/mod/blossombridge/versions).

### Getting a Mod Jar file
=== "Downloading"
    Currently, Constructra is not publicly downloadable on any mod hosting platform, or on GitHub releases, so you will have to get it manually yourself.
=== "Building"
    You can manually build the mod's jar by cloning the repository and running `./gradlew build` in the root directory of the project. Your jar will be in a folder called `build/libs`
    !!! warning
        If you're unfamiliar with Gradle and how to build jars manually, check out the other options.
=== "GitHub Actions"
    1. On the main page of the Repository, click "Actions" :octicons-play-16: in the navigation bar on top.
    2. On the left hand side, there is a list of Workflows :octicons-workflow-16:, click the one named `build`.
    3. Click into the top {==NON-FAILED==} action.
        1. You may filter by branch :octicons-git-branch-16: or release tag :octicons-tag-16: using the filters :octicons-filter-16: on the right side, or target a specific build in the list. This may be handy if you don't want to end up with an in-dev, unstable or broken version.
    4. Scroll down until you see a text named "Artifacts", after you click this a zip file containing the main jar and the sources jar will be downloaded onto your machine. Extract these jars and use the one that is not affixed with `-sources.jar`.

### Finalizing
After you've properly downloaded and setup Fabric Loader, input all the files into your `mods` folder, and start the game.