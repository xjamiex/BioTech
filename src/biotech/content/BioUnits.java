package biotech.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import biotech.BioTech;
import biotech.entities.bullet.LightningLaserBulletType;
import biotech.entities.part.BiologicalRegionPart;
import biotech.type.BiologicalUnitType;
import biotech.type.bullets.SpeedUpBulletType;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.abilities.LiquidExplodeAbility;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.effect.SeqEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.HoverPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootSpread;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.blocks.defense.turrets.PointDefenseTurret;

import static arc.graphics.g2d.Draw.color;

public class BioUnits {
    public static UnitType

            //air support
            carrier,

            //air attack
            scout, seer,

            //air specialty
            smith, anvil,

            //ground attack
            strider, nomad,

            //core
            watcher,

            //immune
            kaph50, kaph37, kaph31, mother

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
                        y = -7;
                        sides = 5;
                        shapes = 1;
                        rotateSpeed = 5;
                        color = BioPal.bloodRed;
                        colorTo = BioPal.bloodRedLight;
                        radius = 0;
                        radiusTo = 2;
                        stroke = 0;
                        strokeTo = 2;
                        layer = Layer.effect;
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

                        shoot.firstShotDelay = BioFx.seerCharge.lifetime + BioFx.seerWarmup.lifetime;

