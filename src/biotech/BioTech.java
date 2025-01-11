package biotech;

import arc.*;
import arc.struct.Seq;
import arc.util.*;
import biotech.content.*;
import biotech.ui.ButtonPref;
import mindustry.content.Planets;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.*;
import mindustry.game.Saves;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
import mindustry.gen.Icon;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import static arc.Core.bundle;
import static arc.Core.settings;
import static mindustry.Vars.*;

public class BioTech extends Mod{

    public BioTech(){
        //AAAAAA
    }

    @Override
    public void loadContent(){
        BioItems.load();
        BioLiquids.load();
        BioSounds.load();
        BioStatusEffects.load();
        BioUnits.load();
        BioBlocks.load();
        BioPlanets.load();
        BioSectorPresets.load();
        BioTechTree.load();

        Planets.erekir.hiddenItems.addAll(BioItems.andoriItems);
        Planets.serpulo.hiddenItems.addAll(BioItems.andoriItems);
    }

    @Override
    public void init() {
        super.init();
        BioTeams.load();
        loadSettings();
    }

    void loadSettings(){
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
