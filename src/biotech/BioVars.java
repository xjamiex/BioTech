package biotech;

import arc.util.Nullable;
import biotech.graphics.ShockwaveRenderer;
import biotech.ui.*;
import mindustry.Vars;
import mindustry.gen.Unit;

public class BioVars {
    public static ShomeretCutscene shomeretCutscene = new ShomeretCutscene();
    public static BioBossBarFragment bossBar = new BioBossBarFragment();

    public static ShockwaveRenderer shockwaveRenderer;


    public static void load(){
        bossBar.load(Vars.ui.hudGroup);
        shomeretCutscene.build(Vars.ui.hudGroup);

        shockwaveRenderer = new ShockwaveRenderer();
    }

    public static void postInit() {
        bossBar.build();
    }
}
