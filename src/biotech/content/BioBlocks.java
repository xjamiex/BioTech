package biotech.content;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import biotech.entities.bullet.LightningLaserBulletType;
import biotech.world.blocks.enviroment.BiologicalStaticSpawner;
import biotech.world.blocks.enviroment.TallTreeBlock;
import biotech.world.blocks.power.PowerConduit;
import biotech.world.blocks.production.BoostableDrill;
import biotech.world.blocks.production.DrillUpgrader;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.BufferedItemBridge;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitCargoLoader;
import mindustry.world.blocks.units.UnitCargoUnloadPoint;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.Category.*;
import static mindustry.type.ItemStack.with;

public class BioBlocks {
    public static Block
            //liquids
            bioPress, liquidPipe, liquidSplitter, liquidOverpass, bioSiphon,

            //distribution
            magnesiumConveyor, splitter, conveyorOverpass,

            //drill
            bioDrill, boneCrusher, bioPiercer, drillUpgrader,

            //env
            flesh, rottenFlesh, decayedFlesh, scarredFlesh,
            flint, bone, myostone, flourspar, dolomite, alloyFloor, squareAlloyFloor, gneiss, marl,
            oreMagnesium, orePhosphorus,
            fleshWall, rottenFleshWall, decayedFleshWall,
            boneWall, decayedBoneWall, dolomiteWall, flintWall, floursparWall, myostoneWall, alloyWall, gneissWall, marlWall,
            poreHole,
            plasmoidPuddle,

            //props
            nerveProtrusion, fleshAmalgam, fleshBoulder, rottenFleshAmalgam, rottenFleshBoulder,

            //turret
            inception, costae, celluris, dissection, needle,

            //power
            rotorPipe,

            //production
            hematicSieve, hemoCrystallizer,

            //defense
            magnesiumWall, largeMagnesiumWall,

            //units
            experimentalManufacturer, unitDocker, unitDischarger,
            descentManufacturer, osylithReformer,
            bioUnitSpawner,

            //effect
            coreSight
    ;

    public static final Attribute
            meat = Attribute.add("meat"),
            calcitic = Attribute.add("calcitic");

