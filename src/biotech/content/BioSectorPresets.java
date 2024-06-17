package biotech.content;

import mindustry.content.SectorPresets;
import mindustry.content.Weathers;
import mindustry.type.SectorPreset;
import mindustry.type.Weather;

public class BioSectorPresets {
    public static SectorPreset
        ankle, crus;

    public static void load(){
        ankle = new SectorPreset("ankle", BioPlanets.andori, 1){{
            alwaysUnlocked = true;
            difficulty = 1;
        }};

        crus = new SectorPreset("crus",BioPlanets.andori, 34){{
            alwaysUnlocked = false;
        }};
    }
}
