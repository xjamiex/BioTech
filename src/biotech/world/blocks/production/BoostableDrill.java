package biotech.world.blocks.production;

import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.*;
import arc.util.Log;
import biotech.world.blocks.production.DrillUpgrader.*;
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
                 boost = build.boost;
            }
        }
        return boost;
    }

    public class BoostableDrillBuild extends DrillBuild {
        public Seq<DrillUpgraderBuild> linked = new Seq<>();

        public int lastChange = -2;

        public void updateOreCount(){
            countOre(tile);
            dominantItem = returnItem;
            dominantItems = returnCount;
        }

        public void updateModules(DrillUpgraderBuild build){
            linked.addUnique(build);
            updateOreCount();
        }

        public void removeModule(DrillUpgraderBuild build){
            linked.remove(build);
            updateOreCount();
        }

        public int highestTier(){
            if(linked.size <= 0) return 0;
            linked.sort( f -> f.boost);
            return  linked.first().boost;
        }


        @Override
        public float efficiencyScale(){
            return moduleEfficiency() ;
        }

        public float moduleEfficiency(){
            if(linked.size <= 0) return 1f;

            float[] total = {0};
            for(DrillUpgraderBuild m : linked) total[0] += m.efficiency;
            return total[0] / linked.size ;
        }


        @Override
        public void placed(){
            super.placed();

            countOre(tile);
        }

        @Override
        public void updateTile() {
            if (lastChange != world.tileChanges) {
                lastChange = world.tileChanges;
                updateOreCount();
            }
            super.updateTile();
        }
    }
}
