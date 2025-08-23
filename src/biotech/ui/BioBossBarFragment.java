package biotech.ui;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
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
            p.add(bossBar).visible(true); //<- replace with the unit detection
        });
    }

    public void build(){
        bossBar.clear();;
        Bar bar = new Bar(
            () -> ">m< ow",
            () -> Pal.heal,
            () -> Vars.player.unit() != null ? Vars.player.unit().healthf() : 0f //<- replace this with the actually unit(s)
        ){
            @Override
            public void draw(){
                //replace this with ur gorry image -rushie
                super.draw();
            }
        };
        bossBar.add(bar).minWidth(Core.graphics.getWidth() * 0.85f).minHeight(50f).pad(5f);

    }
}
