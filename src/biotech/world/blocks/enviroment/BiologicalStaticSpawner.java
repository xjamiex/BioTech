package biotech.world.blocks.enviroment;

import arc.graphics.g2d.Draw;
import arc.scene.ui.layout.Table;
import biotech.content.BioUnits;
import biotech.ui.BioUI;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Waves;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.blocks.environment.Prop;

public class BiologicalStaticSpawner extends Prop {

    public static UnitType spawnedUnit = BioUnits.strider;
    public static float spawnTime = 700;
    public static float spawnAmount = 10;
    public static Effect effect = Fx.none;

    public BiologicalStaticSpawner(String name) {
        super(name);
        update = true;
        hasPower = false;
        hasItems = false;
        solid = false;
        configurable = true;
        clearOnDoubleTap = false;
        outputsPayload = false;
        rotate = false;
        commandable = false;
        ambientSound = Sounds.respawning;
        customShadow = true;
        destructible = targetable = false;
        breakable = false;

        config(Waves.class, (BiologicalStaticSpawnerBuild build, Waves waves) -> {

        });
    }

    public class BiologicalStaticSpawnerBuild extends Building {

        @Override
        public void draw() {
            Draw.alpha(0.5f);
            Draw.rect(customShadowRegion, x, y);
            Draw.alpha(1);
            Draw.rect(region, x, y);
        }

        @Override
        public boolean collide(Bullet other) {
            return false;
        }

        @Override
        public void configure(Object value) {
            super.configure(value);
        }

        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.pencil, Styles.cleari, () -> {
                BioUI.spawner.show();
            }).size(40);
        }
    }
}