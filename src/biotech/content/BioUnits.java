package biotech.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.ai.types.BuilderAI;
import mindustry.ai.types.CargoAI;
import mindustry.ai.types.GroundAI;
import mindustry.ai.types.SuicideAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;

public class BioUnits {
    public static UnitType

            //air support
            carrier,

            //air attack
            scout, seer,

            //ground attack
            strider,

            //core
            watcher,

            //immune
            kaph37, mother

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
            mineSpeed = 12f;
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
                        shootWarmupSpeed = 10f;
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
                            homingPower = 0.2f;
                            buildingDamageMultiplier = 0.01f;
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
            itemCapacity = 5;
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
                        bullet = new BasicBulletType(4.2f, 100){{
                            width = 15;
                            height = 15;
                            shrinkX = shrinkY = 0;
                            backColor = BioPal.bloodRedLight;
                            frontColor = BioPal.bloodRedLight;

                            sprite = "large-orb";

                            trailLength = 10;
                            trailColor = BioPal.bloodRedLight;
                            trailWidth = 4;
                            trailInterval = 2;
                            intervalBullets = 1;
                            bulletInterval = 1.5f;

                            shootEffect = hitEffect = despawnEffect = new ParticleEffect(){{
                                colorFrom = BioPal.bloodRedLight;
                                colorTo = BioPal.bloodRed;
                                particles = 6;
                                sizeFrom = 5;
                                sizeTo = 0;
                            }};
                            intervalBullet = new BasicBulletType(0.2f, 3){{
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
                                    sizeFrom = 3;
                                    sizeTo = 0;
                                }};
                            }};
                        }};
                    }}
            );

            outlineColor = Color.valueOf("2b2626");
        }};

        seer = new UnitType("seer"){{
            constructor = UnitEntity::create;

            lowAltitude = true;
            flying = true;
            drag = 0.06f;
            speed = 2.3f;
            rotateSpeed = 13f;
            accel = 0.04f;
            itemCapacity = 5;
            health = 1520f;
            engineOffset = 11f;
            engineSize = 3f;
            hitSize = 19f;

            parts.addAll(
                    new HaloPart(){{
                        x = 0;
                        y = -5;
                        sides = 5;
                        shapes = 1;
                        rotateSpeed = 5;
                        color = BioPal.bloodRed;
                        colorTo = BioPal.bloodRedLight;
                        radius = 0;
                        radiusTo = 3;
                        stroke = 0;
                        strokeTo = 3;
                        layer = Layer.effect;
                    }},
                    new HaloPart(){{
                        x = 0;
                        y = -5;
                        sides = 5;
                        shapes = 1;
                        rotateSpeed = -5;
                        color = BioPal.bloodRed;
                        colorTo = BioPal.bloodRedLight;
                        radius = 0;
                        radiusTo = 6;
                        stroke = 0;
                        strokeTo = 1.5f;
                        layer = Layer.effect;
                        hollow = true;
                    }},
                    new RegionPart("-wings"){{
                        x = y = 0;
                        progress = PartProgress.warmup;
                        mirror = true;
                        moveX = 1;
                        moveY = -1.2f;
                    }},
                    new RegionPart("-nozzle"){{
                        x = y = 0;
                        progress = PartProgress.warmup;
                        mirror = true;
                        moveX = 1.3f;
                        moveY = -1f;
                    }}
            );

            weapons.add(
                    new Weapon("seer-missile-launcher"){{
                        x = 0;
                        y = 0;
                        mirror = false;
                        top = true;
                        reload = 80f;
                        shootSound = Sounds.missileLarge;
                        shoot = new ShootSpread(5, 20f);

                        bullet = new MissileBulletType(4.2f, 65){{
                            width = 10;
                            height = 15;
                            shrinkX = shrinkY = 0;
                            backColor = BioPal.bloodRedLight;
                            frontColor = BioPal.bloodRedLight;

                            sprite = "biotech-double-rhombus";

                            trailLength = 15;
                            trailColor = BioPal.bloodRedLight;
                            trailWidth = 3;
                            lifetime = 55f;
                            trailInterval = 1.5f;

                            trailEffect = hitEffect = despawnEffect = new ParticleEffect(){{
                                colorFrom = BioPal.bloodRedLight;
                                colorTo = BioPal.bloodRed;
                                particles = 1;
                                sizeFrom = 3;
                                sizeTo = 0;
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
                    homingPower = 0.3f;
                    trailEffect = new ParticleEffect(){{
                        colorFrom = BioPal.bloodRedLight;
                        colorTo = BioPal.bloodRed;
                        particles = 2;
                        sizeFrom = 1;
                        sizeTo = 0;
                    }};

                    sprite = "circle";
                }};
            }});
        }};
        kaph37 = new UnitType("kaph37"){{
            constructor = LegsUnit::create;
            aiController = GroundAI::new;

            speed = 1f;
            drag = 0.11f;
            hitSize = 9f;
            rotateSpeed = 3f;
            health = 650;
            armor = 1f;
            legStraightness = 0.3f;
            stepShake = 0f;
            drawCell = false;

            legCount = 4;
            legLength = 12f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -2f;
            legBaseOffset = 3f;
            legMaxLength = 2.2f;
            legMinLength = 1.5f;
            legLengthScl = 1.1f;
            legForwardScl = 1.1f;
            legSpeed = 0.2f;
            rippleScale = 0.2f;

            legMoveSpace = 1.2f;
            allowLegStep = true;
            legPhysicsLayer = false;

            deathExplosionEffect = new ParticleEffect(){{
                sizeFrom = 5;
                sizeTo = 0;
                colorFrom = BioPal.bloodRedLight;
                colorTo = BioPal.bloodRed;
            }};

            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;
            lightOpacity = 0;

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = BioSounds.fleshHit;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;
                    hitSound = BioSounds.fleshHit;

                    rangeOverride = 60f;
                    hitEffect = new ParticleEffect(){{
                        sizeFrom = 5;
                        sizeTo = 5;
                        colorFrom = BioPal.bloodRedLight;
                        colorTo = BioPal.bloodRed;
                    }};
                    speed = 0f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 50f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});

            parts.add(
                    new RegionPart("-mouth"){{
                        progress = p -> Interp.exp5.apply(Mathf.sinDeg((p.recoil * 360) * 5.2f)) * 0.4f;
                        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg((p.recoil * 360) * 8f)) * 0.1f;
                        growX = 0.6f;
                        growY = 0.5f;
                        moveX = -1.3f;
                        moveY = 0.6f;
                        moveRot = 7f;
                        x = 2f;
                        y = 2f;
                    }},
                    new RegionPart("-holes"){{
                        progress = p -> Interp.exp5.apply(Mathf.sinDeg((p.recoil * 360) * 6.5f)) * 0.6f;
                        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg((p.recoil * 360) * 9.5f)) * 0.1f;
                        growX = 0.3f;
                        growY = 0.5f;
                        moveX = 1f;
                        moveY = -0.4f;
                        moveRot = -5f;
                        x = 0f;
                        y = -2f;
                    }}

            );

            outlineColor = Color.valueOf("2e0808");
        }};
        mother = new UnitType("mother"){{
            weapons.add(new Weapon(){
                {
                    reload = 900f;
                    shootCone = 180f;
                    ejectEffect = Fx.none;
                    shootSound = BioSounds.fleshHit;
                    alwaysShooting = true;
                    x = shootY = 0f;
                    mirror = false;
                    shoot.shots = 3;
                    bullet = new BulletType() {{
                        spawnUnit = new MissileUnitType("fetus"){{
                            speed = 0.9f;
                            legStraightness = 0.3f;
                            stepShake = 0f;
                            range = 40;
                            lightRadius = 0;
                            loopSound = BioSounds.fetusCries;
                            weapons.add(new Weapon(){
                                {
                                    shootOnDeath = true;
                                    reload = 24f;
                                    shootCone = 180f;
                                    ejectEffect = Fx.none;
                                    shootSound = BioSounds.wail;
                                    x = shootY = 0f;
                                    mirror = false;
                                    bullet = new ExplosionBulletType() {
                                        {
                                            damage = 90;
                                            collidesTiles = false;
                                            collides = false;
                                            hitSound = BioSounds.wail;
                                            splashDamageRadius = 45;
                                            splashDamage = 90f;

                                            rangeOverride = 40f;
                                            hitEffect = new ParticleEffect() {{
                                                sizeFrom = 5;
                                                sizeTo = 5;
                                                colorFrom = BioPal.bloodRedLight;
                                                colorTo = BioPal.bloodRed;
                                            }};
                                        }
                                    };
                                    deathExplosionEffect = new ParticleEffect() {{
                                        sizeFrom = 8;
                                        sizeTo = 0;
                                        lightOpacity = 0;
                                        lifetime = 250;
                                        layer = 10;
                                        colorFrom = BioPal.bloodRedLight;
                                        colorTo = BioPal.bloodRed;
                                    }};
                                }});
                            drawCell = false;
                            outlineColor = Color.valueOf("2e0808");
                            legCount = 4;
                            legLength = 9f;
                            lockLegBase = true;
                            legContinuousMove = true;
                            legMaxLength = 2.2f;
                            legMinLength = 1.5f;
                            legLengthScl = 1.1f;
                            legForwardScl = 1.1f;
                            legSpeed = 0.2f;
                            rippleScale = 0.2f;
                            trailLength = 0;
                            engineSize = 0;
                            flying = false;
                            legMoveSpace = 1.2f;
                            allowLegStep = true;
                            legPhysicsLayer = false;
                            constructor = LegsUnit::create;
                            controller = u -> new SuicideAI();
                            aiController = SuicideAI::new;
                            hitSize = 8;
                            parts.add(
                                    new RegionPart("-eye"){{
                                        progress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 5.2f)) * 0.6f;
                                        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 8f)) * 0.05f;
                                        growX = 0.6f;
                                        growY = 0.5f;
                                        moveX = .5f;
                                        moveY = 0.6f;
                                        moveRot = 14f;
                                    }},
                                    new RegionPart("-anothereye"){{
                                        progress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 6.5f)) * 0.2f;
                                        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 5.5f)) * 0.4f;
                                        growX = 0.3f;
                                        growY = 0.5f;
                                        moveX = -0.5f;
                                        moveY = -0.4f;
                                        moveRot = -19f;
                                    }}

                            );

                            }};
                    }};
                }});
            outlineColor = Color.valueOf("2e0808");
            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;
            researchCostMultiplier = 0f;
            lightOpacity = 0;
            legSpeed = 0.005f;
            deathSound = BioSounds.motherDeath;
            hidden = true;
            legPairOffset = 5;
            parts.add(
                    new RegionPart("-pods"){{
                        progress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 1.5f)) * 0.2f;
                        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 2.5f)) * 0.05f;
                        growX = 0.5f;
                        growY = 0.5f;
                        moveX = 0.5f;
                        moveY = 0.5f;
                        moveRot = -5f;
                    }},
                    new RegionPart("-jaw"){{
                        progress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 4.5f)) * 0.3f;
                        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 3.5f)) * 0.3f;
                        growX = 0.25f;
                        growY = 0.25f;
                        moveX = 1f;
                        moveY = -0.4f;
                        moveRot = -14f;
                    }},
            new RegionPart("-head"){{
                progress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 5.2f)) * 0.4f;
                growProgress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time * 8f)) * 0.3f;
                growX = 0.25f;
                growY = 0.15f;
                moveX = 1f;
                moveY = -0.5f;
                moveRot = -10f;
            }}
                    );
            constructor = LegsUnit::create;
            aiController = GroundAI::new;

            speed = 0.2f;
            drag = 0.11f;
            hitSize = 12f;
            rotateSpeed = 1.2f;
            health = 1550;
            armor = 0f;
            legStraightness = 0.25f;
            stepShake = 0.1f;
            drawCell = false;

            legCount = 5;
            legLength = 13f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -4f;
            legBaseOffset = 5f;
            legMaxLength = 2.2f;
            legMinLength = 1.5f;
            legLengthScl = 1.1f;
            legForwardScl = 1.1f;
            legSpeed = 0.05f;
            rippleScale = 0.2f;
            mechStepParticles = true;

            legMoveSpace = 1.7f;
            allowLegStep = true;
            legPhysicsLayer = false;

            deathExplosionEffect = new ParticleEffect(){{
                sizeFrom = 12;
                sizeTo = 0;
                lightOpacity = 0;
                lifetime = 250;
                layer = 10;
                colorFrom = BioPal.bloodRedLight;
                colorTo = BioPal.bloodRed;
            }};
        }};
    }
}
