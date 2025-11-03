package biotech.type.unit;

import arc.graphics.Color;
import biotech.entities.part.*;
import mindustry.gen.*;
import mindustry.type.*;

public class BioTechUnitType extends UnitType{

    public BioTechUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2b2626");
    }

    //Do note this might not update properly w/ weapon parts since the "params" might need to updated per weapon draw (aka weaponType with setting the params) -Rushie
    public void updatePrams(Unit unit){
        BioPartProgParams.bioParams.set(
            unit.id
        );
    }

    @Override
    public void draw(Unit unit){
        if(parts.size > 0) updatePrams(unit);

        super.draw(unit);
    }

}
