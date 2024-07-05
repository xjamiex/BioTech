package biotech.type.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;

public class BiologicalWeapon extends Weapon {
    public BiologicalWeapon(String name) {
        super(name);
        controllable = aiControllable = rotate = false;
        mountType = BiologicalMount::new;
    }

//    @Override
//    public void draw(Unit unit, WeaponMount mount) {
//        DrawPart.params.setRecoil(((BiologicalMount)mount).id / 10000f);
//
//    }
    @
            Override
    public void draw(Unit unit, WeaponMount mount){
        //apply layer offset, roll it back at the end
        float z = Draw.z();
        Draw.z(z + layerOffset);

        float
                rotation = unit.rotation - 90,
                realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
                weaponRotation  = rotation + (rotate ? mount.rotation : baseRotation),
                wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
                wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);

        if(parts.size > 0){
            DrawPart.params.set(mount.warmup, mount.reload / reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90);
            DrawPart.params.sideMultiplier = flipSprite ? -1 : 1;

            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(((BiologicalMount)mount).id / 10000f);
                if(part.under){
                    Log.info(DrawPart.params.recoil);
                    part.draw(DrawPart.params);
                    Log.info(DrawPart.params.recoil);
                }
            }
        }

        Draw.xscl = -Mathf.sign(flipSprite);
        //fix color
        unit.type.applyColor(unit);

        if(region.found()) Draw.rect(region, wx, wy, weaponRotation);

        Draw.xscl = 1f;

        if(parts.size > 0){
            //TODO does it need an outline?
            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(((BiologicalMount)mount).id / 10000f);
                if(!part.under){
                    Log.info(DrawPart.params.recoil);
                    part.draw(DrawPart.params);
                    Log.info(DrawPart.params.recoil);
                }
            }
        }

        Draw.xscl = 1f;

        Draw.z(z);
    }

    public float range(){
        return bullet.range;
    }

    public static class BiologicalMount extends WeaponMount {
        public float id;

        public BiologicalMount(Weapon weapon) {
            super(weapon);
            id = Mathf.random(10000f);
        }
    }

}
