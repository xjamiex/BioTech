package biotech.maps.planets;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import biotech.content.BioBlocks;
import mindustry.ai.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.maps.generators.*;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class AndoriPlanetGenerator extends ErekirPlanetGenerator {

    Block[] terrain = {Blocks.redStone, Blocks.yellowStone, Blocks.redStone, BioBlocks.fleshFloor, BioBlocks.fleshFloor, BioBlocks.fleshFloor, Blocks.denseRedStone, Blocks.yellowStonePlates};
    {
        baseSeed = 2;
        defaultLoadout = Schematics.readBase64("bXNjaAF4nE3MQQqAMAwF0a8tCnXnPTyRuKhpsAFrpcn9UXcybz1wcB7+ioURqDZeVI5sGNU4FkkISplLNCHFlFipyW1SLwDDGXc+Ff26dZh3qcaUl98E6D5vD96uG8Q=");
    }
}
