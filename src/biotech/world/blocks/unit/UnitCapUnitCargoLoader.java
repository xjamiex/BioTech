package biotech.world.blocks.unit;

import arc.math.Mathf;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.BuildingTetherc;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.type.UnitType;
import mindustry.world.blocks.units.UnitCargoLoader;

import static mindustry.Vars.net;

public class UnitCapUnitCargoLoader extends UnitCargoLoader {

    public static int unitCap = 4;

    public UnitCapUnitCargoLoader(String name) {
        super(name);
    }

    public class UnitCapUnitTransportSourceBuild extends UnitTransportSourceBuild {
        @Override
        public void updateTile(){
            //unit was lost/destroyed
            if(unit != null && (unit.dead || !unit.isAdded())){
                unit = null;
            }

            if(readUnitId != -1){
                unit = Groups.unit.getByID(readUnitId);
                if(unit != null || !net.client()){
                    readUnitId = -1;
                }
            }

            warmup = Mathf.approachDelta(warmup, efficiency, 1f / 60f);
            readyness = Mathf.approachDelta(readyness, unit != null ? 1f : 0f, 1f / 60f);

            if(unit == null && canCreate(team, unitType)){
                buildProgress += edelta() / buildTime;
                totalProgress += edelta();

                if(buildProgress >= 1f){
                    if(!net.client()){
                        unit = unitType.create(team);
                        if(unit instanceof BuildingTetherc bt){
                            bt.building(this);
                        }
                        unit.set(x, y);
                        unit.rotation = 90f;
                        unit.add();
                        Call.unitTetherBlockSpawned(tile, unit.id);
                    }
                }
            }
        }
    }

    static boolean canCreate(Team team, UnitType type){
        return team.data().countType(type) < unitCap && !type.isBanned();
    }
}
