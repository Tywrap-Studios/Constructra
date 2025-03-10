---
title: Getting Started
tags:
  - Developer Guides
  - Addons
---
!!! tip "Status"
    This mod is not yet stable enough to fully support addons.  
    It is suggested to wait for a full release.

!!! note
    Some parts of the API need resources from outside its package, you will need the bare content mod in order to make it properly function.

Hello fellow developer, welcome to Constructra!

In order to make an addon at this moment, you will have to do a lot of stuff manually, as the mod and its sources are not yet officially released.

## Importing into your IDE[^1]
[^1]: :fontawesome-solid-code: Integrated Development Environment, like IntelliJ IDEA or Eclipse.
### Maven (Recommended)
You can get the Constructra sources from the jitpack maven repository.
=== "Groovy"
    ```groovy title="build.gradle",hl_lines="2 9"
    repositories {
	    maven { url = 'https://jitpack.io' }
    }

    dependencies {
        ...

	    // Constructra
	    modImplementation "com.github.Tywrap-Studios:Constructra:${project.constructra_version}"
    }
    ```

### Local files
Firstly, obtain a `constructra-<version>-sources.jar` from any of the following sources:
=== "Platform"
    Currently, Constructra is not publicly downloadable on any mod hosting platform, or on GitHub releases, so you will have to get it manually yourself.
=== "Building"
    You can do this by cloning the repository and running `./gradlew build` in the root directory of the project. Your sources jar will be in a folder called `build/libs`.
=== "GitHub Actions"
    1. On the main page of the Repository, click "Actions" in the navigation bar on top.
    2. On the left hand side, there is a list of Workflows/Actions, click the one named `build`.
    3. Click into the top {==NON-FAILED==} action.
        1. You may filter by branch or release tag using the filters on the right side, or target a specific build in the list. This may be handy if you don't want to end up with an in-dev, unstable or broken version.
    4. Scroll down until you see a text named "Artifacts", after you click this a zip file containing the main jar and the sources jar will be downloaded onto your machine. Extract these jars and use the one that is affixed with `-sources.jar`.
After obtaining said file, it is best to make a new directory in your project called `libs/`, and put the jar inside of it.

Now you can manually add it to your repositories and dependencies:
=== "Groovy"
    ```groovy title="build.gradle",hl_lines="5"
    dependencies {
        ...

	    // Constructra
	    modImplementation files("libs/constructra-${project.constructra_version}-sources.jar")
    }
    ```

Finally, add the following to your `gradle.properties` file:
```properties
constructra_version=x.y.z
```
And replace `x.y.z` with your major, minor and patch version, e.g. {==1.0.0==}.
??? tip "Tip: Local versioning"
    If you are using a local jar, check if the version in the file name corresponds with the version you're trying to use.