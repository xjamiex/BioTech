package biotech.content;

import arc.graphics.Color;
import biotech.type.world.enviroment.ExplosiveBlock;
import biotech.type.world.enviroment.NerveProtrusion;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.BufferedItemBridge;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.SteamVent;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.WallCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.UnitCargoLoader;
import mindustry.world.blocks.units.UnitCargoUnloadPoint;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.Category.production;
import static mindustry.type.Category.turret;
import static mindustry.type.ItemStack.with;

public class BioBlocks {
    public static Block
            //liquid
            bioPump, spike,

            //liquids
            liquidPipe, liquidSplitter, liquidOverpass,

            //distribution
            magnesiumConveyor, splitter, conveyorOverpass,

            //drill
            bioDrill, boneCrusher,

            //env
            flesh, flint, bone, myostone,
            oreMagnesium,
            fleshWall, boneWall, decayedBoneWall, dolomiteWall, flintWall, floursparWall, myostoneWall,
            poreHole,

            //props
            nerveProtrusion, fleshGrowth,

            //turret
            alive,

            //production
            hematicSieve,

            //defense
            magnesiumWall, largeMagnesiumWall,

            //units
            aircraftManufacturer, unitDocker, unitDischarger,
            groundManufacturer,

            //effect
            coreSight
    ;

    public static final Attribute
            meat = Attribute.add("meat"),
            calcitic = Attribute.add("calcitic");

