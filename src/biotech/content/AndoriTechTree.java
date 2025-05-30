package biotech.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.game.Objectives;

import static biotech.content.BioTechTree.*;
import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;

public class AndoriTechTree {
    public static void load(){
        BioPlanets.andori.techTree = bioNodeRoot("Andori", BioBlocks.coreSight, () -> {
            bioNode(BioBlocks.magnesiumConveyor, Seq.with(new Objectives.Produce(BioItems.magnesium)), 2, () -> {
                bioNode(BioBlocks.unitDocker, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                    bioNode(BioBlocks.unitDischarger);
                });
                bioNode(BioBlocks.splitter, () -> {
                    bioNode(BioBlocks.conveyorOverpass);
                });
            });

            bioNode(BioBlocks.descentManufacturer, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                bioNode(BioUnits.strider, null, 2, () -> {});
                bioNode(BioUnits.scout, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus)), () -> {});
                bioNode(BioBlocks.osylithReformer, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    bioNode(BioUnits.nomad, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    });
                    bioNode(BioUnits.seer, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    });
                });
                bioNode(BioBlocks.experimentalManufacturer, Seq.with(new Objectives.SectorComplete(BioSectorPresets.pelvis)), () -> {
                    bioNode(BioUnits.smith);
                });
            });

            bioNode(BioBlocks.bioDrill, null, 2, () -> {
                bioNode(BioBlocks.magnesiumBurner, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    bioNode(BioBlocks.drillUpgrader);
                });
                bioNode(BioBlocks.bioPiercer, () -> {
                    bioNode(BioBlocks.bioPress, () -> {
                        bioNode(BioBlocks.liquidPipe, () -> {
                            bioNode(BioBlocks.liquidSplitter, () -> {
                                bioNode(BioBlocks.liquidOverpass);
                                });
                            });
                        });
                    });
                bioNode(BioBlocks.boneCrusher, Seq.with(new Objectives.Research(BioBlocks.magnesiumConveyor)), () -> {
                    });
            });

            bioNode(BioBlocks.hematicSieve, Seq.with(new Objectives.OnSector(BioSectorPresets.crus)), () -> {
                bioNode(BioBlocks.hemoCrystallizer, Seq.with(new Objectives.Produce(BioItems.phosphorus)), () -> {});
            });

            bioNode(BioBlocks.inception, Seq.with(new Objectives.Produce(BioItems.carbonicTissue)), () -> {
                bioNode(BioBlocks.costae, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {
                    bioNode(BioBlocks.needle, Seq.with(new Objectives.Produce(BioItems.potash)), () -> {
                    });
                    bioNode(BioBlocks.celluris, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                    });
                });
                bioNode(BioBlocks.magnesiumWall, () -> {
                    bioNode(BioBlocks.largeMagnesiumWall);
                });
            });


            bioNodeProduce(BioItems.magnesium, 3, () -> {
                bioNodeProduce(BioItems.carbonicTissue, () -> {
                    bioNodeProduce(BioItems.carminite, () -> {
                    });
                });
                bioNodeProduce(BioItems.calciticFragment, () -> {
                });
                bioNodeProduce(BioLiquids.hemoFluid, () -> {
                    bioNodeProduce(BioItems.potash, () -> {
                    });
                });
                bioNodeProduce(BioItems.phosphorus, () -> {
                });
            });


            bioNode(BioSectorPresets.ankle, () -> {
                bioNode(BioSectorPresets.crus, Seq.with(new Objectives.SectorComplete(BioSectorPresets.ankle)), () -> {
                    bioNode(BioSectorPresets.femur, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus)), () -> {
                        bioNode(BioSectorPresets.pelvis, Seq.with(new Objectives.SectorComplete(BioSectorPresets.femur)), () -> {
                        });
                    });
                    bioNode(BioSectorPresets.putridum, Seq.with(new Objectives.SectorComplete(BioSectorPresets.crus), new Objectives.Produce(BioItems.phosphorus)), () -> {
                    });
                });
            });
        });
    }
}
