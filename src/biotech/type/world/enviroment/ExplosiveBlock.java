package biotech.type.world.enviroment;

import arc.audio.Sound;
import arc.func.Prov;
import biotech.content.BioItems;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

public class ExplosiveBlock extends Block {

    public int explodeDamage = 20;
    public int explodeRange = 4;
    public Effect explodeEffect = Fx.none;
    public Sound explodeSound = Sounds.none;

    public ExplosiveBlock(String name) {
        super(name);
        drawCracks = false;
        drawTeamOverlay = false;
        buildVisibility = BuildVisibility.sandboxOnly;
        solid = true;
        targetable = true;
        destructible = true;
        buildType = ExplosiveBlockBuild::new;
    }


    public class ExplosiveBlockBuild extends Building {
        @Override
        public void onDestroyed() {
            super.onDestroyed();
            createExplosion();
        }

        public void createExplosion() {
            if (explodeDamage > 0) {
                Damage.damage(x, y, explodeRange * Vars.tilesize, explodeDamage);
            }
            explodeEffect.at(this);
            explodeSound.at(this);
        }
    }
}