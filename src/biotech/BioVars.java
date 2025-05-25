package biotech;

import mindustry.Vars;

public class BioVars {
     public static ShomeretUI shomeretUI = new ShomeretUI();

    public static void postInit() {
        shomeretUI.build(Vars.ui.hudGroup);
    }
}
