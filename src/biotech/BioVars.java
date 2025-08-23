package biotech;

import biotech.type.unit.*;
import biotech.ui.*;
import mindustry.Vars;

public class BioVars {
    public static ShomeretUI shomeretUI = new ShomeretUI();
    public static BioBossBarFragment bossBar = new BioBossBarFragment();

    public static void load(){
        bossBar.load(Vars.ui.hudGroup);
        shomeretUI.build(Vars.ui.hudGroup);
    }

    public static void postInit() {
        bossBar.build();
    }
}
