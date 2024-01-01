package biotech.type.world.enviroment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;

public class NerveProtrusion extends Block{

    public float shadowOffset = -4f;
    public TextureRegion shadow = Core.atlas.find("@");
    public NerveProtrusion(String name) {
        super(name);
        solid = true;
        clipSize = 90;
    }

    @Override
    public void drawBase(Tile tile) {
        float x = tile.worldx(), y = tile.worldy(), rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f), w = region.width * region.scl(), h = region.height * region.scl(), scl = 30f, mag = 0.2f;

        if(shadow.found()){
            Draw.z(Layer.power - 1);
            Draw.rect(shadow, tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot);
        }

        Draw.z(Layer.power + 1);
        Draw.rect(region, x, y, w, h, rot);
    }
}
