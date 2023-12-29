package biotech.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class BioItems {
    public static Item
        //t1
        carbonicTissue, calciticFragment, magnesium, potash;

    public static final Seq<Item> andoriItems = new Seq<>();
    public static void load() {
        carbonicTissue = new Item("carbonic-tissue", Color.valueOf("ff5959")){{
            hardness = 1;
            cost = 0.7f;
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

        andoriItems.addAll(
                carbonicTissue, calciticFragment, magnesium, potash
        );
    }
}
