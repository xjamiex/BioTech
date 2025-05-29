package biotech.ui;

import arc.Core;
import arc.scene.ui.ImageButton;

public class BioStyles {
    public static ImageButton.ImageButtonStyle technodeFull;

    public static void load() {
        technodeFull = new ImageButton.ImageButtonStyle() {{
            up = imageUp = Core.atlas.drawable("biotech-seer-full");
        }};
    }
}