    public static void load() {

        //liquid
        bioPress = new GenericCrafter("bio-press"){{
            researchCost = with(BioItems.magnesium, 200, BioItems.calciticFragment, 75);
            requirements(production, with(BioItems.magnesium, 100, BioItems.calciticFragment, 50));
            group = BlockGroup.drills;
            size = 2;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            craftTime = 200f;
            itemCapacity = 30;
            ignoreLiquidFullness = true;

            consumeItem(BioItems.carbonicTissue, 4);
            hasLiquids = true;
            outputLiquid = new LiquidStack(BioLiquids.hemoFluid, 1f / 4f);
            liquidCapacity = 40f;
            squareSprite = false;
        }};

        liquidOverpass = new LiquidBridge("liquid-overpass"){{
            requirements(Category.liquid, with(BioItems.calciticFragment, 5));
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 4;
            hasPower = false;
        }};

        liquidPipe = new Conduit("liquid-pipe"){{
            requirements(Category.liquid, with(BioItems.calciticFragment, 1));
            health = 143;
            botColor = Color.valueOf("262525");
            bridgeReplacement = BioBlocks.liquidOverpass;
        }};

        liquidSplitter = new LiquidRouter("liquid-splitter"){{
            requirements(Category.liquid, with(BioItems.calciticFragment, 2));
            health = 143;
            size = 1;
        }};

        bioSiphon = new Pump("bio-siphon"){{
            requirements(Category.liquid, with(BioItems.magnesium, 50, BioItems.calciticFragment, 120));
            pumpAmount = 0.04f;
            liquidCapacity = 30f;
            size = 2;
            drawer = new DrawDefault();
            squareSprite = false;
        }};
        //endregion

        //distribution
        conveyorOverpass = new BufferedItemBridge("conveyor-overpass"){{
            requirements(Category.distribution, with(BioItems.magnesium, 6));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;
            underBullets = true;
        }};
        magnesiumConveyor = new Conveyor("magnesium-convayor"){{
            researchCost = with(BioItems.magnesium, 15);
            bridgeReplacement = BioBlocks.conveyorOverpass;
            requirements(Category.distribution, with(BioItems.magnesium, 1));
            health = 35;
            speed = 0.04f;
            displayedSpeed = 8f;
            underBullets = true;
        }};

        splitter = new Router("splitter"){{
            requirements(Category.distribution, with(BioItems.magnesium, 2));
            underBullets = true;
            health = 40;
        }};
        //endregion

        //drills
        bioDrill = new BoostableDrill("bio-drill"){{
            researchCost = with(BioItems.magnesium, 60);
            requirements(Category.production, with(BioItems.magnesium, 40, BioItems.calciticFragment, 35));
            tier = 1;
            drillTime = 650;
            size = 3;
            squareSprite = false;
            hasLiquids = false;
        }};

        bioPiercer = new AttributeCrafter("bio-piercer"){{
            researchCost = with(BioItems.magnesium, 185, BioItems.calciticFragment, 50);
            requirements(production, with(BioItems.magnesium, 45, BioItems.calciticFragment, 20));
            attribute = meat;
            minEfficiency = 0.000001f;
            baseEfficiency = 0;
            displayEfficiency = false;
            boostScale = 1f / 4f;
            group = BlockGroup.drills;
            size = 2;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            craftTime = 200f;
            itemCapacity = 30;
            ignoreLiquidFullness = true;

            hasLiquids = true;
            outputItem = new ItemStack(BioItems.carbonicTissue, 2);
            liquidCapacity = 40f;
            squareSprite = false;
            craftEffect = new ParticleEffect(){{
                colorFrom = BioPal.bloodRedLight;
                colorTo = BioPal.bloodRed;
                sizeFrom = 2;
                sizeTo = 0;
                particles = 10;
                length = 20f;
            }};

            ambientSound = Sounds.drill;

            drawer = new DrawMulti(
                    new DrawPistons(){{
                        sides = 4;
                        sinMag = 1;
                        sinScl = 1.2f;
                        sinOffset = 2;
                        lenOffset = -5.5f;
                        angleOffset = 45;
                    }},
                    new DrawDefault(),
                    new DrawRegion("-top")
            );
        }};

        boneCrusher = new WallCrafter("bone-crusher"){{
            researchCost = with(BioItems.magnesium, 70, BioItems.calciticFragment, 50);
            requirements(Category.production, with(BioItems.magnesium, 40, BioItems.calciticFragment, 25));
            drillTime = 110f;
            size = 2;
            attribute = calcitic;
            output = BioItems.calciticFragment;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
        }};

        drillUpgrader = new DrillUpgrader("drill-upgrader"){{
            requirements(production, with(BioItems.potash, 120));
            health = 1500;
            size = 2;
            consumePower(0.1f);
        }};
        //endregion

        //environment
        flesh = new Floor("flesh", 4){{
            playerUnmineable = true;
            attributes.set(meat, 1f);
        }};

        flint = new Floor("flint", 4);
        bone = new Floor("bone", 4);
        myostone = new Floor("myostone", 4);
        flourspar = new Floor("flourspar", 4);
        dolomite = new Floor("dolomite", 4);
        alloyFloor = new Floor("alloy-floor", 2);
        squareAlloyFloor = new Floor("square-alloy-floor", 2);
        rottenFlesh = new Floor("rotten-flesh", 4);
        decayedFlesh = new Floor("decayed-flesh", 4);
        scarredFlesh = new Floor("scarred-flesh", 4);
        gneiss = new Floor("gneiss", 4);
        marl = new Floor("marl", 4);
        boneWall = new StaticWall("bone-wall");

        decayedBoneWall = new StaticWall("decayed-bone-wall"){{
            itemDrop = BioItems.calciticFragment;
            attributes.set(calcitic, 1);
        }};

        dolomiteWall = new StaticWall("flint-wall");
        flintWall = new StaticWall("dolomite-wall");
        floursparWall = new StaticWall("flourspar-wall");
        fleshWall = new StaticWall("flesh-wall") {{
            variants = 8;
        }};
        myostoneWall = new StaticWall("myostone-wall");
        alloyWall = new StaticWall("alloy-wall");
        rottenFleshWall = new StaticWall("rotten-flesh-wall");
        decayedFleshWall = new StaticWall("decayed-flesh-wall");
        gneissWall = new StaticWall("gneiss-wall");
        marlWall = new StaticWall("marl-wall");

        poreHole = new SteamVent("pore-hole"){{
            parent = blendGroup = flesh;
            variants = 3;
            effectSpacing = 100f;
            effectColor = Color.valueOf("a69780");
        }};
        plasmoidPuddle = new Floor("plasmoid-puddle"){{
            speedMultiplier = 0.4f;
            variants = 0;
            liquidDrop = BioLiquids.plasmoid;
            liquidMultiplier = 1.2f;
            isLiquid = true;
            status = StatusEffects.shocked;
            statusDuration = 120f;
            drownTime = 200f;
            cacheLayer = CacheLayer.water;
            albedo = 0.8f;
            supportsOverlay = true;
        }};

        nerveProtrusion = new TallTreeBlock("nerve-protrusion");
        fleshAmalgam = new TallTreeBlock("flesh-amalgam"){{
            variants = 5;
            clipSize = 192f;
            shadowOffset = -1.1f;

        }};
        fleshBoulder = new TallTreeBlock("flesh-boulder"){{
            variants = 3;
            shadowOffset = -0.9f;
        }};
        rottenFleshAmalgam = new TallTreeBlock("rotten-flesh-amalgam"){{
            variants = 4;
            clipSize = 192f;
            shadowOffset = -1.1f;
        }};

        rottenFleshBoulder = new TallTreeBlock("rotten-flesh-boulder"){{
            variants = 3;
            shadowOffset = -0.9f;
        }};

        oreMagnesium = new OreBlock("ore-magnesium"){{
            itemDrop = BioItems.magnesium;
        }};
        orePhosphorus = new OreBlock("ore-phosphorus"){{
            itemDrop = BioItems.phosphorus;
        }};
        //endregion

        //turrets
        inception = new ItemTurret("inception"){{
            researchCost = with(BioItems.magnesium, 350, BioItems.calciticFragment, 150);
            health = 1020;
            size = 3;
            requirements(turret, with(BioItems.calciticFragment, 60, BioItems.magnesium, 60));
            maxAmmo = 5;

            range = 150;
            shootY = 0.7f;
            shootSound = Sounds.shootBig;
            inaccuracy = 2f;
            rotateSpeed = 2f;
            reload = 130;
            minWarmup = 0.90f;
            targetAir = false;
            targetGround = true;

            ammo(
                    BioItems.carbonicTissue, new BasicBulletType(3f, 145){{
                        sprite = "biotech-meatball";
                        shootEffect = trailEffect = new ParticleEffect(){{
                            particles = 6;
                            colorFrom = BioPal.bloodRedLight;
                            colorTo = BioPal.bloodRed;
                            sizeFrom = 4;
                            sizeTo = 0;
                            length = 5;
                        }};
                        hitEffect = despawnEffect = new ParticleEffect(){{
                            colorFrom = BioPal.bloodRedLight;
                            colorTo = BioPal.bloodRed;
                            sizeFrom = 10;
                            sizeTo = 0;
                            particles = 10;
                            length = 50f;
                        }};

                        width = 18;
                        height = 18;
                        shrinkX = shrinkY = 0;
                        frontColor = BioPal.bloodRedLight;
                        backColor = BioPal.bloodRed;
                        trailLength = 8;
                        trailWidth = 3;
                        trailColor = BioPal.bloodRedLight;
                        trailInterval = 4f;
                        lifetime = 60f;
                        collidesAir = false;

                        splashDamage = 25;
                        splashDamageRadius = 35f;

                        hitSound = despawnSound = Sounds.dullExplosion;
                    }}
            );

            outlineColor = Color.valueOf("2b2626");

        }};

        costae = new ItemTurret("costae"){{
            health = 1120;
            size = 3;
            requirements(turret, with(BioItems.calciticFragment, 50, BioItems.magnesium, 60));
            maxAmmo = 25;

            range = 230;
            shootY = 0.7f;

            shootSound = Sounds.shootAlt;
            inaccuracy = 10f;
            rotateSpeed = 2f;
            reload = 86;
            minWarmup = 0.90f;
            targetAir = true;
            targetGround = false;

            shoot.shots = 5;
            shoot.shotDelay = 3f;

            ammo(
                    BioItems.calciticFragment, new BasicBulletType(5.5f, 30){{
                        sprite = "biotech-triangle";
                        shootEffect = hitEffect = despawnEffect =  new WaveEffect(){{
                            colorFrom = BioPal.boneWhiteLight;
                            colorTo = BioPal.boneWhite;
                            sizeFrom = 6;
                            sizeTo = 0;
                            strokeFrom = 2f;
                            strokeTo = 0f;
                        }};

                        trailEffect = new WaveEffect(){{
                            colorFrom = BioPal.boneWhiteLight;
                            colorTo = BioPal.boneWhite;
                            sizeFrom = 2;
                            sizeTo = 0;
                            strokeFrom = 1f;
                            strokeTo = 0f;
                        }};

                        width = 14;
                        height = 14;
                        shrinkX = shrinkY = 0;
                        frontColor = BioPal.boneWhiteLight;
                        backColor = BioPal.boneWhiteLight;
                        trailLength = 10;
                        trailWidth = 2;
                        trailColor = BioPal.boneWhiteLight;
                        trailInterval = 2f;
                        lifetime = 60;
                        collidesGround = false;

                        pierce = true;

                        hitSound = despawnSound = Sounds.bang;
                    }}
            );

            outlineColor = Color.valueOf("2b2626");

        }};

        celluris = new ItemTurret("celluris"){{
            health = 1250;
            size = 3;
            requirements(turret, with(BioItems.magnesium, 150, BioItems.potash, 50, BioItems.calciticFragment, 200));
            range = 250;
            shootSound = Sounds.missileSmall;
            inaccuracy = 4.5f;
            rotateSpeed = 1.2f;
            reload = 180;
            minWarmup = 0.8f;
            warmupMaintainTime = 25;
            targetAir = false;
            targetGround = true;
            outlineColor = Color.valueOf("2b2626");

            consumeLiquid(BioLiquids.hemoFluid, 0.12f);
            drawer = new DrawTurret("reinforced-"){
                {
                    parts.addAll(
                            new RegionPart("-barrel") {{
                                progress = PartProgress.warmup;
                                moveY = 2.5f;
                                under = true;
                                layerOffset = -.0001f;
                                moves.add(new PartMove(PartProgress.recoil, 0f, -3.5f, 0f));
                            }},
                                    new RegionPart("-mid") {{
                                        progress = PartProgress.warmup;
                                        moveY = -1f;
                                        moveRot = -10f;
                                        mirror = true;
                                        under = true;
                                        layerOffset = -.0001f;
                                        moves.add(new PartMove(PartProgress.recoil, 0f, -1f, -10f));
                                    }},
                            new RegionPart("-side") {{
                                progress = PartProgress.warmup;
                                moveX = -0.25f;
                                moveY = -0.25f;
                                mirror = true;
                                moves.add(new PartMove(PartProgress.recoil, 1.5f, -1f, -10));
                            }}
                            );
                }};

            ammo(
                    BioItems.potash, new ArtilleryBulletType(4, 85){{
                        width = 15;
                        height = 15;
                        weaveMag = 30;
                        weaveScale = 1;
                        fragBullets = 4;
                        frontColor = BioPal.cellBlueLight;
                        backColor = BioPal.cellBlue;
                        trailColor = BioPal.cellBlueLight;
                        smokeEffect = Fx.shootSmokeSquareBig;
                        shootEffect = Fx.lancerLaserShoot;
                        trailWidth = 3;
                        trailLength = 6;
                        drag = 0.01f;
                        homingPower = 0.2f;
                        homingDelay = 20f;
                        pierce = true;
                        pierceCap = 2;
                        fragOnHit = false;
                        lifetime = 110;
                        collidesAir = false;

                        hitEffect = despawnEffect = new WaveEffect(){{
                            sizeFrom = 5;
                            sizeTo = 0;
                            colorFrom = BioPal.cellBlueLight;
                            colorTo = BioPal.cellBlue;
                            sides = 5;
                            rotateSpeed = 10f;
                        }};
                        fragBullet = new MissileBulletType(3, 40){{
                            width = 6;
                            height = 6;
                            weaveMag = 30;
                            pierce = true;
                            pierceCap = 3;
                            fragOnHit = false;
                            weaveScale = 1;
                            frontColor = BioPal.cellBlueLight;
                            backColor = BioPal.cellBlue;
                            trailColor = BioPal.cellBlueLight;
                            homingPower = 0.15f;
                            trailWidth = 3;
                            trailLength = 6;
                            drag = 0.01f;
                            hitEffect = despawnEffect = new WaveEffect(){{
                                sizeFrom = 5;
                                sizeTo = 0;
                                colorFrom = BioPal.cellBlueLight;
                                colorTo = BioPal.cellBlue;
                                sides = 5;
                                rotateSpeed = 10f;
                            }};

                            fragBullets = 3;

                            fragBullet = new MissileBulletType(2, 25){{
                                width = 3;
                                height = 3;
                                pierce = true;
                                pierceCap = 2;
                                fragOnHit = false;
                                weaveMag = 30;
                                weaveScale = 1;
                                frontColor = BioPal.cellBlueLight;
                                backColor = BioPal.cellBlue;
                                trailColor = BioPal.cellBlueLight;
                                trailWidth = 3;
                                homingPower = 0.1f;
                                trailLength = 6;
                                drag = 0.01f;
                                hitEffect = despawnEffect = new WaveEffect(){{
                                    sizeFrom = 5;
                                    sizeTo = 0;
                                    colorFrom = BioPal.cellBlueLight;
                                    colorTo = BioPal.cellBlue;
                                    sides = 5;
                                    rotateSpeed = 10f;
                                }};
                            }};
                        }};
                    }}
            );
        }};

        dissection = new ItemTurret("dissection"){{
            health = 1100;
            size = 3;
            requirements(turret, with(BioItems.magnesium, 200, BioItems.calciticFragment, 180, BioItems.potash, 120));
            float r = range = 80;
            shootSound = Sounds.missileLarge;
            inaccuracy = 1f;
            rotateSpeed = 2f;
            reload = 45;
            minWarmup = 0.90f;
            targetAir = false;
            targetGround = true;
            outlineColor = Color.valueOf("2b2626");
            shootEffect = BioFx.lightningSpiral;

            ammo(
                    BioItems.stemCapsule, new LightningLaserBulletType(){{
                        length = r - 5;
                        damage = 60f;
                        ammoMultiplier = 4f;
                        width = 9f;
                        reloadMultiplier = 1.3f;
                        serrations = 0;
                        fromColor = BioPal.supportGreenLight;
                        toColor = Pal.heal;
                        lightningColor = BioPal.supportGreenLight;
                        lightningLength = 15;
                        lightning = 1;
                        lightningCone = 0f;
                        lightningType = new BulletType(0.0001f, 2f){{
                            lifetime = Fx.lightning.lifetime;
                            hitEffect = despawnEffect = new WaveEffect(){{
                                sizeFrom = 5;
                                sizeTo = 0;
                                colorFrom = BioPal.supportGreenLight;
                                colorTo = Pal.heal;
                                sides = 3;
                                rotateSpeed = 10f;
                            }};
                            hittable = false;
                            lightColor = Color.white;
                            collidesAir = false;
                            buildingDamageMultiplier = 0.25f;
                        }};
                    }}
            );
        }};
        needle = new ItemTurret("needle"){{
            health = 890;
            size = 2;
            requirements(turret, with(BioItems.magnesium, 50, BioItems.potash, 25));

            range = 140;
            shootSound = Sounds.shootAlt;
            inaccuracy = 2f;
            rotateSpeed = 2f;
            reload = 15;
            minWarmup = 0.90f;
            targetAir = true;
            targetGround = true;

            shoot = new ShootBarrel(){{
                shots = 2;
                barrels = new float[]{2, 1, 0, -2, 1, 0};
            }};

            ammo(
                    BioItems.magnesium, new BasicBulletType(4f, 8){{
                        hitEffect = despawnEffect = shootEffect = new WaveEffect(){{
                            colorFrom = BioPal.magnesiumPurple;
                            colorTo = BioPal.magnesiumPurple;
                            sizeFrom = 0;
                            sizeTo = 3;
                            strokeFrom = 1;
                            strokeTo = 0;
                        }};

                        width = 10;
                        height = 10;
                        shrinkX = shrinkY = 0;
                        frontColor = BioPal.magnesiumPurpleLight;
                        backColor = BioPal.magnesiumPurple;
                        trailLength = 5;
                        trailWidth = 2;
                        trailColor = BioPal.magnesiumPurple;
                        trailInterval = 2f;
                        lifetime = 40f;
                        collidesAir = true;

                        hitSound = despawnSound = Sounds.bang;
                    }}
            );

            outlineColor = Color.valueOf("2b2626");

        }};

        //endregion

        //power
        rotorPipe = new PowerConduit("rotor-pipe"){{
            requirements(power, with(BioItems.calciticFragment, 50, BioItems.potash, 35));
            health = 600;
            size = 1;

            consumeLiquid(BioLiquids.hemoFluid, 0.3f);
            powerProduction = 0.15f;
            botColor = Color.valueOf("262525");
        }};
        //endregion

        //production
        hematicSieve = new GenericCrafter("hematic-sieve"){{
            requirements(Category.crafting, with(BioItems.magnesium, 50, BioItems.calciticFragment, 50));
            squareSprite = false;
            hasItems = true;
            liquidCapacity = 60f;
            craftTime = 2 * 60f;
            outputItem = new ItemStack(BioItems.potash, 3);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(BioLiquids.hemoFluid, 2), new DrawDefault());
            size = 3;
            health = 850;
            hasLiquids = true;
            craftEffect = new ParticleEffect(){{
                particles = 5;
                length = 8;
                sizeFrom = 3;
                sizeTo = 0;
                colorFrom = BioPal.bloodRedLight;
                colorTo = BioPal.bloodRed;

            }};

            consumeLiquid(BioLiquids.hemoFluid, 0.35f);
        }};

