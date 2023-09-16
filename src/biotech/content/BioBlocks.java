package biotech.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
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
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.Drill;
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

            //defense
            boneWall, boneWallLarge,

            //liquids
            liquidPipe,

            //drill
            bioDrill,

            //env
            fleshFloor, oreMagnesium,

            //turret
            alive,

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

        //defense
        int wallHealthMultiplier = 4;
        boneWall = new Wall("bone-wall"){{
            requirements(Category.defense, with(BioItems.boneFragment, 4));
            health = 160 * wallHealthMultiplier;
            envDisabled |= Env.scorching;
        }};

        boneWallLarge = new Wall("bone-wall-large"){{
            requirements(Category.defense, with(BioItems.boneFragment, 16));
            health = 160 * 4 * wallHealthMultiplier;
            envDisabled |= Env.scorching;
            size = 2;
        }};

        //liquids
        liquidPipe = new Conduit("liquid-pipe"){{
            requirements(Category.liquid, with(BioItems.boneFragment, 1));
            health = 143;
            botColor = Color.valueOf("262525");
        }};

        //drills
        bioDrill = new Drill("bio-drill"){{
            requirements(Category.production, with(BioItems.magnesium, 45, BioItems.boneFragment, 25));
            tier = 1;
            drillTime = 200;
            size = 3;
        }};


        //environment
        fleshFloor = new Floor("floor-flesh", 4){{
            playerUnmineable = true;
            attributes.set(flesh, 1f);
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

        aircraftManufacturer = new UnitFactory("aircraft-manufacturer"){{
            requirements(Category.units, with(BioItems.magnesium, 120, BioItems.flesh, 150, BioItems.boneFragment, 140));

            size = 3;
            plans.add(new UnitPlan(BioUnits.extractor, 60f * 35f, with(BioItems.magnesium, 40, BioItems.boneFragment, 25)));
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
