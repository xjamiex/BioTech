package biotech.ui;

import biotech.ui.dialog.BioSpawnerDialog;

public class BioUI{
    public static BioSpawnerDialog spawner;

    public static void load() {
        spawner = new BioSpawnerDialog();
    }
}
