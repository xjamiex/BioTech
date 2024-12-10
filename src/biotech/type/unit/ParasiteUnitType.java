package biotech.type.unit;

import mindustry.ai.types.GroundAI;
import mindustry.gen.ElevationMoveUnit;
import mindustry.type.UnitType;

public class ParasiteUnitType extends UnitType {
    public ParasiteUnitType(String name) {
        super(name);
        constructor = ElevationMoveUnit::create;
        aiController = GroundAI::new;
    }

    //i hate this


}
