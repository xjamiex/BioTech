package biotech;

import arc.*;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.*;
import biotech.content.*;
import biotech.ui.BioStyles;
import biotech.ui.BioUI;
import biotech.ui.ButtonPref;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Saves;
import mindustry.gen.Icon;
import mindustry.mod.*;
import biotech.gen.EntityRegistry;

import static arc.Core.bundle;
import static arc.Core.settings;
import static mindustry.Vars.*;

public class BioTech extends Mod {

    public static Music mothersWail;

    public BioTech() {
        BioEventHandler.init();
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
        AndoriTechTree.load();
    }

    @Override
    public void init() {
        super.init();
        BioTeams.load();
        BioSounds.load();
        loadSettings();

        loadMusic();

        BioUI.load();

        if (!Vars.headless) {
            BioStyles.load();
            BioEventHandler.load();
        }
    }

    public static void loadMusic() {
        Core.assets.load("soundtracks/mothers-wail.ogg", arc.audio.Music.class).loaded = a -> mothersWail = a;
    }


    void loadSettings() {
        ui.settings.addCategory(bundle.get("setting.biotech-category"), Icon.settings, t -> {
            t.checkPref("biotech-bossbar-hides-menu", true);

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
