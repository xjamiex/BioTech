package biotech.content;

import mindustry.content.SectorPresets;
import mindustry.content.Weathers;
import mindustry.type.SectorPreset;
import mindustry.type.Weather;

public class BioSectorPresets {
    public static SectorPreset
        ankle, crus, femur, putridum, pelvis;

    public static void load(){
        ankle = new SectorPreset("ankle", BioPlanets.andori, 1){{
            alwaysUnlocked = true;
            difficulty = 1;
        }};

        crus = new SectorPreset("crus",BioPlanets.andori, 3){{
            alwaysUnlocked = false;
            captureWave = 26;
            difficulty = 1;
        }};

        femur = new SectorPreset("femur", BioPlanets.andori, 53){{
            alwaysUnlocked = false;
            difficulty = 2;
            captureWave = 10;
        }};

        putridum = new SectorPreset("putridum", BioPlanets.andori, 86){{
            alwaysUnlocked = false;
            difficulty = 2;
        }};

        pelvis = new SectorPreset("pelvis", BioPlanets.andori, 71){{
            alwaysUnlocked = false;
            difficulty = 5;
        }};
    }
}
