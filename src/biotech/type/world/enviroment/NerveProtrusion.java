package biotech.type.world.enviroment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;

public class NerveProtrusion extends Block{

    public float shadowOffset = -4f;
    public TextureRegion shadow;
    public NerveProtrusion(String name) {
        super(name);
        solid = true;
        clipSize = 90;
    }

    @Override
    public void load() {
        super.load();
         shadow = Core.atlas.find(name + "-shadow");
    }

    @Override
    public void drawBase(Tile tile) {
        float   x = tile.worldx(), y = tile.worldy(),
                rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 1f) + Mathf.sin(Time.time + x, 50f, 0.8f) + Mathf.sin(Time.time + x, 50f, 0.4f),
                w = region.width * region.scl(), h = region.height * region.scl(),
                scl = 30f, mag = 0.2f;

        if(shadow.found()){
            Log.info("HOLY FUCK THERE IS A SHADOW");
            Draw.z(Layer.power - 1);
            Draw.rect(shadow, tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot);
        }

        Draw.z(Layer.power + 1);
        Draw.rect(region, x, y, w, h, rot);
    }
}
