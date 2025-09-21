package biotech.type.weapon;

import biotech.BioVars;
import mindustry.entities.Mover;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class ShockwaveWeapon extends Weapon {
    public float boomIntensity = 0.2f, boomDuration = 60f;

    public ShockwaveWeapon(String name) {
        super(name);
    }

    @Override
    protected void bullet(Unit unit, WeaponMount mount, float xOffset, float yOffset, float angleOffset, Mover mover) {
        super.bullet(unit, mount, xOffset, yOffset, angleOffset, mover);

        BioVars.shockwaveRenderer.boom(boomIntensity, boomDuration);
    }
}
