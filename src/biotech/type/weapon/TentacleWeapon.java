package biotech.type.weapon;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

import java.util.ArrayList;

public class TentacleWeapon extends Weapon {

    public static int segmentAmt = 30;
    public static float segmentLength = 15;
    public static float range = 300;

    public Unit target;

    public TentacleWeapon(String region) {
        super(region);

        reload = 1f;
        predictTarget = false;
        autoTarget = true;
        controllable = false;
        rotate = true;
        useAmmo = false;
        mountType = TentacleMount::new;
        recoil = 0f;
        noAttack = true;
        useAttackRange = false;
    }

    @Override
    public void update(Unit unit, WeaponMount mount) {
        super.update(unit, mount);

        if(target != null && (target.dead() || target.dst(unit) - target.hitSize/2f > range)){
            target = null;
        }

        target = Units.closestEnemy(unit.team, x, y, range, u -> u.checkTarget(true, true));
        updateTentacles(unit, mount);
    }

    public void updateTentacles(Unit unit, WeaponMount mount) {
        TentacleMount tentacleMount = (TentacleMount) mount;
        ArrayList<TentacleSegment> seg = tentacleMount.segments;

        TentacleSegment endSegment = seg.get(seg.size() - 1);

        tentacleMount.target.set(target.x, target.y);

        if (tentacleMount.target.dst(unit.x, unit.y) > segmentAmt * segmentLength) {
            endSegment.follow(unit.x, unit.y);
        } else {
            endSegment.follow(tentacleMount.target.x, tentacleMount.target.y);
        }
        endSegment.update();

        for (int i = seg.size() - 2; i >= 0; i--) {
            seg.get(i).follow(seg.get(i + 1).head.x, seg.get(i + 1).head.y);
            seg.get(i).update();
        }

        seg.get(0).setHead(unit.x, unit.y);

        for (int i = 1; i < seg.size(); i++) {
            seg.get(i).setHead(seg.get(i - 1).tail.x, seg.get(i - 1).tail.y);
        }
    }

    @Override
    public void draw(Unit unit, WeaponMount mount) {
        super.draw(unit, mount);

        TentacleMount tentacleMount = (TentacleMount) mount;

        for (TentacleSegment segment : tentacleMount.segments) {
            segment.draw();
        }
    }

    public static class TentacleMount extends WeaponMount {
        ArrayList<TentacleSegment> segments = new ArrayList<>();

        Vec2 target = new Vec2();

        public TentacleMount(Weapon weapon) {
            super(weapon);

            segments.add(new TentacleSegment(100, 100, 0, segmentLength));
            for (int i = 0; i < segmentAmt; i++) {
                segments.add(new TentacleSegment(segments.get(i), 0, segmentLength));
            }
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
        }

        public void draw() {
            Lines.stroke(10);
            Draw.color(Color.white);
            Lines.line(head.x, head.y, tail.x, tail.y);
        }
    }
}
