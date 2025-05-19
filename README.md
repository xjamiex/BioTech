# `MindustryModTemplate`

[Mindustry](https://github.com/Anuken/Mindustry) Java mod template, otherwise known as JAR-modding, complete with [`EntityAnno`](https://github.com/GglLfr/EntityAnno) and syntax downgrader integration, works for both Android and PC.

## Using

Before going into using this template, be aware that a fair amount of Java knowledge and Git *(GitHub Desktop is fine, but `git` CLI is a million times better)* is **highly beneficial**. Going in blind isn't impossible, but you'll face a lot of problems. Not that people on [the Discord](https://discord.gg/mindustry) won't help, though, so be communicative!

1. Install JDK 17 or higher. Plain or terminal-based code editors are **completely *not* recommended!** Use an IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/download/); there are free Community Edition releases available, just scroll down a bit.
2. Click the `Use this template` button and create your repository.
3. Clone a local copy of the repository.

> [!IMPORTANT]
> A **local copy** is *not* a mere ZIP archive you obtain by downloading. This is where the Git knowledge comes to play. If you have GitHub CLI or GitHub Desktop installed, the site should provide instructions on how to use either, under the `<> Code` menu.
>
> `Download ZIP` is **not** a proper way to clone your repository.

4. Refactor namings to your preferences. The template is designed in such a way that this step should only require you to modify:
   - `gradle.properties`, the "Project configurations" section. For the "package" properties, if you don't know what you're doing, simply just change `template` to your preferred mod root package naming *(e.g. `mymod`, or `confictura` if your mod name is "confictura")*.
   - `mod.json`, which is the entire metadata of your mod.
   - `src/` folder and its contents, which is where your Java source files are stored. Rename folders, package, and class names as you prefer. Note that the main mod class' full name *(package + class)* must correspond to the `main` property in `mod.json`.
   - `.github/workflows/ci.yml`, which is the automated Continuous Integration that runs on your GitHub repository everytime you push a commit. This automates cross-platform builds which you might find useful. You should only edit the last 2 lines about `name` and `path`.

> [!TIP]
> There's no `name` property in this template's `mod.json`. That property is automatically filled up when building.

   Here's an example of a properly configured mod base from the template, *assuming
   "confictura" as the name*:
   ```mermaid
   ---
   title: Project Hierarchy
   ---
   graph LR;
   %%{init:{'flowchart':{'nodeSpacing': 10, 'rankSpacing': 10}}}%%;
   
   classDef folder fill:#465768,stroke:#bdcedf;
   classDef file fill:#468868,stroke:#bdffdf;

   root{{"/"}};
   github{{".github/"}};
   workflows{{"workflows/"}};
   assets{{"assets/"}};
   gradle{{"gradle/"}};
   wrapper{{"wrapper/"}};
   src{{"src/"}};
   confictura{{"confictura/"}};
   class root,github,workflows,assets,gradle,wrapper,src,confictura folder;

   ci(["ci.yml"]);
   wrapper-jar(["gradle-wrapper.jar"]);
   wrapper-prop(["gradle-wrapper.properties"]);
   main(["ConficturaMod.java"]);
   ignore([".gitignore"]);
   readme(["README.md"]);
   build(["build.gradle.kts"]);
   prop(["gradle.properties"]);
   wrapper-unix(["gradlew"]);
   wrapper-windows(["gradlew.bat"]);
   icon(["icon.png"]);
   meta(["mod.json"]);
   settings(["settings.gradle.kts"]);
   class ci,main,prop,meta file;

   root-->github-->workflows-->ci;
   root-->assets;
   root-->gradle-->wrapper-->wrapper-jar & wrapper-prop;
   root-->src-->confictura-->main;
   root-->ignore & readme & build & prop & wrapper-unix & wrapper-windows & icon & meta & settings;
   ```

   Here are the changes made to files highlighted above; red denotes removals, green denotes additions:
   `./github/workflows/ci.yml`:
   ```diff
   ...
          uses: actions/upload-artifact@v4
          with:
   -        name: ModTemplate (zipped)
   +        name: Confictura (zipped)
   -        path: build/libs/ModTemplate.jar
   +        path: build/libs/Confictura.jar
   ```

   `src/confictura/ConficturaMod.java`:
   ```diff
   - package template;
   + package confictura;

     import mindustry.mod.*;
   - import template.gen.*;
   + import confictura.gen.*;

   - public class ModTemplate extends Mod{
   + public class ConficturaMod extends Mod{
         @Override
         public void loadContent(){
             EntityRegistry.register();
         }
     }
   ```

   `gradle.properties`:
   ```diff
     ##### Project configurations.
     # The mod's internal name, corresponds to `name` in `mod.json`.
   - modName = mod-template
   + modName = confictura
     # The mod's fetched entity sources package.
   - modFetch = template.fetched
   + modFetch = confictura.fetched
     # The mod's input entity sources package.
   - modGenSrc = template.entities.comp
   + modGenSrc = confictura.entities.comp
     # The mod's generated sources package.
   - modGen = template.gen
   + modGen = confictura.gen
     # The mod's JAR file name. Desktop build is suffixed with `Desktop`.
   - modArtifact = ModTemplate
   + modArtifact = Confictura
   ...
   ```

   `mod.json`:
   ```diff
     {
   -     "displayName": "Mod Template",
   +     "displayName": "Confictura",
   -     "description": "Mindustry Java mod template, complete with EntityAnno and syntax downgrader integration.",
   +     "description": "Dive into the past of a trauma-driven uprising.",
         "version": "1.0",
         "minGameVersion": "146",
         "author": "You",
         "java": true,
   -     "main": "template.ModTemplate"
   +     "main": "confictura.ConficturaMod"
     }
   ```

5. Put your asset files *(textures, sounds, music, etc.)* inside `assets/`. The contents of this folder are included in the built JARs, and should look similar to those of JSON and JS mods.
   ```mermaid
   graph TD;
   %%{init:{'flowchart':{'nodeSpacing': 10}}}%%;
   
   classDef folder fill:#465768,stroke:#bdcedf;

   assets{{"assets/"}};
   bundles{{"bundles/"}};
   maps{{"maps/"}};
   music{{"music/"}};
   shaders{{"shaders/"}};
   sounds{{"sounds/"}};
   sprites{{"sprites/"}};
   class assets,bundles,maps,music,shaders,sounds,sprites folder;

   assets-->bundles & maps & music & shaders & sounds & sprites;
   ```
   That's all! You can start hacking your way into modding now. Refer to the [**Building**](#building) section on how to build the JARs.

## Building

Mindustry Java mods are cross-platform, supporting PC (Windows, Mac, Linux) and Android. This section describes how to build the JARs for both PC and Android. Building these JARs are done through the usage of terminals: `cmd.exe` in Windows, Terminal in Mac, and if you're either on Linux or using a terminal emulator on Android such as Termux, you should already know what you're doing anyway. Following these steps should require basic terminal functionality such as `cd`.

### Desktop Build

Desktop builds are convenient for testing, but will obviously **not** work on Android, so never include this in your releases. Desktop JARs have `Desktop` suffixed to their name, e.g. `ModTemplateDesktop.jar`. Here's how you can build the mod:
1. Open your terminal, and `cd` to your local copy of the mod.
2. Ensure your internet connection on first or clean builds, as the project will try to fetch prerequisites from the internet.
3. Run `gradlew jar` *(replace `gradlew` with `./gradlew` on Mac/Linux)*. This should create a JAR inside `build/libs/` that you can copy over to the Mindustry mods folder to install it.
4. You can also then run `gradlew install` to automatically install the mod JAR, or even `gradlew jar install` to do both compiling and installing at once.

### Android Build

Android builds are automated on the CI hosted by GitHub Actions, so you should be able to just push a commit and wait for the CI to provide your build. If you still want to build locally, though, follow these steps.

#### Installing Android SDK
1. Install [Android SDK](https://developer.android.com/studio#command-line-tools-only), specifically the "**Command line tools only**" section. Download the tools that match your platform.
2. Unzip the Android SDK command line tools inside a folder; let's call it `AndroidSDK/` for now.
3. Inside this folder is a folder named `cmdline-tools/`. Put everything inside `cmdline-tools/` to a new folder named `latest/`, so that the folder structure looks like `AndroidSDK/cmdline-tools/latest/`.
4. Open your terminal, `cd` to the `latest/` folder.
5. Run `sdkmanager --install "platforms;android-35" "build-tools;35.0.0"`. These versions correspond to the `androidSdkVersion` and `androidBuildVersion` properties inside `gradle.properties`, which default to `35` and `35.0.0`, respectively.
6. Set environment variable `ANDROID_SDK_ROOT` as the full path to the `AndroidSDK/` folder you created, and restart your terminal to update the environments.

#### Building
1. Open your terminal, and `cd` to your local copy of the mod.
2. Run `gradlew dex`. This should create a cross-platform JAR inside `build/libs/` that isn't suffixed with `Desktop` that you can copy over to the Mindustry mods folder to install it.
3. You can then copy the resulting artifact to your phone's Mindustry mods folder in its data directory.

## Adding Dependencies

**Never** use `implementation` for Mindustry/Arc groups and their submodules. There's a reason they're `compileOnly`; they're only present in compilation and excluded from the final JARs, as on runtime they're resolved from the game instance itself. Other JAR-mod dependencies must also use `compileOnly`. Only ever use `implementation` for external Java libraries that must be bundled with your mod.

## License

The project is licensed under [GNU GPL v3](/LICENSE).
