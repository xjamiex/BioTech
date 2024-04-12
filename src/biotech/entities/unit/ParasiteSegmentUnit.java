package biotech.entities.unit;

import biotech.entities.TentacleSegment;
import mindustry.gen.UnitEntity;

public class ParasiteSegmentUnit extends UnitEntity {


    public void followSegment(TentacleSegment segment) {
        x = segment.beginPoint.x;
        y = segment.beginPoint.y;
    }

    @Override
    public boolean isAI() {
        return false;
    }
}
