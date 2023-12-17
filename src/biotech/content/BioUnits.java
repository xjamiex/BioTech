package biotech.content;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.BuilderAI;
import mindustry.ai.types.CargoAI;
import mindustry.ai.types.GroundAI;
import mindustry.ai.types.MinerAI;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.pattern.ShootHelix;
import mindustry.gen.BuildingTetherPayloadUnit;
import mindustry.gen.LegsUnit;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.PowerAmmoType;

public class BioUnits {
    public static UnitType

            //air support
            extractor, carrier,

            //air attack
            scout,

            //ground attack
            strider,

            //core
            watcher
    ;

    public static void load() {

        carrier = new UnitType("carrier"){{
            controller = u -> new CargoAI();
            constructor = BuildingTetherPayloadUnit::create;
            allowedInPayloads = false;
            logicControllable = false;
            playerControllable = false;
            envDisabled = 0;
            payloadCapacity = 0f;

            lowAltitude = false;
            flying = true;
            drag = 0.06f;
            speed = 1f;
            rotateSpeed = 9f;
            accel = 0.1f;
            itemCapacity = 15;
            health = 200f;
            hitSize = 11f;
            engineSize = 2.3f;
            engineOffset = 6.5f;
            hidden = true;

            outlineColor = Color.valueOf("2b2626");
        }};

        watcher = new UnitType("watcher"){{
            aiController = BuilderAI::new;
            isEnemy = false;
            constructor = UnitEntity::create;

            lowAltitude = true;
            flying = true;
            mineSpeed = 10f;
            mineTier = 1;
            buildSpeed = 1.2f;
            drag = 0.06f;
            speed = 3.25f;
            rotateSpeed = 19f;
            accel = 0.10f;
            itemCapacity = 40;
            health = 350f;
            engineOffset = 7f;
            hitSize = 11f;

            mineWalls = true;
            mineFloor = true;

            weapons.add(
                    new Weapon("bio-tech-watcher-missile"){{
                        x = 0;
                        y = 0;
                        mirror = false;
                        top = true;
                        reload = 125f;
                        shootSound = Sounds.missileLarge;
                        shoot = new ShootHelix(){{
                            mag = 0.8f;
                            scl = 4f;
                            shots = 3;
                        }};
                        bullet = new MissileBulletType(4, 20){{
                            width = 12f;
                            height = 12f;
                            shootEffect = Fx.none;
                            lifetime = 60f;
                            homingPower = 1;
                            buildingDamageMultiplier = 0.1f;
                        }};
                    }}
            );

            outlineColor = Color.valueOf("2b2626");
        }};

        scout = new UnitType("scout"){{
            constructor = UnitEntity::create;

            lowAltitude = true;
            flying = true;
            drag = 0.06f;
            speed = 2.8f;
            rotateSpeed = 19f;
            accel = 0.05f;
            itemCapacity = 40;
            health = 650f;
            engineOffset = 7f;
            hitSize = 11f;

            weapons.add(
                    new Weapon("scout-orb"){{
                        x = 0;
                        y = 0;
                        mirror = false;
                        top = true;
                        reload = 210f;
                        shootSound = Sounds.shootBig;
                        bullet = new BasicBulletType(4.2f, 80){{
                            width = 15;
                            height = 15;
                            shrinkX = shrinkY = 0;
                            backColor = BioPal.bloodRedLight;
                            frontColor = BioPal.bloodRedLight;

                            sprite = "large-orb";

                            trailLength = 10;
                            trailColor = BioPal.bloodRedLight;
                            trailWidth = 4;
                            bulletInterval = 10;
                            intervalBullets = 2;

                            shootEffect = hitEffect = despawnEffect = new ParticleEffect(){{
                                colorFrom = BioPal.bloodRedLight;
                                colorTo = BioPal.bloodRed;
                                particles = 6;
                                sizeFrom = 5;
                                sizeTo = 0;
                            }};
                            intervalBullet = new BasicBulletType(0.2f, 5){{
                                width = 5;
                                height = 5;
                                shrinkX = shrinkY = 0;
                                backColor = BioPal.bloodRedLight;
                                frontColor = BioPal.bloodRedLight;
                                sprite = "large-orb";

                                shootEffect = hitEffect = despawnEffect = new ParticleEffect(){{
                                    colorFrom = BioPal.bloodRedLight;
                                    colorTo = BioPal.bloodRed;
                                    particles = 2;
                                    sizeFrom = 5;
                                    sizeTo = 0;
                                }};
                            }};
                        }};
                    }}
            );

            outlineColor = Color.valueOf("2b2626");
        }};

        strider = new UnitType("strider"){{
            constructor = LegsUnit::create;
            aiController = GroundAI::new;

            speed = 0.72f;
            drag = 0.11f;
            hitSize = 9f;
            rotateSpeed = 3f;
            health = 650;
            armor = 1f;
            legStraightness = 0.3f;
            stepShake = 0f;

            legCount = 4;
            legLength = 8f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -2f;
            legBaseOffset = 3f;
            legMaxLength = 2f;
            legMinLength = 0.9f;
            legLengthScl = 0.96f;
            legForwardScl = 1.1f;
            rippleScale = 0.2f;

            legMoveSpace = 1.2f;
            allowLegStep = true;
            legPhysicsLayer = false;

            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;

            outlineColor = Color.valueOf("2b2626");

            weapons.add(new Weapon("biotech-strider-cannon"){{
                x = 0;
                y = 0;
                alternate = false;
                mirror = false;
                reload = 28f;

                shootSound = Sounds.missile;
                inaccuracy = 15f;
                bullet = new MissileBulletType(3, 15){{
                    frontColor = BioPal.bloodRedLight;
                    backColor = BioPal.bloodRed;
                    width = 8;
                    height = 8;

                    trailColor = BioPal.bloodRedLight;
                    trailWidth = 2;
                    trailLength = 2;
                    trailInterval = 2f;
                    trailEffect = new ParticleEffect(){{
                        colorFrom = BioPal.bloodRedLight;
                        colorTo = BioPal.bloodRed;
                        particles = 2;
                        sizeFrom = 3;
                        sizeTo = 0;
                    }};

                    sprite = "circle";
                }};
            }});
        }};

    }
}
