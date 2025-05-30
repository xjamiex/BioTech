package biotech.content;

import arc.Core;
import arc.func.Cons;
import arc.scene.style.Drawable;
import arc.scene.style.TextureRegionDrawable;
import arc.struct.ObjectFloatMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.content.TechTree;
import mindustry.ctype.Content;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;
import mindustry.type.Planet;

public class BioTechTree extends TechTree {

    private static BioTechNode context = null;

    public static Seq<BioTechNode> all = new Seq<>();
    public static Seq<BioTechNode> roots = new Seq<>();

    public static BioTechNode bioNodeRoot(String name, UnlockableContent content, Runnable children){
        return bioNodeRoot(name, content, false, children);
    }

    public static BioTechNode bioNodeRoot(String name, UnlockableContent content, boolean requireUnlock, Runnable children){
        var root = bioNode(content, content.researchRequirements(), children);
        root.name = name;
        root.requiresUnlock = requireUnlock;
        roots.add(root);
        return root;
    }

    public static BioTechNode bioNode(UnlockableContent content, Runnable children){
        return bioNode(content, content.researchRequirements(), children);
    }

    public static BioTechNode bioNode(UnlockableContent content, ItemStack[] requirements, Runnable children){
        return bioNode(content, requirements, null, 1, children);
    }

    public static BioTechNode bioNode(UnlockableContent content, ItemStack[] requirements, Seq<Objectives.Objective> objectives, int importance, Runnable children){
        BioTechNode bioNode = new BioTechNode(context, content, requirements, importance);
        if(objectives != null){
            bioNode.objectives.addAll(objectives);
        }

        BioTechNode prev = context;
        context = bioNode;
        children.run();
        context = prev;

        return bioNode;
    }

    public static BioTechNode bioNode(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        return bioNode(content, content.researchRequirements(), objectives, 1, children);
    }

    public static BioTechNode bioNode(UnlockableContent content, Seq<Objectives.Objective> objectives, int importance, Runnable children){
        return bioNode(content, content.researchRequirements(), objectives, importance, children);
    }

    public static BioTechNode bioNode(UnlockableContent block){
        return bioNode(block, () -> {});
    }

    public static BioTechNode bioNodeProduce(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        return bioNode(content, content.researchRequirements(), objectives.add(new Objectives.Produce(content)), 1, children);
    }

    public static BioTechNode bioNodeProduce(UnlockableContent content, Runnable children){
        return bioNodeProduce(content, new Seq<>(), children);
    }

    public static class BioTechNode extends TechNode {
        public int importance;
        public final Seq<BioTechNode> bioChildren = new Seq<>();
        public @Nullable BioTechNode bioParent;

        public BioTechNode(BioTechNode bioParent, UnlockableContent content, ItemStack[] requirements, int importance) {
            super(bioParent, content, requirements);

            if(bioParent != null){
                bioParent.bioChildren.add(this);
                planet = bioParent.planet;
                researchCostMultipliers = bioParent.researchCostMultipliers;
            }else if(researchCostMultipliers == null){
                researchCostMultipliers = new ObjectFloatMap<>();
            }

            this.bioParent = bioParent;
            this.importance = importance;
            this.content = content;
            this.depth = bioParent == null ? 0 : bioParent.depth + 1;

            if(researchCostMultipliers.size > 0){
                requirements = ItemStack.copy(requirements);
                for(ItemStack requirement : requirements){
                    requirement.amount = (int)(requirement.amount * researchCostMultipliers.get(requirement.item, 1));
                }
            }

            setupRequirements(requirements);

            var used = new ObjectSet<Content>();

            //add dependencies as objectives.
            content.getDependencies(d -> {
                if(used.add(d)){
                    objectives.add(new Objectives.Research(d));
                }
            });

            content.techNode = this;
            content.techNodes.add(this);
            all.add(this);
        }

        /** Recursively iterates through everything that is a child of this node. Includes itself. */
        public void bioEach(Cons<BioTechNode> consumer){
            consumer.get(this);
            for(var child : bioChildren){
                child.bioEach(consumer);
            }
        }

        /** Adds the specified database tab to all the content in this tree. */
        public void addDatabaseTab(UnlockableContent tab){
            bioEach(node -> node.content.databaseTabs.add(tab));
        }

        /** Adds the specified planet to the shownPlanets of all the content in this tree. */
        public void addPlanet(Planet planet){
            bioEach(node -> node.content.shownPlanets.add(planet));
        }

        public Drawable icon(){
            return icon == null ? new TextureRegionDrawable(content.uiIcon) : icon;
        }

        public String localizedName(){
            return Core.bundle.get("techtree." + name, name);
        }

        public void setupRequirements(ItemStack[] requirements){
            this.requirements = requirements;
            this.finishedRequirements = new ItemStack[requirements.length];

            //load up the requirements that have been finished if settings are available
            for(int i = 0; i < requirements.length; i++){
                finishedRequirements[i] = new ItemStack(requirements[i].item, Core.settings == null ? 0 : Core.settings.getInt("req-" + content.name + "-" + requirements[i].item.name));
            }
        }

        /** Resets finished requirements and saves. */
        public void reset(){
            for(ItemStack stack : finishedRequirements){
                stack.amount = 0;
            }
            save();
        }

        /** Removes this node from the tech tree. */
        public void remove(){
            all.remove(this);
            if(bioParent != null){
                bioParent.bioChildren.remove(this);
            }
        }

        /** Flushes research progress to settings. */
        public void save(){

            //save finished requirements by item type
            for(ItemStack stack : finishedRequirements){
                Core.settings.put("req-" + content.name + "-" + stack.item.name, stack.amount);
            }
        }
    }
}