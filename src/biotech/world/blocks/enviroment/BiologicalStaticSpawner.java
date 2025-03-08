package biotech.world.blocks.enviroment;

import arc.audio.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.io.*;
import biotech.ui.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.environment.*;

public class BiologicalStaticSpawner extends Prop {
    public static Effect spawnFx = Fx.none;
    public static Sound spawnSound = Sounds.none;

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

        config(SpawnPlan[].class, (BiologicalStaticSpawnerBuild build, SpawnPlan[] waves) -> build.plans = waves);
    }


    @Override
    public void setBars(){
        super.setBars();
    }

    public class BiologicalStaticSpawnerBuild extends Building {
        SpawnPlan[] plans;
        public float[] timers;

        @Override
        public void update(){
            super.update();

            if(plans == null || plans.length == 0) return;
            if(timers == null || timers.length != plans.length){
                timers = new float[plans.length];
                for(int i = 0; i < plans.length; i++) timers[i] = plans[i].time;
            }

            for(int i = 0; i < plans.length; i++){
                timers[i]--;
                if(timers[i] > 0) continue;

                SpawnPlan plan = plans[i];
                if(team.data().countType(plan.unit) >= team.data().unitCap) continue;

                timers[i] = plan.time;
                for(int j = 0; j < plan.amount; j++){
                    float fx = x + Mathf.random(-size, size), fy = y + Mathf.random(-size, size);
                    Unit u = plan.unit.spawn(team, fx, fy);
                    u.apply(plan.effect, plan.effectTime);

                    spawnFx.at(fy , fy, 0, u);
                }
                spawnSound.at(x, y);
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
            table.button(Icon.pencil, Styles.cleari, () -> BioUI.spawner.show(plans, p ->{
                configure(p);
                timers = null;
            } )).size(40);
        }

        @Override
        public byte version() {
            return 2;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            if( plans == null ) return;

            StringBuilder out = new StringBuilder();
            for(int i = 0; i < plans.length; i++){
                SpawnPlan plan = plans[i];
                if(timers != null) out.append(plan.dataToString(timers[i])).append(" ");
                else out.append(plan.dataToString(-1)).append(" ");
            }
            write.str(out.toString());
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision <= 0) return;
            //There's probably a better way to do this but this works so let it be damned -RushieWashie

            String in = read.str();
            String[] buffer = in.split(" ");
            Seq<SpawnPlan> sp = new Seq<>();
            Seq<Float> tim = new Seq<>();

            for(int i = 0; i < buffer.length; i++){
                String data = buffer[i];
                String[] plan = data.split(",");
                UnitType unit = Vars.content.unit(plan[0]);
                float time = Float.parseFloat(plan[1]);
                int amount = Integer.parseInt(plan[2]);
                StatusEffect sta = Vars.content.statusEffect(plan[3]);
                float dur = Float.parseFloat(plan[4]);
                if(revision >= 2) tim.add(Float.parseFloat(plan[5]));

                if(unit == null) continue;
                if(sta == null) sta = StatusEffects.none;
                sp.add(new SpawnPlan(unit, time, amount, sta, dur));
            }

            //prevents crash bc java/arc is being java/arc -RushieWashie
            SpawnPlan[] out = new SpawnPlan[buffer.length];
            for(int i = 0; i < buffer.length; i++) out[i] = sp.get(i);
            plans = out;

            float[] out2 = new float[tim.size];
            for(int i = 0; i < tim.size; i++) out2[i] = tim.get(i);
            timers = out2;

        }



    }


    public static class SpawnPlan{
        public UnitType unit;
        public int amount;
        public float time;
        public StatusEffect effect;
        public float effectTime;

        public SpawnPlan(UnitType unit, float time, int amount,  StatusEffect effect, float effectTime){
            this.unit = unit;
            this.time = time;
            this.amount = amount;
            this.effect = effect;
            this.effectTime = effectTime;
        }

        public SpawnPlan(){}

        public static
        SpawnPlan copy(SpawnPlan other){
            return new SpawnPlan(other.unit, other.time, other.amount, other.effect, other.effectTime);
        }

        public String dataToString(){
            return unit.name + "," + time + "," + amount + "," + effect.name + "," + effectTime;
        }

        public String dataToString(float timer){
            return unit.name + "," + time + "," + amount + "," + effect.name + "," + effectTime + "," + timer;
        }
    }
}