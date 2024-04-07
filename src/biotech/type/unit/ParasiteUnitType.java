package biotech.type.unit;

import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import biotech.entities.TentacleSegment;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class ParasiteUnitType extends UnitType {
    TentacleSegment segment;

    public ParasiteUnitType(String name) {
        super(name);
        segment = new TentacleSegment(300, 200, 0, 100);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        segment.update();
        segment.render();
    }
}
