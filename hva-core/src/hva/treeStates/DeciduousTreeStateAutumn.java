package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class DeciduousTreeStateAutumn extends DeciduousTreeState {
    
    public DeciduousTreeStateAutumn(Tree tree) {
        super(tree, 5, "LARGARFOLHAS", Season.AUTUMN);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new DeciduousTreeStateWinter(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new DeciduousTreeStateAutumn(newTree); 
    }
}
