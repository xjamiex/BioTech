package biotech.entities;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Tmp;

public class TentacleSegment {
    public Vec2 beginPoint;
    public Vec2 endPoint = new Vec2();
    public float angle;
    public float length;
    public TentacleSegment(float x, float y, float len, float ang) {
        beginPoint = new Vec2(x, y);
        angle = ang;
        length = len;
        calculateEnd();
    }

    public TentacleSegment(TentacleSegment par, float len, float ang) {
        beginPoint = par.endPoint.cpy();
        angle = ang;
        length = len;
        calculateEnd();
    }

    public void follow(TentacleSegment child) {
        float targetX = child.beginPoint.x;
        float targetY = child.beginPoint.y;
        follow(targetX, targetY);
    }

    public void follow(float tx, float ty) {
        Vec2 target = new Vec2(tx, ty);
        Vec2 dir = Tmp.v1.set(target).sub(beginPoint);
        angle = dir.angle();

        dir = dir.setLength(length).inv();
        beginPoint = target.add(dir);
    }

    public void calculateEnd() {
        float dx = length * Mathf.cosDeg(angle);
        float dy = length * Mathf.sinDeg(angle);
        endPoint.set(beginPoint.x + dx, beginPoint.y + dy);
    }

    public void render(String sprite) {
        Draw.rect(sprite, beginPoint.x, beginPoint.y, angle - 90);

    }
}
