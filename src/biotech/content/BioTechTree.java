package biotech.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;

public class BioTechTree {
    public static void load(){
        BioPlanets.andori.techTree = TechTree.nodeRoot("andori", BioBlocks.coreSight, () -> {
            node(BioBlocks.magnesiumConveyor, Seq.with(new Objectives.Produce(BioItems.magnesium)), () -> {
                node(BioBlocks.unitDocker, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                    node(BioBlocks.unitDischarger);
                });
                node(BioBlocks.splitter, () -> {
                    node(BioBlocks.conveyorOverpass);
                });
            });

            node(BioBlocks.bioDrill, () -> {
                node(BioBlocks.bioPiercer, () -> {
                    node(BioBlocks.bioPress, () -> {
                        node(BioBlocks.liquidPipe, () -> {
                            node(BioBlocks.liquidSplitter, () -> {
                                node(BioBlocks.liquidOverpass);
                            });
                        });
                    });
                });
                node(BioBlocks.boneCrusher, Seq.with(new Objectives.Research(BioBlocks.magnesiumConveyor)), () -> {
                });
            });

            node(BioBlocks.alive, Seq.with(new Objectives.Produce(BioItems.carbonicTissue)), () -> {
                node(BioBlocks.spike, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {
                    node(BioBlocks.needle, Seq.with(new Objectives.Produce(BioItems.potash)), () -> {});
                });
                node(BioBlocks.magnesiumWall, () -> {
                    node(BioBlocks.largeMagnesiumWall);
                });
            });

            node(BioBlocks.groundManufacturer, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                node(BioUnits.strider);
                node(BioBlocks.aircraftManufacturer, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus)), () -> {
                    node(BioUnits.scout);
                });
            });

            node(BioBlocks.hematicSieve, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {
            });

            nodeProduce(BioItems.magnesium, () -> {
                nodeProduce(BioItems.carbonicTissue, () -> {});
                nodeProduce(BioItems.calciticFragment, () -> {});
                nodeProduce(BioLiquids.hemoFluid, () -> {
                    nodeProduce(BioItems.potash, () -> {});
                });
            });
        });
    }
}
