package biotech;

import arc.*;
import arc.util.*;
import biotech.content.BioBlocks;
import biotech.content.BioItems;
import biotech.content.BioLiquids;
import biotech.content.BioUnits;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class BioTech extends Mod{

    public BioTech(){
    }

    @Override
    public void loadContent(){
        BioItems.load();
        BioLiquids.load();
        BioUnits.load();
        BioBlocks.load();
    }
}
