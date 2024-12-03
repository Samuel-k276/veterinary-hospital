package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class EvergreenTreeStateSummer extends EvergreenTreeState {
    
    public EvergreenTreeStateSummer(Tree tree) {
        super(tree, 1, "COMFOLHAS", Season.SUMMER);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new EvergreenTreeStateAutumn(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new EvergreenTreeStateSummer(newTree); 
    }
}
