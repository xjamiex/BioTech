package biotech.type;

import arc.graphics.Color;
import arc.math.Interp;
import biotech.entities.GoreParticleEffect;
import biotech.type.unit.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class BiologicalUnitType extends BioTechUnitType{

    public BiologicalUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2e0808");
        useUnitCap = false;

        deathExplosionEffect = new GoreParticleEffect(){{
            interp = Interp.exp5;
            spin = 15;
            lifetime = 100;
            colorFrom = colorTo = Color.white;
            length = 40;
            layer = Layer.groundUnit - 1;
            particles = 1;
            sizeFrom = 10;
            sizeTo = 0;
        }};
    }
}
