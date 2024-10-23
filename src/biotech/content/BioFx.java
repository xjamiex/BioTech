package biotech.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import java.util.Random;

import static arc.graphics.g2d.Draw.alpha;
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

        seerCharge = new Effect(40, e -> {
            color(BioPal.bloodRedLight);
            Lines.stroke(e.fout() * 4);
            Lines.poly(e.x, e.y, (int) (8 * e.fout(Interp.exp10Out)), e.fout(Interp.exp10Out) * 6, e.fout() * 360);
        }),

        seerWarmup = new Effect(10, e -> {
            color(BioPal.bloodRedLight);
            Lines.stroke(e.fin() * 4);
            Lines.poly(e.x, e.y, 8, 6, e.fout() * 360);
        }),

        nomadBeginWarmup = new Effect(20, e -> {
            Draw.color(BioPal.bloodRedLight);
            Lines.stroke(e.fin() * 3);
            Lines.circle(e.x, e.y, e.fin(Interp.exp10) * 5);
            Fill.circle(e.x, e.y, e.fin(Interp.exp10) * 3);
        }),

        nomadWarmup = new Effect(90, e -> {
            color(BioPal.bloodRedLight);
            for(int i = 0; i <= 4; i++) {
                Drawf.tri(e.x, e.y, 4, 20 * e.fin(), i * 90 + (e.fin() * 200));
            }

            Lines.stroke(3);
            Lines.circle(e.x, e.y, 5);
            Fill.circle(e.x, e.y, 2);
        }),

        nomadRelease = new Effect(10, e -> {
            color(BioPal.bloodRedLight);
            for(int i = 0; i <= 4; i++) {
                Drawf.tri(e.x, e.y, 4, 20 * e.fout(Interp.exp10Out), i * 90 + (e.fin() * 50));
            }

            Lines.stroke(e.fout() *3);
            Lines.circle(e.x, e.y, e.fout(Interp.exp10Out) * 5);
            Fill.circle(e.x, e.y, e.fout(Interp.exp10Out) * 2);
        });

    public static Effect fourSpike(float width, float length){
        return new Effect(60, e -> {
            color(Color.white);
            for(int i = 0; i <= 4; i++) {
                Drawf.tri(e.x, e.y, width * 0.5f, length * e.fout(), i * 90);
            }
            color(BioPal.bloodRedLight);
            for(int i = 0; i <= 4; i++) {
                Drawf.tri(e.x, e.y, width, length * e.fout(), i * 90);
            }
        });
    }

    public static float getRandomNum(float c, float f){
        return rand.nextFloat() * (c - f) + f;
    }
}
