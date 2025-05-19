package biotech.world.blocks.power;

import arc.Core;
import arc.struct.EnumSet;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class PowerConduit extends ArmoredConduit {
    public float powerProduction = 0f;
    public Stat generationType = Stat.basePowerGeneration;

    public PowerConduit(String name) {
        super(name);
        outputsPower = true;
        hasPower = true;
        consumesPower = false;
        sync = true;
        baseExplosiveness = 5f;
        flags = EnumSet.of(BlockFlag.generator);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(generationType, powerProduction * 60.0f, StatUnit.powerSecond);
    }

    @Override
    public void setBars() {
        super.setBars();

        if(hasPower && outputsPower){
            addBar("power", (ConduitBuild entity) -> new Bar(() ->
                    Core.bundle.format("bar.poweroutput",
                            Strings.fixed(entity.getPowerProduction() * 60 * entity.timeScale(), 1)),
                    () -> Pal.powerBar,
                    () -> entity.efficiency));
        }
    }

    public class PowerConduitBuild extends ConduitBuild {
        @Override
        public float getPowerProduction() {
            return powerProduction * liquids.currentAmount();
        }
    }
}
