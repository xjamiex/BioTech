package biotech.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;

public class BioTechTree {
    public static void load(){
        BioPlanets.andori.techTree = TechTree.nodeRoot("Andori", BioBlocks.coreSight, () -> {
            node(BioBlocks.magnesiumConveyor, Seq.with(new Objectives.Produce(BioItems.magnesium)), () -> {
                node(BioBlocks.unitDocker, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                    node(BioBlocks.unitDischarger);
                });
                node(BioBlocks.splitter, () -> {
                    node(BioBlocks.conveyorOverpass);
                });
            });

            node(BioBlocks.descentManufacturer, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                node(BioUnits.strider, () -> {
                    node(BioUnits.nomad, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {});
                });
                node(BioUnits.scout, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus)), () -> {
                    node(BioUnits.seer, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {});
                });
                node(BioBlocks.experimentalManufacturer, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    node(BioUnits.smith);
                });
            });

            node(BioBlocks.bioDrill, () -> {
                node(BioBlocks.drillUpgrader, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {});
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

            node(BioBlocks.hematicSieve, Seq.with(new Objectives.OnSector(BioSectorPresets.crus)), () -> {
            });

            node(BioBlocks.inception, Seq.with(new Objectives.Produce(BioItems.carbonicTissue)), () -> {
                node(BioBlocks.costae, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {
                    node(BioBlocks.needle, Seq.with(new Objectives.Produce(BioItems.potash)), () -> {
                    });
                    node(BioBlocks.celluris, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    });
                });
                node(BioBlocks.magnesiumWall, () -> {
                    node(BioBlocks.largeMagnesiumWall);
                });
            });

            nodeProduce(BioItems.magnesium, () -> {
                nodeProduce(BioItems.carbonicTissue, () -> {
                });
                nodeProduce(BioItems.calciticFragment, () -> {
                });
                nodeProduce(BioLiquids.hemoFluid, () -> {
                    nodeProduce(BioItems.potash, () -> {
                    });
                });
            });

            node(BioSectorPresets.ankle, () -> {
                node(BioSectorPresets.crus, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {
                    node(BioSectorPresets.femur, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus)), () -> {
                    });
                    node(BioSectorPresets.putridum, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus), new Objectives.Produce(BioItems.phosphorus)), () -> {
                    });
                });
            });
        });
    }
}
