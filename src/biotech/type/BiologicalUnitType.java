package biotech.type;

import arc.graphics.Color;
import arc.math.Interp;
import biotech.content.BioPal;
import biotech.entities.bullet.ImmuneGoreBulletType;
import biotech.type.unit.*;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Layer;
import mindustry.type.Weapon;

public class BiologicalUnitType extends BioTechUnitType{

    public static int goreParticles = 5;

    public BiologicalUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2e0808");
        useUnitCap = false;
        lightOpacity = 0;

        weapons.add(
                new Weapon("immune-death"){{
                    controllable = false;
                    noAttack = true;
                    shootOnDeath = true;
                    x = y = 0;
                    top = false;
                    mirror = false;
                    shootCone = inaccuracy = 360;
                    bullet = new BulletType(5, 0){{
                        lifetime = 1;
                        deathExplosionEffect = Fx.none;
                        shootEffect = new ParticleEffect(){{
                                    interp = Interp.pow10In;
                                    lifetime = 10 * 60;
                                    colorFrom = colorTo = BioPal.bloodRed;
                                    layer = Layer.blockUnder - 0.01f;
                                    length = 10;
                                    particles = 10;
                                    sizeFrom = 14;
                                    sizeTo = 0;
                                }};
                        fragBullets = goreParticles;
                        fragLifeMax = 2 * 60;
                        fragLifeMin = 60;
                        fragVelocityMax = 1.5f;
                        fragVelocityMin = 0.5f;
                        fragBullet = new ImmuneGoreBulletType(1, 0){{
                            drag = 0.05f;
                            width = height = 12f;
                        }};
                    }};
                }}
        );
    }
}
