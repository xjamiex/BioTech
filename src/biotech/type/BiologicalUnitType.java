package biotech.type;

import arc.graphics.Color;
import biotech.type.unit.*;
import mindustry.type.UnitType;

public class BiologicalUnitType extends BioTechUnitType{

    public BiologicalUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2e0808");
        useUnitCap = false;
    }
}
