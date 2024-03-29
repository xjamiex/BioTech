package biotech.entities.bullet;

import arc.math.Mathf;
import biotech.content.BioPal;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.gen.Bullet;

public class LightningLaserBulletType extends ShrapnelBulletType {
    @Override
    public void init(Bullet b) {
        super.init(b);
        Lightning.create(b, BioPal.supportGreenLight, damage, b.x, b.y, b.rotation(), lightningLength + Mathf.random(lightningLengthRand));

    }
    @Override
    public void draw(Bullet b) {
        super.draw(b);
    }
}
