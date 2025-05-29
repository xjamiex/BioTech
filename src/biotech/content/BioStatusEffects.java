package biotech.content;

import arc.graphics.Color;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class BioStatusEffects {
    public static StatusEffect
            overloaded;

    public static void load() {
        overloaded = new StatusEffect("status-overloaded"){{
            speedMultiplier = 0.7f;
            applyColor = BioPal.plasmoidBlueLight;
            reloadMultiplier = buildSpeedMultiplier = 0.4f;
            color = BioPal.plasmoidBlueLight;
            outline = true;
            effect = new ParticleEffect() {{
                particles = 6;
                colorFrom = BioPal.plasmoidBlueLight;
                colorTo = BioPal.plasmoidBlueLight;
                sizeFrom = 4;
                sizeTo = 0;
                length = 6;
            }};
        }};
    }
}
