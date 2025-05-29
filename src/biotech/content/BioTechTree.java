package biotech.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

public class BioTechTree extends TechTree {

    private static BioTechNode context = null;

    public static Seq<BioTechNode> all = new Seq<>();
    public static Seq<BioTechNode> roots = new Seq<>();

    public static TechNode bioNodeRoot(String name, UnlockableContent content, Runnable children){
        return nodeRoot(name, content, false, children);
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

    public static class BioTechNode extends TechNode {
        int importance;
        public final Seq<BioTechNode> children = new Seq<>();

        public BioTechNode(TechNode parent, UnlockableContent content, ItemStack[] requirements, int importance) {
            super(parent, content, requirements);
            this.importance = importance;
        }
    }
}
