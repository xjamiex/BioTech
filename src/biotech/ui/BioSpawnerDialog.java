package biotech.ui;

import arc.Core;
import arc.func.Cons;
import arc.util.Log;
import mindustry.gen.Icon;
import mindustry.logic.LCanvas;
import mindustry.logic.LExecutor;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.steam;
import static mindustry.Vars.ui;

public class BioSpawnerDialog extends BaseDialog {
    public LCanvas canvas;

    public BioSpawnerDialog() {
        super("spawner");
        clearChildren();
        addCloseListener();
        makeButtonOverlay();

        canvas = new LCanvas();
        shouldPause = true;

        setup();

        shown(this::setup);
    }



    private void setup(){
        buttons.clearChildren();
        buttons.defaults().size(160f, 64f);
        buttons.button("@add", Icon.add, () -> {
            BaseDialog wave = new BaseDialog("@add");
            wave.cont.ta
        }).name("add");
        addCloseButton();
    }
}
