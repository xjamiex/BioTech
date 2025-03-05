package biotech.ui;

import arc.func.*;
import arc.graphics.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import biotech.world.blocks.enviroment.BiologicalStaticSpawner.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.*;

public class BioSpawnerDialog extends BaseDialog {
    public Seq<SpawnPlan> spawnPlans;
    public Table spawnTable = new Table();
    public Cell<ScrollPane> canvas;

    int size = 60;

    public BioSpawnerDialog() {
        super("spawner");
        clearChildren();
        addCloseListener();
        makeButtonOverlay();

        setup();

        shown(this::setup);
        onResize(this::setup);
    }


    public Dialog show(SpawnPlan[] spawnPlans){
        if(spawnPlans != null)this.spawnPlans = Seq.with(spawnPlans);
        else this.spawnPlans = new Seq<>();
        return super.show();
    }

    private void setup(){
        buttons.clearChildren();
        buttons.defaults().size(160f, 64f);
        buttons.button("@add", Icon.add, () -> {
            SpawnPlan p = new SpawnPlan(UnitTypes.dagger, 700, 1, StatusEffects.none, 0);
            spawnPlans.add(p);
            rebuild();
        }).name("add");
        addCloseButton();

        rebuild();
    }

    public void rebuild(){

        cont.top();
        cont.clear();
        cont.defaults();
        cont.table(a -> canvas = a.pane(spawnTable).size(a.getWidth(), a.getWidth()).scrollX(false).growX());


        spawnTable.clear();
        spawnTable.defaults();


        if(spawnPlans !=null && spawnPlans.size > 0){
            for(SpawnPlan plan : spawnPlans){
                spawnTable.table(Tex.button, s -> {
                    s.table( Tex.underline, e-> {
                        Image img =new Image(Icon.cancel);
                        img.clicked(() ->{
                            spawnPlans.remove(plan);
                            rebuild();
                        });
                        e.add(img).size(size / 2f ).touchable(Touchable.enabled).scaling(Scaling.bounded).padLeft(2f);

                        Image cpy =new Image(Icon.copySmall);
                        cpy.clicked(() ->{
                            spawnPlans.add(SpawnPlan.copy(plan));
                            rebuild();
                        });
                        e.add(cpy).size(size / 2f ).touchable(Touchable.enabled).scaling(Scaling.bounded);
                    }).growX().row();
                    s.table( u -> {
                        Image img =new Image(plan.unit.uiIcon);
                        img.clicked(() -> unitPicker(plan));
                        u.add(img).scaling(Scaling.bounded).size(size * 0.85f).touchable(Touchable.enabled).padRight(0f).left();

                        Label txt = new Label(Iconc.pencil +" " +plan.unit.localizedName);
                        txt.setWrap(true);
                        txt.clicked(() -> unitPicker(plan));
                        txt.fillParent = true;
                        u.add(txt).padLeft(0f).touchable(Touchable.enabled).grow();
                    }).growX();
                    s.row();
                    s.table( e-> {
                        Image img = new Image((plan.effect == StatusEffects.none) ? Icon.none.getRegion() : plan.effect.uiIcon);
                        img.clicked(() -> effectPicker(plan));
                        e.add(img).size(size * 0.85f).scaling(Scaling.bounded);

                        number(":", i -> plan.effectTime = i,() -> plan.effectTime, e);
                    }).growX().row();
                    s.table( e-> {
                        numberi("amount:", i -> plan.amount = i,() -> plan.amount, e);
                        numberi("time:", i -> plan.time = i,() -> plan.time, e);
                    }).growX();
                }).pad(1.5f).growX().width(canvas.maxWidth()).row();
            }
        } else spawnTable.table(t -> t.label(() -> "@empty").growX().center().style(Styles.outlineLabel).pad(20f)).width(canvas.maxWidth());


        shouldPause = true;

    }

    void effectPicker(SpawnPlan plan){
        BaseDialog dialog = new BaseDialog("@waves.edit");
        dialog.addCloseButton();
        dialog.setFillParent(false);

        dialog.cont.pane( t -> {
            t.defaults().size(280f, 64f).pad(2f);
            int c = 0;
            for(StatusEffect item : content.statusEffects()){
                t.button(new TextureRegionDrawable((item == StatusEffects.none) ? Icon.none.getRegion() : item.uiIcon), Styles.flati, iconLarge, () -> {
                    plan.effect = item;
                    rebuild();
                    dialog.hide();
                }).size(iconXLarge).scaling(Scaling.bounded).tooltip(item.localizedName);

                if(++c % 6 == 0) t.row();
            }
        });

        dialog.show();
    }

    void unitPicker(SpawnPlan plan){
        BaseDialog dialog = new BaseDialog("@waves.edit");
        dialog.addCloseButton();
        dialog.setFillParent(false);
        dialog.cont.pane( t -> {
            t.defaults().size(280f, 64f).pad(2f);
            int c = 0;
            for(UnitType item : content.units()){
                if(!item.unlockedNow() || item.isHidden() || !item.logicControllable) continue;

                t.button(new TextureRegionDrawable(item.uiIcon), Styles.flati, iconLarge, () -> {
                    plan.unit = item;
                    rebuild();
                    dialog.hide();
                }).size(iconXLarge).scaling(Scaling.bounded).tooltip(item.localizedName);

                if(++c % 6 == 0) t.row();
            }
        });
        dialog.show();
    }

    void numberi(String text, Intc cons, Intp prov, Table main){
        numberi(text, cons, prov, 0, Integer.MAX_VALUE, main);
    }

    void numberi(String text, Intc cons, Intp prov, int min, int max, Table main){
        main.table(t -> {
            t.left();
            t.add(text).left().padRight(5);
            t.field((prov.get()) + "", s -> cons.get(Strings.parseInt(s)))
            .valid(f -> Strings.parseInt(f) >= min && Strings.parseInt(f) <= max).width(130f).left();
        }).padTop(0).row();
    }


    void number(String text, Floatc cons, Floatp prov,  Table main){
        number(text, cons, prov, 0f, Float.MAX_VALUE, main);
    }

    void number(String text, Floatc cons, Floatp prov, float min, float max, Table main){
        main.table(t -> {
            t.left();
            t.add(text).left().padRight(5);
            t.field((prov.get()) + "", s -> cons.get(Strings.parseFloat(s)))
            .valid(f -> Strings.parseFloat(f) >= min && Strings.parseFloat(f) <= max).width(130f).left();
        }).padTop(0).row();
    }


}
