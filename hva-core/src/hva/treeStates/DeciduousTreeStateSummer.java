package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class DeciduousTreeStateSummer extends DeciduousTreeState {

    public DeciduousTreeStateSummer(Tree tree) {
        super(tree, 2, "COMFOLHAS", Season.SUMMER);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new DeciduousTreeStateAutumn(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new DeciduousTreeStateSummer(newTree); 
    }
}
