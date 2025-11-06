package biotech.entities.part;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Segment;
import arc.math.geom.Vec2;
import arc.math.geom.Vector;
import arc.util.Log;
import biotech.content.BioPal;
import mindustry.Vars;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.InverseKinematics;

import java.util.ArrayList;

public class TentacleRegionPart extends BiologicalRegionPart {

    ArrayList<TentacleSegment> segments = new ArrayList<>();

    public static int segmentAmt = 1;
    public static float segmentLength = 100;

    public TentacleRegionPart(String region) {
        super(region);

        segments.add(new TentacleSegment(100, 100, 0, segmentLength));
        for (int i = 0; i < segmentAmt; i++) {
            segments.add(new TentacleSegment(segments.get(i), 0, segmentLength));
        }
    }

    @Override
    public void draw(PartParams params) {
        super.draw(params);

        //terrible code
        TentacleSegment endSegment = segments.get(segments.size() - 1);

        endSegment.follow(Vars.player.x, Vars.player.y);
        endSegment.update();

        for (int i = segments.size() - 2; i >= 0; i--) {
            segments.get(i).follow(segments.get(i + 1).head.x, segments.get(i + 1).head.y);
            segments.get(i).update();
        }

        segments.get(0).setHead(params.x, params.y);

        for (int i = 1; i < segments.size(); i++) {
            segments.get(i).setHead(segments.get(i - 1).tail.x, segments.get(i - 1).tail.y);
        }

        for (TentacleSegment segment : segments) {
            segment.draw();
        }


    }

    static class TentacleSegment {
        Vec2 head;
        Vec2 tail = new Vec2();
        float angle;
        float length;

        TentacleSegment parent = null;

        public TentacleSegment(float x, float y, float angle, float length) {
            head = new Vec2(x, y);
            this.angle = angle;
            this.length = length;

            update();
        }

        public TentacleSegment(TentacleSegment parent, float angle, float length) {
            this.parent = parent;
            this.angle = angle;
            this.length = length;
            head = parent.tail.cpy();

            update();
        }

        void setHead(float x, float y) {
            head.set(x, y);
            update();
        }

        void update() {
            //calc B
            float dx = length * Mathf.cos(angle);
            float dy = length * Mathf.sin(angle);
            tail.set(head.x + dx, head.y + dy);
        }

        void follow(float targetX, float targetY) {
            Vec2 target = new Vec2(targetX, targetY);
            Vec2 direction = target.cpy().sub(head);
            angle = direction.angleRad();

            direction.setLength(length);
            direction.scl(-1);

            head = direction.cpy().add(target);

            Draw.color(Color.red);
            Fill.circle(targetX, targetY, 20);
        }

        public void draw() {
            Lines.stroke(10);
            Draw.color(Color.white);
            Lines.line(head.x, head.y, tail.x, tail.y);
        }
    }
}