                        bullet = new MissileBulletType(4.2f, 65){{
                            width = 10;
                            height = 15;
                            shrinkX = shrinkY = 0;
                            backColor = BioPal.bloodRedLight;
                            frontColor = BioPal.bloodRedLight;

                            chargeEffect = new SeqEffect(BioFx.seerWarmup, BioFx.seerCharge);
                            shootEffect = BioFx.fourSpike(BioPal.bloodRedLight, 4, 25);

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

        smith = new UnitType("smith"){{
            constructor = ElevationMoveUnit::create;

            hovering = true;
            drag = 0.01f;
            speed = 5.2f;
            rotateSpeed = 19f;
            accel = 0.01f;
            itemCapacity = 0;
            health = 650f;
            hitSize = 11f;

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                recoil = 20;
                minWarmup = 0.9f;
                shootCone = 2f;
                ejectEffect = Fx.none;
                range = 10;
                shootSound = BioSounds.fleshHit;
                x = shootY = 0f;
                mirror = false;

                shake = 3f;

                bullet = new ShrapnelBulletType(){{
                    damage = 125f;
                    hitSound = BioSounds.fleshHit;
                    splashDamageRadius = 5f;
                    splashDamage = 10f;
                    killShooter = true;
                    shootEffect = smokeEffect = Fx.shootBig;
                    hittable = false;
                    collidesAir = true;

                    fromColor = BioPal.potashOrangeLight;
                    toColor = BioPal.potashOrange;
                    width = 10f;
                    serrationWidth = 2f;
                    serrationSpacing = 12f;
                    pierce = true;
                }};
            }});

            abilities.add(new MoveEffectAbility(0f, -7f, Pal.sapBulletBack, Fx.missileTrailShort, 4f){{
                teamColor = true;
            }});
            parts.addAll(
                    new HoverPart(){{
                        x = 0f;
                        y = 0;
                        mirror = true;
                        radius = 18f;
                        sides = 3;
                        phase = 90f;
                        stroke = 2f;
                        layerOffset = -1f;
                        color = BioPal.potashOrangeLight;
                    }},
                    new RegionPart("-piston"){{
                        x = y = 0;
                        progress = PartProgress.warmup;
                        layerOffset = -0.001f;
                        mirror = true;
                        moveX = 2f;
                        moveY = 2f;
                    }}
            );

            outlineColor = Color.valueOf("2b2626");
        }};

        anvil = new UnitType("anvil"){{
            constructor = UnitEntity::create;
            aiController = FlyingAI::new;

            flying = true;
            drag = 0.03f;
            speed = 3.2f;
            rotateSpeed = 2f;
            accel = 0.02f;
            itemCapacity = 0;
            health = 1320f;
            hitSize = 14f;
            engineOffset = 8;
            engineSize = 5;
            lowAltitude = true;

            weapons.addAll(
                    new PointDefenseWeapon("biotech-anvil-point-defense"){{
                        x = 5;
                        y = 0;
                        mirror = true;
                        alternate = false;
                        color = BioPal.potashOrangeLight;
                        reload = 15;
                        range = 120;
                        layerOffset = 1;

                        shake = 1f;
                    }},

                    new Weapon("biotech-anvil-orb"){{
                        x = 0;
                        y = 0;
                        mirror = false;
                        alternate = false;
                        shoot.firstShotDelay = 240;
                        reload = 280;
                        range = 180;
                        shootSound = Sounds.shootBig;
                        shootStatus = StatusEffects.unmoving;
                        shootStatusDuration = 240;

                        shake = 5f;

                        bullet = new SpeedUpBulletType(20, 325, "circle"){{
                            backColor = BioPal.potashOrange;
                            frontColor = BioPal.potashOrangeLight;
                            width = 20;
                            height = 20;
                            shrinkX = shrinkY = 0;
                            hitSound = despawnSound = Sounds.shockBlast;
                            chargeEffect = new MultiEffect(new SeqEffect(BioFx.smithBeginWarmup, BioFx.smithWarmup));
                            shootEffect = new MultiEffect(BioFx.smithShoot, new ParticleEffect(){{
                                colorTo = BioPal.potashOrange;
                                colorFrom = BioPal.potashOrangeLight;
                                particles = 15;
                                length = 60;
                                randLength = true;
                                cone = 45;
                                sizeFrom = 4;
                                sizeTo = 0;
                            }});
                            splashDamage = 50;
                            splashDamageRadius = 10;
                            pierceCap = 4;
                            pierceBuilding = pierce = true;
                            recoil = 5;

                            trailColor = BioPal.potashOrangeLight;
                            trailInterval = 3f;
                            trailLength = 20;
                            trailWidth = 4f;
                            trailRotation = true;
                            trailEffect = new Effect(16f, e -> {
                                color(BioPal.potashOrangeLight);
                                for(int s : Mathf.signs){
                                    Drawf.tri(e.x, e.y, 4f, 20f * e.fslope(), e.rotation + 90f*s);
                                }
                            });

                            hitEffect = despawnEffect = BioFx.fourSpike(BioPal.potashOrangeLight, 10, 38);

                            velocityBegin = 0.5f;
                            velocityIncrease = 10f;
                            accelerateBegin = 0.05f;
                            accelerateEnd = 0.95f;
                            accelInterp = Interp.exp5;
                        }};
                    }}
            );

            parts.addAll(
                    new RegionPart("-wing"){{
                        x = 0;
                        y = 0;
                        mirror = true;
                        progress = PartProgress.charge;
                        moveX = -2;
                        moveY = 4;
                        moveRot = 12;
                        weaponIndex = 2;
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
                    homingPower = 0.02f;
                    lifetime = 35f;
                    trailEffect = new ParticleEffect(){{
                        colorFrom = BioPal.bloodRedLight;
                        colorTo = BioPal.bloodRed;
                        particles = 2;
                        sizeFrom = 1.5f;
                        sizeTo = 0;
                    }};

                    sprite = "circle";
                }};
            }});
        }};

        nomad = new UnitType("nomad"){{
            constructor = LegsUnit::create;
            aiController = GroundAI::new;

            speed = 0.65f;
            drag = 0.09f;
            hitSize = 13f;
            rotateSpeed = 2.0f;
            health = 1620f;
            armor = 1f;
            legStraightness = 0.3f;
            stepShake = 0.3f;

            legCount = 4;
            legLength = 15f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -4f;
            legBaseOffset = 3f;
            legMaxLength = 1.1f;
            legMinLength = 0.9f;
            legLengthScl = 1f;
            legForwardScl = 1.1f;
            rippleScale = 0.5f;

            legMoveSpace = 1.2f;
            allowLegStep = true;
            legPhysicsLayer = false;

            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;

            outlineColor = Color.valueOf("2b2626");

            weapons.addAll(
                    new Weapon("biotech-nomad-laser"){{
                        x = 10;
                        y = 0;
                        alternate = false;
                        mirror = true;
                        alwaysContinuous = true;
                        continuous = true;
                        range = 100f;
                        layerOffset = 1;

                        shootSound = Sounds.flame2;
                        inaccuracy = 15f;
                        bullet = new ContinuousFlameBulletType(){{
                            colors = new Color[]{BioPal.bloodRed.a(0.55f), BioPal.bloodRed.a(0.7f), BioPal.bloodRedLight.a(0.8f), BioPal.bloodRedLight, Color.white.cpy()};
                            shootEffect = Fx.none;
                            damage = 10f;
                            pierce = pierceBuilding = false;
                            pierceCap = 1;
                            damageInterval = 10;

                            flareColor = BioPal.bloodRedLight;
                            flareLength = 6f;
                            flareRotSpeed = 0.3f;
                            flareWidth = 5f;

                            length = 50f;
                            width = 2.2f;

                            lifetime = 80f;
                            trailEffect = hitEffect = new ParticleEffect(){{
                                colorFrom = BioPal.bloodRedLight;
                                colorTo = BioPal.bloodRed;
                                particles = 2;
                                sizeFrom = 1.5f;
                                sizeTo = 0;
                            }};
                        }};
                    }},
                    new Weapon("biotech-nomad-railgun"){{
                        x = 0;
                        y = 0;
                        alternate = false;
                        mirror = false;
                        range = 100f;

                        shoot.firstShotDelay = reload = 120f;

                        shootSound = BioSounds.shootMedium;
                        inaccuracy = 2f;
                        bullet = new BasicBulletType(10, 185){{
                            sprite = "circle";

                            frontColor = BioPal.bloodRedLight;
                            backColor = BioPal.bloodRed;
                            width = 10;
                            height = 12;
                            shrinkX = shrinkY = 0;
                            pierce = pierceBuilding = true;
                            pierceCap = 3;
                            lifetime = 20;

                            chargeEffect = new SeqEffect(BioFx.nomadBeginWarmup, BioFx.nomadWarmup, BioFx.nomadRelease);
                            shootEffect = new ParticleEffect(){{
                                cone = 40;
                                colorFrom = BioPal.bloodRedLight;
                                colorTo = BioPal.bloodRed;
                                sizeFrom = 7;
                                sizeTo = 0;
                                particles = 20;
                                interp = Interp.exp5Out;
                                length = 40;
                            }};

                            trailLength = 20;
                            trailWidth = 4;
                            trailColor = BioPal.bloodRedLight;

                            hitEffect = despawnEffect = Fx.none;
                            trailEffect = hitEffect = new ParticleEffect(){{
                                colorFrom = BioPal.bloodRedLight;
                                colorTo = BioPal.bloodRed;
                                particles = 2;
                                sizeFrom = 1.5f;
                                sizeTo = 0;
                            }};

                            fragBullets = 4;
                            fragBullet = new ContinuousFlameBulletType(){{
                                colors = new Color[]{BioPal.bloodRed.a(0.55f), BioPal.bloodRed.a(0.7f), BioPal.bloodRedLight.a(0.8f), BioPal.bloodRedLight, Color.white.cpy()};
                                shootEffect = Fx.none;
                                damage = 2f;
                                pierce = pierceBuilding = false;
                                pierceCap = 1;
                                damageInterval = 10;

                                drawFlare = true;
                                flareLength = 15f;
                                flareWidth = 10;
                                flareColor = BioPal.bloodRedLight;

                                length = 2f;
                                width = 2;

                                lifetime = 40f;
                                trailEffect = hitEffect = new ParticleEffect(){{
                                    colorFrom = BioPal.bloodRedLight;
                                    colorTo = BioPal.bloodRed;
                                    particles = 2;
                                    sizeFrom = 1.5f;
                                    sizeTo = 0;
                                }};
                            }};
                        }};
                    }}
            );

            parts.add(
                    new RegionPart("-front"){{
                        x = 0;
                        y = 0;
                        weaponIndex = 1;
                        mirror = true;
                        progress = PartProgress.warmup;
                        moveX = -3;
                        moveY = -3;
                    }}
            );
        }};

        kaph50 = new BiologicalUnitType("kaph50"){{
            constructor = LegsUnit::create;
            aiController = SuicideAI::new;

            speed = 0.9f;
            drag = 0.11f;
            hitSize = 11f;
            rotateSpeed = 3f;
            health = 450;
            armor = 1f;
            legStraightness = 0.3f;
            stepShake = 0f;
            drawCell = false;

            legCount = 4;
            legLength = 10f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -7.5f;
            legBaseOffset = 2f;
            legMaxLength = 2.1f;
            legMinLength = 1.4f;
            legLengthScl = 1.1f;
            legForwardScl = 1.3f;
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

            parts.addAll(
                    new BiologicalRegionPart("-body"){{
                        growX = 0.5f;
                        growY = 0.6f;
                        randProgScale = 250f;
                        moveX = -0.3f;
                        moveY = 0.6f;
                        moveRot = 3f;
                    }}
            );

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                shootCone = 180f;
                ejectEffect = Fx.none;
                range = 1;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;

                    rangeOverride = 10f;
                    hitEffect = new ParticleEffect(){{
                        sizeFrom = 5;
                        sizeTo = 5;
                        colorFrom = BioPal.bloodRedLight;
                        colorTo = BioPal.bloodRed;
                    }};
                    speed = 0f;
                    splashDamageRadius = 25f;
                    instantDisappear = true;
                    splashDamage = 40f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                    shootEffect = Fx.none;
                }};
            }});
        }};

        kaph37 = new BiologicalUnitType("kaph37"){{
            constructor = LegsUnit::create;
            aiController = SuicideAI::new;

            speed = 0.7f;
            drag = 0.11f;
            hitSize = 11f;
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
                range = 1;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;

                    rangeOverride = 10f;
                    hitEffect = new ParticleEffect(){{
                        sizeFrom = 5;
                        sizeTo = 5;
                        colorFrom = BioPal.bloodRedLight;
                        colorTo = BioPal.bloodRed;
                    }};
                    speed = 0f;
                    splashDamageRadius = 35f;
                    instantDisappear = true;
                    splashDamage = 50f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                    shootEffect = Fx.none;
                }};
            }});

            parts.add(
                    new BiologicalRegionPart("-mouth"){{
                        growX = 0.8f;
                        growY = 0.8f;
                        randProgScale = 250f;
                        moveX = -1.3f;
                        moveY = 0.6f;
                        moveRot = 7f;
                        x = 2f;
                        y = 2f;
                    }},
                    new BiologicalRegionPart("-holes"){{
                        growX = 0.7f;
                        growY = 0.7f;
                        randProgScale = 200f;
                        moveX = 1f;
                        moveY = -0.4f;
                        moveRot = -5f;
                        x = 0f;
                        y = -2f;
                    }}

            );
        }};

        kaph31 = new BiologicalUnitType("kaph31"){{
            constructor = LegsUnit::create;
            aiController = GroundAI::new;

            speed = 0.5f;
            drag = 0.12f;
            hitSize = 14f;
            rotateSpeed = 2f;
            health = 950;
            armor = 1.5f;
            legStraightness = 0.3f;
            stepShake = 1f;
            drawCell = false;

            legCount = 6;
            legLength = 19f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -4f;
            legBaseOffset = 4f;
            legMaxLength = 1.2f;
            legMinLength = 1.1f;
            legLengthScl = 1.1f;
            legForwardScl = 1.1f;
            legSpeed = 0.5f;
            rippleScale = 0.2f;

            legMoveSpace = 1.3f;
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

            weapons.add(new Weapon("biotech-kaph-gastric-gun"){{
                x = 3;
                y = 7.5f;
                top = false;
                reload = 5f;
                shootCone = 45f;
                loopSound = Sounds.spray;
                shootSound = Sounds.none;
                ejectEffect = Fx.none;
                range = 1;
                mirror = false;
                bullet = new LiquidBulletType(BioLiquids.heraticAcid){{
                    lifetime = 60f;
                    inaccuracy = 15;
                    speed = 4.5f;
                    knockback = 0.2f;
                    puddleSize = 7f;
                    orbSize = 3f;
                    damage = 6f;
                    drag = 0.05f;
                    statusDuration = 60f * 4f;
                }};
            }});

            parts.add(
                    new BiologicalRegionPart("-spike"){{
                        growX = 0.2f;
                        growY = 0.2f;
                        randProgScale = 250f;
                        moveX = -2f;
                        moveY = 1.6f;
                        moveRot = 7f;
                        x = 0f;
                        y = 0f;
                    }},
                    new BiologicalRegionPart("-eye"){{
                        growX = 0.2f;
                        growY = 0.2f;
                        randProgScale = 292f;
                        moveX = 0.5f;
                        moveY = -2f;
                        moveRot = -5f;
                        x = 0f;
                        y = 0f;
                    }},
                    new BiologicalRegionPart("-pupil-1"){{;
                        randProgScale = 292f;
                        moveX = 0.5f;
                        moveY = -0.5f;
                        x = 0f;
                        y = 0f;
                    }},
                    new BiologicalRegionPart("-pupil-2"){{
                        randProgScale = 292f;
                        moveX = 0.5f;
                        moveY = -0.5f;
                        x = 0f;
                        y = 0f;
                    }}
            );
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
