package biotech.content;

import arc.graphics.Color;
import biotech.maps.planets.AndoriPlanetGenerator;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Planet;

public class BioPlanets {
    public static Planet
        andori;

    public static void load(){
        andori = new Planet("andori", Planets.sun, 1.2f, 3){{
            generator = new AndoriPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 33, 0.32f, 0.1f, 4, new Color().set(BioPal.bloodRedLight).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                    new HexSkyMesh(this, 69, 0.82f, 0.17f, 6, Color.white.cpy().lerp(BioPal.bloodRedLight, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );

            launchCapacityMultiplier = 0.5f;
            sectorSeed = 4;
            allowWaves = true;
            allowWaveSimulation = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            //doesn't play well with configs
            prebuildBase = false;
            ruleSetter = r -> {
                r.waveTeam = Team.crux;
                r.placeRangeCheck = false;
                r.showSpawns = false;
            };
            iconColor = BioPal.bloodRedLight;
            atmosphereColor = BioPal.bloodRed;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 15;
            alwaysUnlocked = true;
            defaultCore = BioBlocks.coreSight;
            landCloudColor = BioPal.bloodRed.cpy().a(0.5f);
        }};
    }
}
