package biotech;

import arc.Events;
import biotech.content.BioUnits;
import biotech.type.unit.*;
import biotech.ui.dialog.BioResearchDialog;
import biotech.world.blocks.enviroment.BiologicalStaticSpawner;
import mindustry.Vars;
import mindustry.game.*;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.world.Tile;

public class BioEventHandler {
    public static BioResearchDialog researchDialog;
    public static void load() {
        researchDialog = new BioResearchDialog();
    }

    public static void init() {
        Events.on(EventType.SectorCaptureEvent.class, event -> {
            for (Building building : Groups.build) {
                if (building instanceof BiologicalStaticSpawner.BiologicalStaticSpawnerBuild build) {
                    Tile tile = build.tile;
                    tile.remove();
                }
            }
        });

        Events.on(EventType.WaveEvent.class, event -> {
            if (Vars.state.rules.spawns.contains( w -> w.type instanceof ShomeretUnitType)) BioVars.shomeretCutscene.begin();
        });

        Events.on(EventType.WorldLoadEvent.class, you -> BioVars.postInit());
        Events.on(EventType.ClientLoadEvent.class, world -> BioVars.load());
    }
}
