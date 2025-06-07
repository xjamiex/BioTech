package biotech.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class BioItems {
    public static Item
        //t1
        carbonicTissue, calciticFragment, magnesium, potash,

        //t2
        carminite, phosphorus, dustClump, proteicPaste, stemCapsule;

    public static final Seq<Item> andoriItems = new Seq<>();
    public static void load() {
        carbonicTissue = new Item("carbonic-tissue", Color.valueOf("ff5959")){{
            hardness = 1;
            cost = 0.7f;
            alwaysUnlocked = true;
        }};

        calciticFragment = new Item("calcitic-fragment", Color.valueOf("ebf1fa")){{
            hardness = 1;
            cost = 0.8f;
        }};

        magnesium = new Item("magnesium", Color.valueOf("8c7c91")){{
            hardness = 1;
            cost = 0.8f;
        }};

        potash = new Item("potash", Color.valueOf("e88b43")){{
            hardness = 1;
            cost = 1f;
        }};

        //T2
        carminite = new Item("carminite", Color.valueOf("ff5959")){{
            hardness = 2;
            cost = 1.3f;
        }};

        phosphorus = new Item("phosphorus", Color.valueOf("f7dc6d")){{
            hardness = 2;
            cost = 1.1f;
            flammability = 1.5f;
            explosiveness = 0.5f;
        }};

        dustClump = new Item("dust-clump", Color.valueOf("968360")){{
            hardness = 1;
            cost = 0.5f;
        }};

        proteicPaste = new Item("proteic-paste", Color.valueOf("f5d33b")){{
            hardness = 1;
            cost = 1.5f;
        }};

        stemCapsule = new Item("stem-capsule", BioPal.bloodRedLight){{
            hardness = 2;
            cost = 1.8f;
        }};

        andoriItems.addAll(
                carbonicTissue, calciticFragment, magnesium, potash,
                carminite, phosphorus, dustClump, proteicPaste, stemCapsule
        );
    }
}
