package biotech.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.game.Objectives;

import static biotech.content.BioTechTree.bioNode;
import static biotech.content.BioTechTree.bioNodeRoot;
import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;

public class AndoriTechTree {
    public static void load(){
        BioPlanets.andori.techTree = bioNodeRoot("Andori", BioBlocks.coreSight, () -> {
            bioNode(BioBlocks.magnesiumConveyor,  Seq.with(new Objectives.Produce(BioItems.magnesium)), () -> {
                bioNode(BioBlocks.unitDocker, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                    bioNode(BioBlocks.unitDischarger);
                });
                bioNode(BioBlocks.splitter, () -> {
                    bioNode(BioBlocks.conveyorOverpass);
                });
            });

            bioNode(BioBlocks.descentManufacturer, Seq.with(new Objectives.Research(BioBlocks.boneCrusher)), () -> {
                bioNode(BioUnits.strider, () -> {});
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

            bioNode(BioBlocks.bioDrill, () -> {
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

            /*
            nodeProduce(BioItems.magnesium, () -> {
                nodeProduce(BioItems.carbonicTissue, () -> {
                    nodeProduce(BioItems.carminite, () -> {
                    });
                });
                nodeProduce(BioItems.calciticFragment, () -> {
                });
                nodeProduce(BioLiquids.hemoFluid, () -> {
                    nodeProduce(BioItems.potash, () -> {
                    });
                });
                nodeProduce(BioItems.phosphorus, () -> {
                });
            });
            */

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
