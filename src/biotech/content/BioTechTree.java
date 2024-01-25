package biotech.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;

public class BioTechTree {
    public static void load(){
        BioPlanets.andori.techTree = TechTree.nodeRoot("andori", BioBlocks.coreSight, () -> {
            node(BioBlocks.magnesiumConveyor, () -> {
                node(BioBlocks.unitDischarger);
                node(BioBlocks.unitDocker);
                node(BioBlocks.splitter, () -> {
                    node(BioBlocks.conveyorOverpass);
                });
            });

            node(BioBlocks.bioDrill, () -> {
                node(BioBlocks.boneCrusher, () -> {
                    node(BioBlocks.bioPump);
                    node(BioBlocks.liquidPipe, () -> {
                        node(BioBlocks.liquidSplitter);
                        node(BioBlocks.liquidOverpass);
                    });
                });
            });

            node(BioBlocks.alive,() -> {
                node(BioBlocks.spike, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {});
                node(BioBlocks.magnesiumWall, () -> {
                    node(BioBlocks.largeMagnesiumWall);
                });
            });

            node(BioBlocks.groundManufacturer, () -> {
                node(BioUnits.strider);
            });

            node(BioBlocks.hematicSieve);

            nodeProduce(BioItems.magnesium, () -> {
                nodeProduce(BioItems.calciticFragment, () -> {
                    nodeProduce(BioItems.potash, () -> {});
                });
            });
            nodeProduce(BioItems.carbonicTissue, () -> {});

            nodeProduce(BioLiquids.hemoFluid, () -> {});
        });
    }
}
