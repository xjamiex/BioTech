package biotech;

import mindustry.Vars;

public class BioVars {
     public static ShomeretCutscene shomeretCutscene = new ShomeretCutscene();

    public static void postInit() {
        shomeretCutscene.build(Vars.ui.hudGroup);
    }
}
