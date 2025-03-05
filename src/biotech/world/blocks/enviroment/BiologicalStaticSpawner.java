package biotech.world.blocks.enviroment;

import arc.graphics.g2d.Draw;
import arc.scene.ui.layout.Table;
import arc.util.*;
import biotech.ui.BioUI;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.game.Waves;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.environment.Prop;

public class BiologicalStaticSpawner extends Prop {
    public static Effect effect = Fx.none;

    public BiologicalStaticSpawner(String name) {
        super(name);
        update = true;
        hasPower = false;
        hasItems = false;
        solid = false;
        configurable = true;
        clearOnDoubleTap = false;
        outputsPayload = false;
        rotate = false;
        commandable = false;
        ambientSound = Sounds.respawning;
        customShadow = true;
        destructible = targetable = false;
        breakable = false;

        config(Waves.class, (BiologicalStaticSpawnerBuild build, Waves waves) -> {

        });
    }


    @Override
    public void setBars(){
        super.setBars();
    }

    public class BiologicalStaticSpawnerBuild extends Building {
        SpawnPlan[] plans;
        int[] timers;

        @Override
        public void update(){
            super.update();

            if(plans == null || plans.length == 0) return;
            if(timers == null) timers = new int[plans.length];

            for(int i = 0; i < plans.length; i++){
                timers[i]--;
                if(timers[i] > 0) continue;

                SpawnPlan plan = plans[i];
                timers[i] = plan.time;
                Unit u = plan.unit.spawn(x, y);
                u.apply(plan.effect, plan.effectTime);

                effect.at(x, y);
            }


        }

        @Override
        public void draw() {
            Draw.alpha(0.5f);
            Draw.rect(customShadowRegion, x, y);
            Draw.alpha(1);
            Draw.rect(region, x, y);
        }

        @Override
        public boolean collide(Bullet other) {
            return false;
        }

        @Override
        public void configure(Object value) {
            super.configure(value);
        }

        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.pencil, Styles.cleari, () -> BioUI.spawner.show(plans)).size(40);
        }
    }


    public static class SpawnPlan{
        public UnitType unit;
        public int amount;
        public int time;
        public StatusEffect effect;
        public float effectTime;

        public SpawnPlan(UnitType unit, int time, int amount,  StatusEffect effect, float effectTime){
            this.unit = unit;
            this.time = time;
            this.amount = amount;
            this.effect = effect;
            this.effectTime = effectTime;
        }

        public SpawnPlan(){}

        public static SpawnPlan copy(SpawnPlan other){
            return new SpawnPlan(other.unit, other.time, other.amount, other.effect, other.effectTime);
        }
    }
}