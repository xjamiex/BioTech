package biotech.content;

import mindustry.content.TechTree;

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
                node(BioBlocks.bioPump, () -> {
                    node(BioBlocks.boneCrusher);
                    node(BioBlocks.liquidPipe, () -> {
                        node(BioBlocks.liquidSplitter);
                    });
                });
            });

            node(BioBlocks.alive, () -> {
                node(BioBlocks.spike);
                node(BioBlocks.magnesiumWall, () -> {
                    node(BioBlocks.largeMagnesiumWall);
                });
            });

            node(BioBlocks.aircraftManufacturer, () -> {
                node(BioUnits.scout);
            });

            nodeProduce(BioItems.magnesium, () -> {
                nodeProduce(BioItems.carbonicTissue, () -> {});
                nodeProduce(BioItems.calciticFragment, () -> {});
            });

            nodeProduce(BioLiquids.hemoFluid, () -> {});
        });
    }
}
