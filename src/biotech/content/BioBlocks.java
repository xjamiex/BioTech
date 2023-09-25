package biotech.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.SteamVent;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.WallCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.UnitCargoLoader;
import mindustry.world.blocks.units.UnitCargoUnloadPoint;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.*;

import static mindustry.type.Category.production;
import static mindustry.type.Category.turret;
import static mindustry.type.ItemStack.with;

public class BioBlocks {
    public static Block
            //liquid
            bioPump, spike,

            //liquids
            liquidPipe, liquidPipeRouter,

            //distribution
            magnesiumConvayor,

            //drill
            bioDrill, boneCrusher,

            //env
            fleshFloor, oreMagnesium, fleshWall, boneWall, poreHole,

            //turret
            alive,

            //defense
            magnesiumWall, largeMagnesiumWall,

            //units
            aircraftManufacturer, unitDocker, unitDischarger,

            //effect
            coreSight
    ;

    public static final Attribute
            flesh = Attribute.add("flesh");

    public static void load() {
        //liquid
        bioPump = new AttributeCrafter("bio-pump"){{
            requirements(production, with(BioItems.magnesium, 45));
            attribute = flesh;
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
            outputLiquid = new LiquidStack(BioLiquids.blood, 1f / 2f / 2f);
            outputItem = new ItemStack(BioItems.flesh, 1);
            liquidCapacity = 40f;
            squareSprite = false;
        }};

        //liquids
        liquidPipe = new Conduit("liquid-pipe"){{
            requirements(Category.liquid, with(BioItems.boneFragment, 1));
            health = 143;
            botColor = Color.valueOf("262525");
        }};

        //distribution
        magnesiumConvayor = new Conveyor("magnesium-convayor"){{
            requirements(Category.distribution, with(BioItems.magnesium, 1));
            health = 35;
            speed = 0.04f;
            displayedSpeed = 8f;
        }};

        liquidPipeRouter = new LiquidRouter("liquid-pipe-router"){{
            requirements(Category.liquid, with(BioItems.boneFragment, 5));
            health = 143;
            size = 1;
        }};

        //drills
        bioDrill = new Drill("bio-drill"){{
            //more expensive
            requirements(Category.production, with(BioItems.magnesium, 45, BioItems.boneFragment, 25));
            tier = 1;
            drillTime = 500;
            size = 3;
            squareSprite = false;
        }};

        boneCrusher = new WallCrafter("bone-crusher"){{
            requirements(Category.production, with(BioItems.magnesium, 40));

            drillTime = 110f;
            size = 2;
            attribute = BioAttributes.bone;
            output = BioItems.boneFragment;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
        }};


        //environment
        fleshFloor = new Floor("floor-flesh", 4){{
            playerUnmineable = true;
            attributes.set(flesh, 1f);
        }};

        fleshWall = new StaticWall("flesh-wall");

        boneWall = new StaticWall("bone-wall"){{
            itemDrop = BioItems.boneFragment;
            attributes.set(BioAttributes.bone, 1);
        }};

        poreHole = new SteamVent("pore-hole"){{
            parent = blendGroup = fleshFloor;
            variants = 3;
            effectSpacing = 60f;
            effectColor = Color.valueOf("a69780");
        }};

        oreMagnesium = new OreBlock("ore-magnesium"){{
            itemDrop = BioItems.magnesium;
        }};

        //turrets
        alive = new ItemTurret("alive"){{
            health = 1020;
            size = 3;
            buildCostMultiplier = 10/4.2f;
            requirements(turret, with(BioItems.boneFragment, 120, BioItems.flesh, 60, BioItems.magnesium, 75));
            consumeLiquid(BioLiquids.blood, 0.4f);
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
                    BioItems.flesh, new BasicBulletType(3f, 55){{
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
            requirements(turret, with(BioItems.boneFragment, 50, BioItems.flesh, 100, BioItems.magnesium, 125));
            consumeLiquid(BioLiquids.blood, 0.3f);
            maxAmmo = 25;

            range = 300;
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
                    BioItems.boneFragment, new BasicBulletType(5.5f, 25){{
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

                        pierce = true;

                        hitSound = despawnSound = Sounds.bang;
                    }}
            );

            outlineColor = Color.valueOf("2b2626");

        }};

        magnesiumWall = new Wall("magnesium-wall"){{
            requirements(Category.defense, with(BioItems.magnesium, 45));
            health = 250;
        }};

        largeMagnesiumWall = new Wall("large-magnesium-wall"){{
            requirements(Category.defense, with(BioItems.magnesium, 45 * 4));
            health = 250 * 4;
            size = 2;
        }};

        aircraftManufacturer = new UnitFactory("aircraft-manufacturer"){{
            requirements(Category.units, with(BioItems.magnesium, 120, BioItems.flesh, 150, BioItems.boneFragment, 140));

            size = 3;
            plans.add(new UnitPlan(BioUnits.extractor, 60f * 35f, with(BioItems.magnesium, 40, BioItems.boneFragment, 25)));
            plans.add(new UnitPlan(BioUnits.scout, 60 * 28f, with(BioItems.magnesium, 35, BioItems.flesh, 15)));
            researchCostMultiplier = 0.5f;
            consumeLiquid(BioLiquids.blood, 0.2f);
        }};

        unitDocker = new UnitCargoLoader("unit-docker"){{
            unitType = BioUnits.carrier;

            polyColor = BioPal.supportGreenLight;
            polySides = 3;
            polyRadius = 5f;
            polyStroke = 1.3f;

            requirements(Category.distribution, with(BioItems.magnesium, 50));
            size = 2;
            buildTime = 60f * 8f;
            consumeLiquid(BioLiquids.blood, 10f / 60f);
            itemCapacity = 40;
            squareSprite = false;
        }};

        unitDischarger = new UnitCargoUnloadPoint("unit-discharger"){{
            requirements(Category.distribution, with(BioItems.magnesium, 34));
            size = 2;
            itemCapacity = 40;
            squareSprite = false;
        }};

        coreSight = new CoreBlock("core-sight"){{
            requirements(Category.effect, BuildVisibility.shown, with(BioItems.magnesium, 500, BioItems.flesh, 500, BioItems.boneFragment, 250));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = BioUnits.watcher;
            health = 2130;
            itemCapacity = 4000;
            size = 3;

            unitCapModifier = 8;
        }};
    }
}
