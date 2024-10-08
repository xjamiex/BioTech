package biotech.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import biotech.content.BioPal;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.gen.Bullet;

public class LightningLaserBulletType extends ShrapnelBulletType {

    public static Color lightningColor;

    @Override
    public void init(Bullet b) {
        super.init(b);
        Lightning.create(b, lightningColor, damage, b.x, b.y, b.rotation(), lightningLength + Mathf.random(lightningLengthRand));
    }
}
