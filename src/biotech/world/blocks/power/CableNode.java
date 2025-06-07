package biotech.world.blocks.power;

import arc.func.Boolf;
import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Structs;
import mindustry.core.Renderer;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerNode;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class CableNode extends PowerNode {
    public CableNode(String name) {
        super(name);
        laserScale = 0.5f;
    }

    @Override
    public boolean linkValid(Building tile, Building link, boolean checkMaxNodes) {
        if(tile == link || link == null || !(link.block instanceof CableNode) || tile.team != link.team) return false;

        if(overlaps(tile, link, laserRange * tilesize) || (link.block instanceof PowerNode node && overlaps(link, tile, node.laserRange * tilesize))){
            if(checkMaxNodes && link.block instanceof PowerNode node){
                return link.power.links.size < node.maxNodes || link.power.links.contains(tile.pos());
            }
            return true;
        }
        return false;
    }

    @Override
    protected void getPotentialLinks(Tile tile, Team team, Cons<Building> others) {
        if(!autolink) return;

        Boolf<Building> valid = other -> other != null && other.tile != tile && other.block.connectedPower && other.power != null &&
                (other.block instanceof CableNode) &&
                overlaps(tile.x * tilesize + offset, tile.y * tilesize + offset, other.tile, laserRange * tilesize) && other.team == team &&
                !graphs.contains(other.power.graph) &&
                !PowerNode.insulated(tile, other.tile) &&
                !(other instanceof PowerNodeBuild obuild && obuild.power.links.size >= ((PowerNode)obuild.block).maxNodes) &&
                !Structs.contains(Edges.getEdges(size), p -> { //do not link to adjacent buildings
                    var t = world.tile(tile.x + p.x, tile.y + p.y);
                    return t != null && t.build == other;
                });

        tempBuilds.clear();
        graphs.clear();

        //add conducting graphs to prevent double link
        for(var p : Edges.getEdges(size)){
            Tile other = tile.nearby(p);
            if(other != null && other.team() == team && other.build != null && other.build.power != null){
                graphs.add(other.build.power.graph);
            }
        }

        if(tile.build != null && tile.build.power != null){
            graphs.add(tile.build.power.graph);
        }

        var worldRange = laserRange * tilesize;
        var tree = team.data().buildingTree;
        if(tree != null){
            tree.intersect(tile.worldx() - worldRange, tile.worldy() - worldRange, worldRange * 2, worldRange * 2, build -> {
                if(valid.get(build) && !tempBuilds.contains(build)){
                    tempBuilds.add(build);
                }
            });
        }

        tempBuilds.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof PowerNode, b.block instanceof PowerNode);
            if(type != 0) return type;
            return Float.compare(a.dst2(tile), b.dst2(tile));
        });

        returnInt = 0;

        tempBuilds.each(valid, t -> {
            if(returnInt ++ < maxNodes){
                graphs.add(t.power.graph);
                others.get(t);
            }
        });
    }

    public class CableNodeBuild extends PowerNodeBuild {
        @Override
        public void draw() {
            Draw.z(Layer.power);
            if (this.block.variants != 0 && this.block.variantRegions != null) {
                Draw.rect(this.block.variantRegions[Mathf.randomSeed(this.tile.pos(), 0, Math.max(0, this.block.variantRegions.length - 1))], this.x, this.y, this.drawrot());
            } else {
                Draw.rect(this.block.region, this.x, this.y, this.drawrot());
            }

            this.drawTeamTop();

            if(Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

            Draw.z(Layer.power - 1);
            setupColor(power.graph.getSatisfaction());

            for(int i = 0; i < power.links.size; i++){
                Building link = world.build(power.links.get(i));

                if(!linkValid(this, link)) continue;

                if(link.block instanceof PowerNode && link.id >= id) continue;
                drawLaser(x, y, link.x, link.y, size, link.block.size);
            }

            Draw.reset();
        }
    }
}
