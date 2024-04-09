package biotech.type.unit;

import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import biotech.entities.TentacleSegment;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class ParasiteUnitType extends UnitType {
    public int segments = 50;
    public float offsetX = 0;
    public float offsetY = 0;
    public String tentacleSprite = "biotech-carrier";
    public float length = 32 * 0.3f;
    TentacleSegment[] body = new TentacleSegment[segments];

    public ParasiteUnitType(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        body[0] = new TentacleSegment(0, 0, length, 0);
        for (int i = 1; i < body.length; i++) {
            body[i] = new TentacleSegment(body[i - 1], length, 0);
        }
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);

        int total = body.length;
        TentacleSegment base = body[total - 1];
        base.follow(unit.x, unit.y);
        base.calculateEnd();
        base.render(tentacleSprite);

        for (int i = total - 2; i >= 0; i--) {
            body[i].follow(body[i + 1]);
            body[i].calculateEnd();
            body[i].render(tentacleSprite);
        }
    }
}
