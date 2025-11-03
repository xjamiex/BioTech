package biotech.entities.part;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.math.geom.Vector;
import arc.util.Log;
import biotech.content.BioPal;
import mindustry.entities.part.RegionPart;
import mindustry.graphics.Drawf;

public class TentacleRegionPart extends RegionPart {

    TentacleSegment segment;

    public TentacleRegionPart(String region) {
        super(region);
    }

    @Override
    public void load(String name) {
        super.load(name);
        segment = new TentacleSegment(0, 0, 25, 100);
    }

    @Override
    public void draw(PartParams params) {
        super.draw(params);
        segment.follow(params.x, params.y);
        segment.update();
        segment.draw();

        Log.info(segment.segBegin);
    }

    static class TentacleSegment {
        Vec2 segBegin;
        Vec2 segEnd = new Vec2();
        float angle;
        float length;

        public TentacleSegment(float x, float y, float angle, float length) {
            segBegin = new Vec2(x, y);
            this.angle = angle;
            this.length = length;
        }

        void calculateSegmentEnd() {
            float dx = length * Mathf.cos(angle);
            float dy = length * Mathf.sin(angle);
            segEnd.set(segBegin.x + dx, segBegin.y + dy);
        }

        void follow(float tx, float ty) {
            Vec2 target = new Vec2(tx, ty);
            Vec2 direction = target.sub(segBegin);
            angle = direction.angleRad();

            direction.setLength(length);
            direction.x *= -1;
            direction.y *= -1;

            segBegin = direction.add(target);
        }

        void update() {
            calculateSegmentEnd();
        }

        void draw() {
            Lines.stroke(10);
            Draw.color(Color.white);
            Lines.line(segBegin.x, segBegin.y, segEnd.x, segEnd.y);
        }
    }
}
