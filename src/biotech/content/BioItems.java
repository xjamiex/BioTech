package biotech.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class BioItems {
    public static Item
        flesh, boneFragment, magnesium;


    public static void load() {
        flesh = new Item("flesh", Color.valueOf("ff5959")){{
            hardness = 1;
            cost = 0.7f;
            alwaysUnlocked = true;
        }};

        boneFragment = new Item("bone-fragment", Color.valueOf("ebf1fa")){{
            hardness = 2;
            cost = 0.8f;
        }};

        magnesium = new Item("magnesium", Color.valueOf("494b52")){{
            hardness = 1;
            cost = 0.8f;
        }};
    }
}
