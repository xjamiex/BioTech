package biotech.entities;

import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;

public class TentacleSegment {
    public Vec2 beginPoint;
    public Vec2 endPoint = new Vec2();
    public float angle;
    public float length;
    public TentacleSegment(float x, float y, float ang, float len) {
        beginPoint = new Vec2(x, y);
        angle = ang;
        length = len;
    }

    public void calculateEndPoint() {
        float dx = length * Mathf.cos(angle);
        float dy = length * Mathf.sin(angle);
        endPoint.set(beginPoint.x + dx, beginPoint.y + dy);
    }

    public void update() {
        calculateEndPoint();
    }

    public void render() {
        Lines.stroke(4);
        Lines.line(beginPoint.x, beginPoint.y, endPoint.x, endPoint.y);
    }
}