    public static void load() {

        //liquid
        bioPump = new AttributeCrafter("bio-pump"){{
            researchCost = with(BioItems.magnesium, 20);
            requirements(production, with(BioItems.magnesium, 45));
            attribute = meat;
            group = BlockGroup.drills;
            displayEfficiency = false;
            minEfficiency = 0.0001f;
            baseEfficiency = 0f;
            boostScale = 1f / 4f;
            size = 2;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            craftTime = 120f;
            itemCapacity = 30;
            ignoreLiquidFullness = true;

            hasLiquids = true;
            outputLiquid = new LiquidStack(BioLiquids.hemoFluid, 1f / 2f / 2f);
            outputItem = new ItemStack(BioItems.carbonicTissue, 1);
            liquidCapacity = 40f;
            squareSprite = false;
        }};

        liquidPipe = new Conduit("liquid-pipe"){{
            researchCost = with(BioItems.calciticFragment, 6);
            requirements(Category.liquid, with(BioItems.calciticFragment, 1));
            health = 143;
            botColor = Color.valueOf("262525");
        }};

        liquidSplitter = new LiquidRouter("liquid-splitter"){{
            requirements(Category.liquid, with(BioItems.calciticFragment, 2));
            health = 143;
            size = 1;
        }};

        liquidOverpass = new LiquidBridge("liquid-overpass"){{
            requirements(Category.liquid, with(BioItems.calciticFragment, 5));
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 4;
            hasPower = false;
        }};
        //endregion

        //distribution
        magnesiumConveyor = new Conveyor("magnesium-convayor"){{
            researchCost = with(BioItems.magnesium, 6);
            bridgeReplacement = BioBlocks.conveyorOverpass;
            requirements(Category.distribution, with(BioItems.magnesium, 1));
            health = 35;
            speed = 0.04f;
            displayedSpeed = 8f;
        }};

        splitter = new Router("splitter"){{
            researchCost = with(BioItems.magnesium, 15);
            requirements(Category.distribution, with(BioItems.magnesium, 2));
            health = 40;
        }};

        conveyorOverpass = new BufferedItemBridge("conveyor-overpass"){{
            requirements(Category.distribution, with(BioItems.magnesium, 6));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;
        }};
        //endregion

        //drills
        bioDrill = new Drill("bio-drill"){{
            researchCost = with(BioItems.magnesium, 10, BioItems.calciticFragment, 10);
            //more expensive
            requirements(Category.production, with(BioItems.magnesium, 65, BioItems.calciticFragment, 35));
            tier = 1;
            drillTime = 650;
            size = 3;
            squareSprite = false;
        }};

        boneCrusher = new WallCrafter("bone-crusher"){{
            researchCost = with(BioItems.magnesium, 15);
            requirements(Category.production, with(BioItems.magnesium, 40));
            drillTime = 110f;
            size = 2;
            attribute = calcitic;
            output = BioItems.calciticFragment;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
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

        boneWall = new StaticWall("bone-wall"){{
            itemDrop = BioItems.calciticFragment;
        }};

        decayedBoneWall = new StaticWall("decayed-bone-wall"){{
            itemDrop = BioItems.calciticFragment;
            attributes.set(calcitic, 1);
        }};

        dolomiteWall = new StaticWall("flint-wall");
        flintWall = new StaticWall("dolomite-wall");
        floursparWall = new StaticWall("flourspar-wall");
        fleshWall = new StaticWall("flesh-wall");
        myostoneWall = new StaticWall("myostone-wall");

        poreHole = new SteamVent("pore-hole"){{
            parent = blendGroup = flesh;
            variants = 3;
            effectSpacing = 100f;
            effectColor = Color.valueOf("a69780");
        }};

        oreMagnesium = new OreBlock("ore-magnesium"){{
            itemDrop = BioItems.magnesium;
        }};
        //endregion

        //props
        nerveProtrusion = new NerveProtrusion("nerve-protrusion");
        fleshGrowth = new ExplosiveBlock("flesh-growth"){{
            health = 200;
            size = 1;
            new ParticleEffect(){{
                colorFrom = BioPal.bloodRedLight;
                colorTo = BioPal.bloodRed;
                sizeFrom = 8;
                sizeTo = 0;
                particles = 5;
                length = 35f;
            }};
        }};
        //endregion

        //turrets
        alive = new ItemTurret("alive"){{
            health = 1020;
            size = 3;
            buildCostMultiplier = 10/4.2f;
            researchCostMultiplier = 0.2f;
            requirements(turret, with(BioItems.calciticFragment, 60, BioItems.magnesium, 60));
            liquidCapacity = 10;
            maxAmmo = 15;

            range = 250;
            shootY = 0.7f;
            shootSound = Sounds.shootBig;
            inaccuracy = 2f;
            rotateSpeed = 2f;
            reload = 120;
            minWarmup = 0.90f;
            targetAir = false;
            targetGround = true;

            ammo(
                    BioItems.carbonicTissue, new BasicBulletType(3f, 55){{
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
                            sizeFrom = 15;
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
                        lifetime = 80f;

                        splashDamage = 25;
                        splashDamageRadius = 35f;

                        hitSound = despawnSound = Sounds.artillery;
                    }}
            );

            outlineColor = Color.valueOf("2b2626");

        }};

        spike = new ItemTurret("spike"){{
            health = 1120;
            size = 3;
            buildCostMultiplier = 10/4.2f;
            researchCostMultiplier = 0.2f;
            requirements(turret, with(BioItems.calciticFragment, 50, BioItems.magnesium, 60));
            maxAmmo = 25;

            range = 270;
            shootY = 0.7f;

            shootSound = Sounds.shootAlt;
            inaccuracy = 10f;
            rotateSpeed = 2f;
            reload = 86;
            minWarmup = 0.90f;
            targetAir = true;
            targetGround = false;


            shoot.shots = 3;
            shoot.shotDelay = 3f;

            ammo(
                    BioItems.calciticFragment, new BasicBulletType(5.5f, 25){{
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
        //endregion

        //production
        hematicSieve = new GenericCrafter("hematic-sieve"){{
            researchCost = with(BioItems.magnesium, 100, BioItems.calciticFragment, 100);
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
        //endregion

        //defense
        magnesiumWall = new Wall("magnesium-wall"){{
            requirements(Category.defense, with(BioItems.magnesium, 15));
            researchCost = with(BioItems.magnesium, 50);
            health = 250;
        }};

        largeMagnesiumWall = new Wall("large-magnesium-wall"){{
            requirements(Category.defense, with(BioItems.magnesium, 15 * 4));
            health = 250 * 4;
            size = 2;
        }};
        //endregion

        //units
        aircraftManufacturer = new UnitFactory("aircraft-manufacturer"){{
            // TODO: CHANGE RESEARCH / BUILD COST
            researchCost = with(BioItems.magnesium, 340, BioItems.carbonicTissue, 300, BioItems.calciticFragment, 450);
            requirements(Category.units, with(BioItems.magnesium, 120, BioItems.carbonicTissue, 150, BioItems.calciticFragment, 140));
            size = 3;
            plans.add(new UnitPlan(BioUnits.scout, 60 * 28f, with(BioItems.magnesium, 35, BioItems.carbonicTissue, 15)));
            researchCostMultiplier = 0.5f;
            consumeLiquid(BioLiquids.hemoFluid, 0.2f);
        }};

        groundManufacturer = new UnitFactory("ground-manufacturer"){{
            researchCost = with(BioItems.magnesium, 340, BioItems.potash, 200, BioItems.calciticFragment, 450);
            requirements(Category.units, with(BioItems.magnesium, 120, BioItems.potash, 80, BioItems.calciticFragment, 140));
            size = 3;
            plans.add(new UnitPlan(BioUnits.strider, 60 * 25f, with(BioItems.magnesium, 35, BioItems.carbonicTissue, 15)));
            consumeLiquid(BioLiquids.hemoFluid, 0.2f);
        }};

        unitDocker = new UnitCargoLoader("unit-docker"){{
            researchCost = with(BioItems.magnesium, 30, BioItems.calciticFragment, 45);
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
            researchCost = with(BioItems.magnesium, 25, BioItems.calciticFragment, 35);
            requirements(Category.distribution, with(BioItems.magnesium, 34, BioItems.calciticFragment, 15));
            size = 2;
            itemCapacity = 40;
            squareSprite = false;
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
