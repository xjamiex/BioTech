package biotech.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.EnumSet;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;

public class DrillUpgrader extends Block {

    public static TextureRegion arrowRegion;
    public DrillUpgrader(String name) {
        super(name);
        rotate = false;
        update = true;
        solid = true;
        drawArrow = false;

        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
    }
}
