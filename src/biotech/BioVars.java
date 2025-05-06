package biotech;

import mindustry.Vars;

public class BioVars {
     public static ImaFragment imaCutscene = new ImaFragment();

    public static void postInit() {
        imaCutscene.build(Vars.ui.hudGroup);
    }
}
