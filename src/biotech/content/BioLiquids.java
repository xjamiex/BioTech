package biotech.content;

import arc.graphics.Color;
import mindustry.type.Liquid;
import mindustry.world.Block;

public class BioLiquids {
    public static Liquid hemoFluid, plasmoid;

    public static void load() {
        hemoFluid = new Liquid("hemo-fluid") {{
            heatCapacity = 0.4f;
            viscosity = 0.7f;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.9f);
            alwaysUnlocked = true;
            color = BioPal.bloodRedLight;
        }};

        plasmoid = new Liquid("plasmoid") {{
            heatCapacity = 0.8f;
            viscosity = 0.5f;
            boilPoint = 0.4f;
            gasColor = Color.grays(0.5f);
            alwaysUnlocked = false;
            color = Color.valueOf("b09ede");
        }};
    }
}
