package biotech.content;

import arc.graphics.Color;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.MinerAI;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.ammo.PowerAmmoType;

public class BioUnits {
    public static UnitType

            //air support
            carrier
    ;

    public static void load() {
        carrier = new UnitType("carrier"){{
            controller = u -> new MinerAI();
            constructor = UnitEntity::create;

            defaultCommand = UnitCommand.mineCommand;

            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 420;
            engineSize = 2.2f;
            engineOffset = 7.7f;
            range = 50f;
            isEnemy = false;

            ammoType = new PowerAmmoType(250);

            mineTier = 1;
            mineSpeed = 2.5f;

            outlineColor = Color.valueOf("2b2626");
        }};
    }

}
