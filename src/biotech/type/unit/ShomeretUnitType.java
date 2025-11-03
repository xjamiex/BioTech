package biotech.type.unit;

import arc.graphics.Color;
import arc.math.geom.Position;
import arc.util.Log;
import biotech.BioVars;
import biotech.content.BioFx;
import biotech.content.BioPal;
import biotech.content.BioUnits;
import biotech.entities.part.BiologicalRegionPart;
import biotech.type.BiologicalUnitType;
import biotech.type.weapon.ShockwaveWeapon;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.LegsUnit;
import mindustry.gen.MechUnit;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.InverseKinematics;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class ShomeretUnitType extends BiologicalUnitType {
    public static int state;
    public static UnitType ima;

    int spawnCooldown = 10 * 60;

    public ShomeretUnitType(String name) {
        super(name);
        speed = 0;
        constructor = MechUnit::create;
        health = 25000;
        drawCell = false;
        rotateSpeed = 0;
        hitSize = 90f;

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
                new ShockwaveWeapon("shomeret-shockwave"){{
                    x = y = 0;
                    reload = 10 * 60;
                    shoot.shots = 360;
                    shoot.shotDelay = 0.02f;
                    rotate = true;
                    inaccuracy = 320;

                    boomEffect = new MultiEffect(
                            new ParticleEffect() {{
                                layer = Layer.effect;
                                particles = 8;
                                colorFrom = Pal.darkestestGray.a(0.5f);
                                colorTo = Pal.darkestestGray.a(0.5f);
                                sizeFrom = 35;
                                sizeTo = 0;
                                length = 100;
                                lifetime = 60 * 6;
                            }},
                            new ParticleEffect() {{
                                layer = Layer.effect;
                                particles = 7;
                                colorFrom = Pal.darkerGray.a(0.5f);
                                colorTo = Pal.darkestGray.a(0.5f);
                                sizeFrom = 10;
                                sizeTo = 0;
                                length = 50;
                                lifetime = 60 * 3;
                            }}
                    );

                    bullet = new BasicBulletType(4, 5){{
                        shake = 32;
                        despawnEffect = hitEffect = shootEffect = ejectEffect = Fx.none;
                        shootSound = Sounds.none;
                        drag = 0.004f;
                        lifetime = 9 * 60;
                        knockback = 20f;
                        lightOpacity = 0;
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