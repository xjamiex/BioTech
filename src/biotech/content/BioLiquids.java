package biotech.content;

import arc.graphics.Color;
import mindustry.type.Liquid;
import mindustry.world.Block;

public class BioLiquids {
    public static Liquid blood;

    public static void load() {
        blood = new Liquid("blood") {{
            heatCapacity = 0.4f;
            viscosity = 0.7f;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.9f);
            alwaysUnlocked = true;
            color = BioPal.bloodRedLight;
        }};
    }
}
