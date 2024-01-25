package biotech.type.world.enviroment;

import arc.Events;
import biotech.BioTech;
import biotech.content.BioUnits;
import mindustry.content.UnitTypes;
import mindustry.core.World;
import mindustry.game.EventType;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class NestBlock extends ExplosiveBlock {
    public NestBlock(String name) {
        super(name);
        explodeDamage = 0;
        buildType = NestBlockBuild::new;
    }

    public class NestBlockBuild extends ExplosiveBlockBuild {
        @Override
        public void onDestroyed() {
            super.onDestroyed();
            spawnUnits();
        }
        public void spawnUnits() {
            Unit unit = BioUnits.scout.create(team);
            unit.rotation(90f);
            unit.set(x, y);
            unit.add();
        }
    }
}
