package biotech.ui;

import arc.*;
import arc.func.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.geom.Rect;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.struct.Seq;
import arc.util.*;
import biotech.content.BioPal;
import biotech.type.unit.ShomeretUnitType;
import mindustry.*;
import mindustry.gen.Groups;
import mindustry.gen.Tex;
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
            parent.find("waves").visible( () ->  hide() && finalprevHud.get() && !Groups.unit.copy().contains(u -> u.type instanceof ShomeretUnitType));
        }

        if(prevMap == null) prevMap = () -> false;
        Boolp finalprevMap = prevMap;
        parent.find("minimap/position").visible( () ->  hide() && finalprevMap.get() && !Groups.unit.copy().contains(u -> u.type instanceof ShomeretUnitType));

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
            () -> Color.clear, this::getHealth
        ){
            final Rect scissor = new Rect();

            @Override
            public void draw(){
                Draw.color(Color.valueOf("2e0808"));
                Draw.rect(Core.atlas.find("biotech-shomeret-bar"), x + (this.width / 2), y + (height / 2), width, height);
                ScissorStack.push(scissor.set(this.x, this.y, this.width * getHealth(), this.height));
                Draw.color(Color.white);
                Draw.rect(Core.atlas.find("biotech-shomeret-bar"), x + (this.width / 2), y + (height / 2), width, height);
                ScissorStack.pop();
                Draw.rect(Core.atlas.find("biotech-shomeret-bar-outline"), x + (this.width / 2), y + (height / 2), width, height);
            }
            {
                visible(() -> Groups.unit.copy().contains(u -> u.type instanceof ShomeretUnitType));
            }
        };
        bossBar.add(bar).minWidth(Core.graphics.getWidth() * 0.80f).update(a -> {
        }).minHeight(100f).pad(30f);
    }

    public float getHealth() {
        float health = 0;
        Seq<Unit> bosses =  Groups.unit.copy().retainAll(unit -> unit.type instanceof ShomeretUnitType);
        for(Unit u : bosses) health += u.healthf();
        health = health / bosses.size;
        return health;
    }
}
