package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class DeciduousTreeStateWinter extends DeciduousTreeState {
    
    public DeciduousTreeStateWinter(Tree tree) {
        super(tree, 0, "SEMFOLHAS", Season.WINTER);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new DeciduousTreeStateSpring(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new DeciduousTreeStateWinter(newTree); 
    }
}
