package biotech.ui;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.struct.Seq;
import arc.util.*;
import biotech.BioVars;
import biotech.content.BioUnits;
import biotech.type.unit.ShomeretUnitType;
import mindustry.*;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.ui.*;


public class BioBossBarFragment {
    public Table bossBar = new Table();

    public boolean hide(){
        return !Core.settings.getBool("biotech-bossbar-hides-menu", true);
    }

    public void load(Group parent){
        Boolp prevHud =  Vars.ui.hudGroup.find("waves").visibility, prevMap =  Vars.ui.hudGroup.find("minimap/position").visibility;
        if(!Vars.mobile){ //dont softlock mobile players
            if(prevHud == null) prevHud = () -> false;
            Boolp finalprevHud = prevHud;
            parent.find("waves").visible( () ->  hide() && finalprevHud.get());
        }

        if(prevMap == null) prevMap = () -> false;
        Boolp finalprevMap = prevMap;
        parent.find("minimap/position").visible( () ->  hide() && finalprevMap.get());

        parent.fill( p -> {
            p.name = "biotech-bossbar";
            p.align(Align.top).setFillParent(true);
            p.add(bossBar).visible(true);
        });
    }

    public void build(){
        bossBar.clear();


        Bar bar = new Bar(
            () -> "A-16 'SHOMERET'",
            () -> Pal.heal,
            () -> {
                float health = 0;
                Seq<Unit> bosses =  Groups.unit.copy().retainAll(unit -> unit.type instanceof ShomeretUnitType);
                for(Unit u : bosses) health += u.healthf();
                health= health /bosses.size;
                return health;
            }
        ){
            @Override
            public void draw(){
                //replace this with ur gorry image -rushie
                super.draw();
            }
            {
                visible(() -> Groups.unit.copy().contains(u -> u.type instanceof ShomeretUnitType));
            }
        };
        bossBar.add(bar).minWidth(Core.graphics.getWidth() * 0.95f).update(a -> {
        }).minHeight(50f).pad(5f);
    }
}
