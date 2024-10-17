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

    float boostDrill(int tx, int ty, int rotation){
        float eff = 0f;
        int cornerX = tx - (size-1)/2, cornerY = ty - (size-1)/2, s = size;

        for(int i = 0; i < size; i++){
            int rx = 0, ry = 0;

            switch(rotation){
                case 0 -> {
                    rx = cornerX + s;
                    ry = cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    ry = cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    ry = cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    ry = cornerY - 1;
                }
            }

            Tile other = world.tile(rx, ry);
            if(other != null && other.solid()){
                if(other.block() instanceof BoostableDrill){
                    ((BoostableDrill.boostableDrillBuild) other.build).boostedTier = 2;
                }
            }
        }
        return eff;
    }

    public class DrillBoosterBuild extends Building{
        @Override
        public void updateTile() {
            super.updateTile();
            boostDrill(tile.x, tile.y, rotation);
        }
    }
}
