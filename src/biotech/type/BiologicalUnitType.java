package biotech.type;

import arc.math.Mathf;
import arc.util.Log;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class BiologicalUnitType extends UnitType {
    public static float unitIdRandomized;

    public BiologicalUnitType(String name) {
        super(name);
    }

    @Override
    public void update(Unit unit) {
        Log.info(unit.id());
        unitIdRandomized = Mathf.randomSeed(unit.id, 0f, 10f);
    }
}
