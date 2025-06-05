package biotech.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.*;
import arc.struct.EnumSet;
import arc.util.Log;
import biotech.world.blocks.production.BoostableDrill.*;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.*;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;

import static mindustry.Vars.world;

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
        public int lastChange = -2;

        public int boost = 1;

        @Override
        public void placed() {
            super.placed();

            updateLinks();
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();

            updateLinks();
        }

        public void updateLinks(){
            for (Point2 edge : Edges.getEdges(block.size)) {
                if (tile.build == null) continue;
                if (tile.build.tile.nearby(edge).build == null) continue;
                if(tile.build.tile.nearby(edge).build instanceof  BoostableDrillBuild b){
                    b.updateModules(this);
                }
            }
        }

        @Override
        public void onRemoved(){
            super.onRemoved();

            for (Point2 edge : Edges.getEdges(block.size)) {
                if (tile.build == null) continue;
                if (tile.build.tile.nearby(edge).build == null) continue;
                if(tile.build.tile.nearby(edge).build instanceof  BoostableDrillBuild b){
                    b.removeModule(this);
                }
            }
        }

        @Override
        public void updateTile() {
            if (lastChange != world.tileChanges) {
                lastChange = world.tileChanges;
                updateLinks();
            }
        }

    }
}
