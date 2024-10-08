package biotech.entities.bullet;

import arc.util.Nullable;
import mindustry.core.World;
import mindustry.entities.bullet.ContinuousFlameBulletType;
import mindustry.game.Team;
import mindustry.gen.Building;

import static mindustry.Vars.world;

public class BlockedContinuousFlameBulletType extends ContinuousFlameBulletType {

    public BlockedContinuousFlameBulletType(float damage) {
        super(damage);
    }
}
