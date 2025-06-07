package biotech.type.unit;

import arc.graphics.Color;
import biotech.BioVars;
import biotech.content.BioPal;
import biotech.content.BioUnits;
import biotech.entities.part.BiologicalRegionPart;
import biotech.type.BiologicalUnitType;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.LegsUnit;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class ShomeretUnitType extends BiologicalUnitType {
    public static int state;
    public static UnitType ima;

    boolean cutsceneFinished = false;
    int spawnCooldown = 10 * 60;

    public ShomeretUnitType(String name) {
        super(name);
        speed = 0;
        constructor = LegsUnit::create;
        health = 100000;
        drawCell = false;
        rotateSpeed = 0;
        hitSize = 300f;

        //dormant
        state = 0;

        parts.addAll(
                new BiologicalRegionPart("-1"){{
                    x = y = 0;
                    growX = 0.2f;
                    growY = 0.1f;
                    moveX = 2f;
                    moveY = -0.6f;
                    moveRot = 3f;
                }},
                new BiologicalRegionPart("-2"){{
                    x = y = 0;
                    growX = 0.1f;
                    growY = 0.1f;
                    moveX = -1.5f;
                    moveY = 0.6f;
                    moveRot = -3f;
                }},
                new BiologicalRegionPart("-3"){{
                    x = y = 0;
                    growX = 0.2f;
                    growY = 0.3f;
                    moveX = -1f;
                    moveY = 0.6f;
                    moveRot = 2f;
                }},
                new BiologicalRegionPart("-4"){{
                    x = y = 0;
                    growX = 0.2f;
                    growY = 0.1f;
                    moveX = 3f;
                    moveY = 0.6f;
                    moveRot = 3f;
                }}
        );

        weapons.add(
                new Weapon("shomeret-shockwave"){{
                    x = y = 0;
                    reload = 5 * 60;
                    shoot.shots = 360;
                    shoot.shotDelay = 0.02f;
                    rotate = true;
                    inaccuracy = 320;
                    bullet = new BasicBulletType(4, 1){{
                        shake = 1;
                        despawnEffect = hitEffect = Fx.none;
                        drag = 0.004f;
                        lifetime = 9 * 60;
                        knockback = 20f;
                        backColor = frontColor = trailColor = Color.clear;
                        trailLength = 5;
                        trailWidth = 2;
                        trailInterval = 2;
                        trailEffect = new ParticleEffect(){{
                            layer = Layer.blockUnder;
                            lifetime = 20f;
                            length = 50;
                            particles = 10;
                            sizeFrom = 8;
                            sizeTo = 0;
                            colorFrom = BioPal.bloodRed.a(0.04f);
                            colorTo = BioPal.bloodRed.mul(Color.gray).a(0.04f);
                        }};
                    }};
                }}
        );
    }

    @Override
    public void init() {
        super.init();
        ima = BioUnits.ima;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);

        if (!cutsceneFinished) {
            BioVars.shomeretUI.begin();
            cutsceneFinished = true;
        }

        if (unit.health < unit.maxHealth / 2 && state == 0) state = 1;

        //ima shitter
        if (spawnCooldown <= 0) {
            //ima.spawn(unit.x, unit.y);
            spawnCooldown = 10 * 60;
        } else {
            spawnCooldown--;
        }
    }
}