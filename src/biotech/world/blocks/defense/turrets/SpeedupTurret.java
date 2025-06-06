package biotech.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import biotech.content.BioPal;
import biotech.content.BioStats;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;

public class SpeedupTurret extends ItemTurret {

    public float maxSpeedupScl = 0.5f;
    public float speedupPerShoot = 0.075f;

    public float slowDownReloadTime = 150f;
    public float inaccuracyUp = 0f;

    public String chargeRegion;
    public Color chargeColor = Color.white;
    public float chargeLayer = Layer.effect;

    public SpeedupTurret(String name){
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("speedboost",
                (SpeedupTurretBuild entity) -> new Bar(
                        () -> Core.bundle.format("biotech.bar.speed-up", Strings.autoFixed((entity.speedupScl) * 100, 0)),
                        () -> BioPal.boneWhiteLight,
                        () -> entity.speedupScl / maxSpeedupScl
                )
        );
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(BioStats.fireRateMax, Strings.autoFixed((1 + maxSpeedupScl) * 100, 0) + "%");
    }

    public class SpeedupTurretBuild extends ItemTurretBuild{
        public float speedupScl = 0f;
        public float slowDownReload = 0f;
        public float overheat = 0;
        public boolean requireCompleteCooling = false;

        @Override
        public void updateTile(){
            updateCooldown();
            if(!requireCompleteCooling){
                super.updateTile();
            }else{
                forceCoolDown();
            }
        }

        @Override
        public void draw() {
            super.draw();
            if(chargeRegion != null){
                Drawf.additive(Core.atlas.find(chargeRegion), chargeColor.write(Tmp.c1).a(speedupScl), x + recoilOffset.x, y + recoilOffset.y, drawrot(), chargeLayer);
            }
        }

        public void updateCooldown(){
            if(slowDownReload > 0f){
                slowDownReload -= Time.delta;
            }else{
                speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
            }
        }

        public void forceCoolDown(){
            slowDownReload = 0;
            if(linearWarmup){
                shootWarmup = Mathf.approachDelta(shootWarmup, 0, shootWarmupSpeed);
            }else{
                shootWarmup = Mathf.lerpDelta(shootWarmup, 0, shootWarmupSpeed);
            }

            unit.tile(this);
            unit.rotation(rotation);
            unit.team(team);
            curRecoil = Mathf.approachDelta(curRecoil, 0, 1 / recoilTime);
            recoilOffset.trns(rotation, -Mathf.pow(curRecoil, recoilPow) * recoil);

            if(logicControlTime > 0){
                logicControlTime -= Time.delta;
            }

            if(overheat <= 0){
                overheat = 0;
                requireCompleteCooling = false;
            }
        }

        @Override
        protected void updateShooting(){
            if(reloadCounter >= reload){
                BulletType type = peekAmmo();

                shoot(type);

                reloadCounter = 0f;
            }else{
                reloadCounter += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            }
        }

        @Override
        protected void shoot(BulletType type){
            super.shoot(type);

            slowDownReload = slowDownReloadTime;
            if(speedupScl < maxSpeedupScl){
                speedupScl += speedupPerShoot;
            }else speedupScl = maxSpeedupScl;
        }

        @Override
        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover){
            super.bullet(type, xOffset, yOffset, angleOffset + Mathf.range(speedupScl * inaccuracyUp), mover);
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(overheat);
            write.f(slowDownReload);
            write.f(speedupScl);
            write.bool(requireCompleteCooling);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision == 2){
                overheat = read.f();
                slowDownReload = read.f();
                speedupScl = read.f();
                requireCompleteCooling = read.bool();
            }
        }
    }
}