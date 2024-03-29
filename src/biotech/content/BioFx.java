package biotech.content;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import java.io.File;

import static arc.graphics.g2d.Draw.color;

public class BioFx {
    public static Effect
        lightningSpiral = new Effect(100f, effectContainer -> {
            color(BioPal.supportGreenLight, Pal.heal, effectContainer.fin());
            float w = 1f + 5 * effectContainer.fout();
            Fill.circle(effectContainer.x, effectContainer.y, w);
        });
}
