package biotech.type.weapon;

import biotech.BioVars;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class ShockwaveWeapon extends Weapon {
    public float boomIntensity = 0.2f, boomDuration = 60f;
    public static Effect boomEffect;

    public ShockwaveWeapon(String name) {
        super(name);
    }

    @Override
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation) {
        super.shoot(unit, mount, shootX, shootY, rotation);

        BioVars.shockwaveRenderer.boom(boomIntensity, boomDuration);
        boomEffect.at(unit.x, unit.y);
    }
}
