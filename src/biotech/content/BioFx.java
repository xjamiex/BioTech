package biotech.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import java.util.Random;

import static arc.graphics.g2d.Draw.color;

public class BioFx {

    static int[] laserOffsets = new int[]{0, 1, 1, 0, -1, -1};
    static Random rand = new Random();
    public static Effect
        lightningSpiral = new Effect(20f, e -> {
            color(BioPal.supportGreenLight, Pal.heal, e.fin());
            Lines.stroke(5 * e.fout());
            Lines.beginLine();
            for (int i = 0; i <= 15; i++) {
                Tmp.v1.set(i, laserOffsets[i%6]).scl(Vars.tilesize).
                        rotate(getRandomNum(e.rotation + 5, e.rotation - 5)).
                        add(e.x, e.y);
                Lines.linePoint(Tmp.v1);
            }
            Lines.endLine();
        }),

        needleSpike = new Effect(50f, e -> {
            float offset = 4;
            color(BioPal.magnesiumPurple, Color.clear.add(0, 0, 0, 100), e.fin());
            Fill.tri(e.x, e.y,
                    e.x + getRandomNum(offset, -offset), e.y + getRandomNum(offset, -offset),
                    e.x + getRandomNum(offset, -offset), e.y + getRandomNum(offset, -offset));
        });

    public static float getRandomNum(float c, float f){
        return rand.nextFloat() * (c - f) + f;
    }
}
