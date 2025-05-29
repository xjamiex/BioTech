package biotech;

import arc.Events;
import arc.util.Log;
import arc.util.Reflect;
import biotech.content.BioFx;
import biotech.ui.BioResearchDialog;
import biotech.world.blocks.enviroment.BiologicalStaticSpawner;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.ui.fragments.MenuFragment;
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
                    BioFx.immuneSpawnerExplode.at(tile);
                }
            }
        });

        Events.on(EventType.WorldLoadEvent.class, you -> BioVars.postInit());
    }
}
