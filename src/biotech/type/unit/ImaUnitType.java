package biotech.type.unit;

import biotech.entities.part.BiologicalRegionPart;
import biotech.type.BiologicalUnitType;
import mindustry.gen.LegsUnit;
import mindustry.gen.Unit;

public class ImaUnitType extends BiologicalUnitType {
    public static int state;

    public ImaUnitType(String name) {
        super(name);
        speed = 0;
        constructor = LegsUnit::create;
        health = 200000;

        //dormant
        state = 0;

        parts.addAll(
                new BiologicalRegionPart("-1"){{
                    x = y = 0;
                    growX = 0.2f;
                    growY = 0.1f;
                    moveX = 2f;
                    moveY = -0.6f;
                    moveRot = 3f;
                }},
                new BiologicalRegionPart("-2"){{
                    x = y = 0;
                    growX = 0.1f;
                    growY = 0.1f;;
                    moveX = -1.5f;
                    moveY = 0.6f;
                    moveRot = -3f;
                }},
                new BiologicalRegionPart("-3"){{
                    x = y = 0;
                    growX = 0.2f;
                    growY = 0.3f;
                    moveX = -1f;
                    moveY = 0.6f;
                    moveRot = 2f;
                }},
                new BiologicalRegionPart("-4"){{
                    x = y = 0;
                    growX = 0.2f;
                    growY = 0.1f;
                    moveX = 3f;
                    moveY = 0.6f;
                    moveRot = 3f;
                }}
        );
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