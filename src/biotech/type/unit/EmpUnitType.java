package biotech.type.unit;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import biotech.content.BioItems;
import biotech.content.BioPal;
import biotech.content.BioTeams;
import mindustry.Vars;
import mindustry.ai.types.GroundAI;
import mindustry.ai.types.MissileAI;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.gen.ElevationMoveUnit;
import mindustry.gen.Groups;
import mindustry.gen.TimedKillUnit;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.unit.MissileUnitType;

import javax.sound.sampled.Line;

import static mindustry.Vars.*;

public class EmpUnitType extends UnitType {

    public static float empRadius;
    public static float empDamage;
    public static StatusEffect empEffect;

    public EmpUnitType(String name) {
        super(name);
        speed = 0;
        drawCell = false;
        engineSize = 0;
        rotateSpeed = 0f;
        constructor = TimedKillUnit::create;
        flying = true;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        unit.rotation = 90;
        Groups.unit.intersect(unit.x - empRadius, unit.y - empRadius, empRadius * 2, empRadius * 2, u -> {
            if(unit.dst(u) >= empRadius || u.team == unit.team) return;
            u.apply(empEffect, 4f);
        });
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);

        if(renderer.animateShields){
            Draw.z(Layer.shields);
            Fill.circle(unit.x, unit.y, empRadius);
        }else{
            Draw.z(Layer.shields);
            Lines.stroke(1.5f);
            Draw.alpha(0.06f);
            Fill.circle(unit.x, unit.y, empRadius);
            Draw.alpha(1f);
            Lines.circle(unit.x, unit.y, empRadius);
            Draw.reset();
        }
    }
}
