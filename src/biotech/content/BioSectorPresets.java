package biotech.content;

import mindustry.content.SectorPresets;
import mindustry.type.SectorPreset;

public class BioSectorPresets {
    public static SectorPreset
        ankle;

    public static void load(){
        ankle = new SectorPreset("ankle", BioPlanets.andori, 15){{
            alwaysUnlocked = true;
            addStartingItems = true;
            difficulty = 2;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 3f;
        }};
    }
}
