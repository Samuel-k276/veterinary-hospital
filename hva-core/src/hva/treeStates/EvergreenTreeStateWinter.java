package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class EvergreenTreeStateWinter extends EvergreenTreeState {

    public EvergreenTreeStateWinter(Tree tree) {
        super(tree, 2, "LARGARFOLHAS", Season.WINTER);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new EvergreenTreeStateSpring(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new EvergreenTreeStateWinter(newTree); 
    }
}
