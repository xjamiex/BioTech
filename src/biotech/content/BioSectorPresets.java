package biotech.content;

import mindustry.content.SectorPresets;
import mindustry.type.SectorPreset;

public class BioSectorPresets {
    public static SectorPreset
        ankle;

    public static void load(){
        ankle = new SectorPreset("ankle", BioPlanets.andori, 1){{
            //>:C
            alwaysUnlocked = true;
            difficulty = 1;
        }};
    }
}
