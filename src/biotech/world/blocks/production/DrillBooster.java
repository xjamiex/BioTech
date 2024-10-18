package biotech.world.blocks.production;

import arc.struct.EnumSet;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;

import static mindustry.Vars.world;

public class DrillBooster extends Block {
    public DrillBooster(String name) {
        super(name);
        rotate = true;
        update = true;
        solid = true;

        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
    }
}
