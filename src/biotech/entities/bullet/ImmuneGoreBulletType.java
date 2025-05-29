package biotech.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;

public class ImmuneGoreBulletType extends BasicBulletType {

    TextureRegion[][] gore;
    Rand rand = new Rand();

    public ImmuneGoreBulletType(float speed, float damage) {
        super(speed, damage);
        lightOpacity = 0;
    }

    @Override
    public void load() {
        super.load();
        gore = Core.atlas.find("biotech-immune-gore").split(32, 32);
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        if(b == null) return;
        int v1 = rand.random(0 , 3);
        int v2 = rand.random(0 , 3);
        b.data = gore[v1][v2];
    }

    @Override
    public void draw(Bullet b) {
        Draw.color(Color.white);
        Draw.z(Layer.blockUnder);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);

        Draw.rect((TextureRegion) b.data, b.x, b.y, width, height, b.rotation() + offset);

        Draw.reset();
    }
}
