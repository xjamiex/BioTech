package biotech.content;

import mindustry.content.Planets;

import mindustry.content.TechTree;
import static mindustry.content.TechTree.node;
public class BioTechTree {
    public static void load(){
        BioPlanets.andori.techTree = TechTree.nodeRoot("andori", BioBlocks.coreSight, () -> {
            node(BioBlocks.magnesiumConvayor, () -> {
                node(BioBlocks.unitDischarger);
                node(BioBlocks.unitDocker);
            });

            node(BioBlocks.bioPump, () -> {
                node(BioBlocks.bioDrill);
                node(BioBlocks.boneCrusher);
            });

            node(BioBlocks.alive, () -> {
                node(BioBlocks.spike);
            });
        });
    }
}
