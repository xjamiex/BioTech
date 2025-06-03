package biotech.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.EnumSet;
import arc.util.Log;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;

public class DrillUpgrader extends Block {

    public DrillUpgrader(String name) {
        super(name);
        rotate = false;
        update = true;
        solid = true;
        drawArrow = false;
        hasPower = true;
        consumesPower = true;

        buildType = DrillUpgraderBuild::new;

        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
    }

    public static class DrillUpgraderBuild extends Building {

        public int boost = 1;

        public boolean canBoost() {
            return power.status == 1.0f;
        }
    }
}
