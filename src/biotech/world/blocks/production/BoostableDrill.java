package biotech.world.blocks.production;

import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.util.Log;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Drill;

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
            //what am i doing with my life
            if (tile.build == null) continue;
            if (tile.build.tile.nearby(edge).build == null) continue;
            if (tile.build.tile.nearby(edge).build instanceof DrillUpgrader.DrillUpgraderBuild build) {
                if (build.canBoost()) boost = build.boost;
            }
        }
        return boost;
    }

    public class BoostableDrillBuild extends DrillBuild {

        @Override
        public void updateTile() {
            countOre(tile);
            if(timer(timerDump, dumpTime)) {
                dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);
            }
            Log.info(dominantItems + " " + efficiency);

            if(dominantItem == null) {
                return;
            }

            timeDrilled += warmup * delta();

            float delay = getDrillTime(dominantItem);

            if(items.total() < itemCapacity && dominantItems > 0 && efficiency > 0){
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                lastDrillSpeed = (speed * dominantItems * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * dominantItems * speed * warmup;

                if(Mathf.chanceDelta(updateEffectChance * warmup))
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
            }else{
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if(dominantItems > 0 && progress >= delay && items.total() < itemCapacity){
                offload(dominantItem);

                progress %= delay;

                if(wasVisible && Mathf.chanceDelta(updateEffectChance * warmup)) drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
            }
        }
    }
}
