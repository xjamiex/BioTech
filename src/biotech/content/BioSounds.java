package biotech.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class BioSounds {
    public static Sound
        fleshHit = new Sound(),
        motherDeath = new Sound(),
        fetusCries = new Sound(),
        wail = new Sound(),
        shootMedium = new Sound();

    public static void load(){
        fleshHit = loadSound("flesh-hit");
        wail = loadSound("wail");
        motherDeath = loadSound("mother-death");
        fetusCries = loadSound("fetus-cries");
        shootMedium = loadSound("shoot-medium");
    }

    private static Sound loadSound(String soundName){
        if(!Vars.headless) {
            String name = "sounds/" + soundName;
            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;

            return sound;

        } else {
            return new Sound();
        }
    }
}
