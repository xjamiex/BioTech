package biotech;

import arc.*;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.*;
import biotech.content.*;
import biotech.ui.BioSpawnerDialog;
import biotech.ui.BioUI;
import biotech.ui.ButtonPref;
import biotech.world.blocks.enviroment.BiologicalStaticSpawner;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.Planets;
import mindustry.content.TechTree;
import mindustry.core.World;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import mindustry.game.Saves;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.mod.*;
import mindustry.type.UnitType;
import mindustry.ui.dialogs.*;
import mindustry.world.Tile;
import biotech.gen.EntityRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static arc.Core.bundle;
import static arc.Core.settings;
import static mindustry.Vars.*;

public class BioTech extends Mod {

    public static Music mothersWail;

    public BioTech() {
        Events.on(EventType.SectorCaptureEvent.class, event -> {
            for (Building building : Groups.build) {
                if (building instanceof BiologicalStaticSpawner.BiologicalStaticSpawnerBuild build) {
                    Tile tile = build.tile;
                    tile.remove();
                    BioFx.immuneSpawnerExplode.at(tile);
                }
            }
        });

        Events.on(EventType.UnitSpawnEvent.class, event -> {
            for (Unit unit : Groups.unit) {
                if (unit.type == BioUnits.ima) {
                    BioVars.imaCutscene.enabled = true;
                    break;
                }
            }
        });

        Events.on(EventType.WorldLoadEvent.class, you -> BioVars.postInit());
    }

    @Override
    public void loadContent() {
        EntityRegistry.register();

        BioItems.load();
        BioLiquids.load();
        BioStatusEffects.load();
        BioUnits.load();
        BioBlocks.load();
        BioPlanets.load();
        BioSectorPresets.load();
        BioTechTree.load();
    }

    @Override
    public void init() {
        super.init();
        BioTeams.load();
        BioSounds.load();
        loadSettings();

        loadMusic();

        BioUI.load();
    }

    public static void loadMusic() {
        Core.assets.load("soundtracks/mothers-wail.ogg", arc.audio.Music.class).loaded = a -> mothersWail = a;
    }


    void loadSettings() {
        ui.settings.addCategory(bundle.get("setting.biotech-category"), Icon.settings, t -> {

            t.pref(new ButtonPref(bundle.get("setting.biotech-clear-tech-tree"), Icon.trash,() -> {
                ui.showConfirm("@confirm", bundle.get("setting.biotech-clear-tech-tree.confirm"), () -> {
                    BioPlanets.andori.techTree.reset();
                    for(TechTree.TechNode node : BioPlanets.andori.techTree.children){
                        node.reset();
                    }
                    content.each(c -> {
                        if(c instanceof UnlockableContent u && c.minfo != null && c.minfo.mod != null && c.minfo.mod.name.equals("biotech")){
                            u.clearUnlock();
                        }
                    });
                    settings.remove("unlocks");
                });
            }));
            t.pref(new ButtonPref(bundle.get("setting.biotech-clear-campaign"),Icon.trash,() -> {
                ui.showConfirm("@confirm", bundle.get("setting.biotech-clear-campaign.confirm"), () -> {
                    Seq<Saves.SaveSlot> toDelete = Seq.with();
                    control.saves.getSaveSlots().each(s -> {
                        if(s.getSector() == null) return;
                        if(s.getSector().planet == BioPlanets.andori) {
                            toDelete.add(s);
                            Log.info("Deleted Biotech sector: "+ s.getSector().id+ (s.getSector().preset != null ? " "+s.getSector().preset.localizedName : ""));
                        }
                    });
                    toDelete.each(Saves.SaveSlot::delete);
                    ui.showInfoOnHidden(bundle.get("setting.biotech-clear-campaign-close.confirm"), () -> {
                        Core.app.exit();
                    });
                });
            }));
        });
    }
}
