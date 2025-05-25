package biotech.entities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Nullable;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.Tex;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;

public class GoreParticleEffect extends ParticleEffect {

    private @Nullable TextureRegion[][] tex;
    private static final Rand rand = new Rand();
    private static final Vec2 rv = new Vec2();


    TextureRegion outlineRegion;

    @Override
    public void render(EffectContainer e) {
        if(tex == null) tex = Core.atlas.find("biotech-immune-gore").split(32, 32);

        int flip = casingFlip ? -Mathf.sign(e.rotation) : 1;
        float rotation = rand.random(0, 360);
        float rawfin = e.fin();
        float fout = e.fout(interp);
        float rad = sizeInterp.apply(sizeFrom, sizeTo, Mathf.curve(rawfin, sizeChangeStart / lifetime, 1f)) * 2;
        float ox = e.x + Angles.trnsx(rotation, offsetX * flip, offsetY), oy = e.y + Angles.trnsy(rotation, offsetX * flip, offsetY);

        Draw.color(colorFrom, colorTo, fout);
        Color lightColor = this.lightColor == null ? Draw.getColor() : this.lightColor;

        if(line){
            Lines.stroke(sizeInterp.apply(strokeFrom, strokeTo, rawfin));
            float len = sizeInterp.apply(lenFrom, lenTo, rawfin);

            rand.setSeed(e.id);
            for(int i = 0; i < particles; i++){
                float l = length * fout;
                rv.trns(rotation + rand.range(cone), !randLength ? l : rand.random(l));
                float x = rv.x, y = rv.y;

                Lines.lineAngle(ox + x, oy + y, Mathf.angle(x, y), len, cap);
            }
        }else{
            rand.setSeed(e.id);
            for(int i = 0; i < particles; i++){
                float l = length * fout + baseLength;
                rv.trns(rotation + rand.range(cone), !randLength ? l : rand.random(l));
                float x = rv.x, y = rv.y;

                int regionId = rand.random(0, 3);

                Draw.rect(tex[regionId][regionId], ox + x, oy + y, rad, rad / tex[regionId][regionId].ratio(), rand.random(0, 360) + e.time * spin * e.fin());
            }
        }
    }
}
