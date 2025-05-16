package biotech.type.unit;

import mindustry.entities.units.AIController;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class ImaUnitType extends UnitType {
    public static int state;

    public ImaUnitType(String name) {
        super(name);
        speed = 0;
        constructor = UnitEntity::create;
        health = 200000;

        //dormant
        state = 0;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if (unit.damaged() && state == 0) state = 1;

        if (state > 0) {
            unit.damage(1000);
        }
    }
}