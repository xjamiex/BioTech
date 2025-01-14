package biotech.type;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class BiologicalUnitType extends UnitType {

    public BiologicalUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2e0808");
        useUnitCap = false;
    }
}