        hemoCrystallizer = new GenericCrafter("hemo-crystallizer"){{
            requirements(Category.crafting, with(BioItems.magnesium, 50, BioItems.calciticFragment, 50));
            squareSprite = false;
            hasItems = true;
            liquidCapacity = 60f;
            craftTime = 4 * 60f;
            outputItem = new ItemStack(BioItems.carminite, 3);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(BioLiquids.hemoFluid, 3), new DrawDefault());
            size = 4;
            health = 1120;
            hasLiquids = true;
            craftEffect = new ParticleEffect(){{
                particles = 5;
                length = 8;
                sizeFrom = 3;
                sizeTo = 0;
                colorFrom = BioPal.bloodRedLight;
                colorTo = BioPal.bloodRed;
            }};

            consumeLiquid(BioLiquids.hemoFluid, 0.40f);
            consumeItem(BioItems.magnesium, 1);
        }};
        //endregion

        //power
        //endregion

        //defense
        magnesiumWall = new Wall("magnesium-wall"){{
            researchCost = with(BioItems.magnesium, 300);
            requirements(Category.defense, with(BioItems.magnesium, 15));
            researchCost = with(BioItems.magnesium, 50);
            health = 250;
        }};

        largeMagnesiumWall = new Wall("large-magnesium-wall"){{
            researchCost = with(BioItems.magnesium, 1200);
            requirements(Category.defense, with(BioItems.magnesium, 15 * 4));
            health = 250 * 4;
            size = 2;
        }};
        //endregion

        //units
        experimentalManufacturer = new UnitFactory("experimental-manufacturer"){{
            requirements(Category.units, with(BioItems.magnesium, 120, BioItems.calciticFragment, 140, BioItems.potash, 50, BioItems.phosphorus, 32));
            size = 3;
            plans.add(new UnitPlan(BioUnits.smith, 60 * 28f, with(BioItems.potash, 15, BioItems.phosphorus, 45)));
            researchCostMultiplier = 0.8f;
            consumeLiquid(BioLiquids.hemoFluid, 0.4f);
        }};

        descentManufacturer = new UnitFactory("descent-manufacturer"){{
            researchCost = with(BioItems.magnesium, 300, BioItems.calciticFragment, 250);
            requirements(Category.units, with(BioItems.magnesium, 190, BioItems.calciticFragment, 140));
            size = 3;
            plans.add(new UnitPlan(BioUnits.strider, 60 * 15f, with(BioItems.magnesium, 35, BioItems.carbonicTissue, 15)));
            plans.add(new UnitPlan(BioUnits.scout, 60 * 12f, with(BioItems.magnesium, 20, BioItems.potash, 15)));
            consumeLiquid(BioLiquids.hemoFluid, 0.3f);
        }};

        osylithReformer = new Reconstructor("osylith-reformer"){{
            requirements(Category.units, with(BioItems.phosphorus, 40, BioItems.potash, 100, BioItems.calciticFragment, 120, BioItems.magnesium, 260));

            size = 4;
            consumeLiquid(BioLiquids.plasmoid, 1.2f);
            consumeItems(with(BioItems.carminite, 35, BioItems.phosphorus, 50));

            constructTime = 60f * 25f;

            upgrades.addAll(
                    new UnitType[]{BioUnits.scout, BioUnits.seer},
                    new UnitType[]{BioUnits.strider, BioUnits.nomad}
            );
            squareSprite = false;
        }};

        unitDocker = new UnitCargoLoader("unit-docker"){{
            researchCost = with(BioItems.magnesium, 120, BioItems.calciticFragment, 50);
            unitType = BioUnits.carrier;

            polyColor = BioPal.supportGreenLight;
            polySides = 3;
            polyRadius = 5f;
            polyStroke = 1.3f;

            requirements(Category.distribution, with(BioItems.magnesium, 50, BioItems.calciticFragment, 25));
            size = 2;
            buildTime = 60f * 8f;
            itemCapacity = 40;
            squareSprite = false;
        }};

        unitDischarger = new UnitCargoUnloadPoint("unit-discharger"){{
            researchCost = with(BioItems.magnesium, 85, BioItems.calciticFragment, 35);
            requirements(Category.distribution, with(BioItems.magnesium, 34, BioItems.calciticFragment, 15));
            size = 2;
            itemCapacity = 40;
            squareSprite = false;
        }};

        bioUnitSpawner = new BiologicalStaticSpawner("bio-unit-spawner"){{
            requirements(units, BuildVisibility.shown, with(BioItems.carbonicTissue, 1));
            health = 999999999;
            size = 8;
        }};
        //endregion

        //effect
        coreSight = new CoreBlock("core-sight"){{
            requirements(Category.effect, BuildVisibility.shown, with(BioItems.magnesium, 500, BioItems.carbonicTissue, 500, BioItems.calciticFragment, 250));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = BioUnits.watcher;
            health = 2130;
            itemCapacity = 4000;
            size = 3;
            squareSprite = false;

            unitCapModifier = 15;
        }};
        //endregion
    }
}
