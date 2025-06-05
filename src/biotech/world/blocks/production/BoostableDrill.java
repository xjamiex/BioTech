package biotech.world.blocks.production;

import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.util.Log;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Drill;

import static mindustry.Vars.world;

public class BoostableDrill extends Drill {

    public BoostableDrill(String name) {
        super(name);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return true;
    }

    @Override
    public boolean canMine(Tile tile) {
        if (tile == null || tile.block().isStatic()) return false;
        Item drops = tile.drop();
        return drops != null && drops.hardness <= (tier + checkBooster(tile)) && drops != blockedItem;
    }

    public int checkBooster(Tile tile) {
        int boost = 0;
        for (Point2 edge : Edges.getEdges(this.size)) {
            //what am I doing with my life
            if (tile.build == null) continue;
            if (tile.build.tile.nearby(edge).build == null) continue;
            if (tile.build.tile.nearby(edge).build instanceof DrillUpgrader.DrillUpgraderBuild build) {
                if (build.canBoost()) boost = build.boost;
            }
        }
        return boost;
    }

    public class BoostableDrillBuild extends DrillBuild {

        public int lastChange = -2;

        @Override
        public void updateTile() {
            super.updateTile();
            if (lastChange != world.tileChanges) {
                lastChange = world.tileChanges;
                countOre(tile);
            }
        }
    }
}
