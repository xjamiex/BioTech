package biotech.content;

import arc.graphics.Color;
import biotech.maps.planets.AndoriPlanetGenerator;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.content.Weathers;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.NoiseMesh;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Planet;
import mindustry.type.Weather;

public class BioPlanets {
    public static Planet
        andori;

    public static void load(){
        andori = new Planet("andori", Planets.sun, 1.2f, 3){{
            defaultCore = BioBlocks.coreSight;
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
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            allowLaunchSchematics = true;
            lightColor = BioPal.bloodRed;
            //doesn't play well with configs
            prebuildBase = false;
            ruleSetter = r -> {
                r.waveTeam = BioTeams.septaris;
                r.placeRangeCheck = false;
                r.showSpawns = false;
                r.weather.add(new Weather.WeatherEntry(){{
                    weather = Weathers.fog;
                    always = true;
                }});
            };
            iconColor = BioPal.bloodRedLight;
            atmosphereColor = BioPal.bloodRed;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 1;
            allowLaunchToNumbered = false;
            alwaysUnlocked = true;
            landCloudColor = BioPal.bloodRed.cpy().a(0.5f);
            updateLighting = false;
            clearSectorOnLose = true;
        }};
    }
}
