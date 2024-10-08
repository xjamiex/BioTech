package biotech.world.blocks.enviroment;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import biotech.content.BioUnits;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.UnitBlock;
import mindustry.world.blocks.units.UnitFactory;

public class BiologicalStaticSpawner extends UnitBlock {

    public static UnitType spawnedUnit = BioUnits.strider;
    public static float spawnTime = 700;
    public static float spawnAmount = 10;
    public static Effect effect = Fx.none;

    public BiologicalStaticSpawner(String name) {
        super(name);
        targetable = false;
        update = true;
        hasPower = false;
        hasItems = false;
        solid = false;
        configurable = false;
        clearOnDoubleTap = false;
        outputsPayload = false;
        rotate = false;
        commandable = false;
        ambientSound = Sounds.respawning;
        customShadow = true;
        destructible = false;
    }

    public class BiologicalStaticSpawnerBuild extends UnitBuild {

        @Override
        public void updateTile() {
            super.updateTile();
            if(efficiency > 0){
                time += edelta() * speedScl * Vars.state.rules.unitBuildSpeed(team);
                progress += edelta() * Vars.state.rules.unitBuildSpeed(team);
                speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
            }else{
                speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
            }

            if(progress >= spawnTime){
                progress %= 1f;

                for (int i = 0; i < spawnAmount + Mathf.random(-5, 5); i++) {
                    Unit unit = spawnedUnit.spawn(Team.crux, tile.worldx(), tile.worldy());
                    payload = new UnitPayload(unit);
                    payVector.setZero();
                    Events.fire(new EventType.UnitCreateEvent(payload.unit, this));

                    effect.at(x, y);

                    if (payload.dump()) {
                        Call.unitBlockSpawn(tile);
                    }
                }
            }

            progress = Mathf.clamp(progress, 0, spawnTime);
        }

        @Override
        public void draw() {
            Draw.alpha(0.5f);
            Draw.rect(customShadowRegion, x, y);
            Draw.alpha(1);
            Draw.rect(region, x, y);
        }
    }
}