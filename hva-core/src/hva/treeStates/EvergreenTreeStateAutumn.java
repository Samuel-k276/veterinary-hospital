package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class EvergreenTreeStateAutumn extends EvergreenTreeState {

    public EvergreenTreeStateAutumn(Tree tree) {
        super(tree, 1, "COMFOLHAS", Season.AUTUMN);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new EvergreenTreeStateWinter(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new EvergreenTreeStateAutumn(newTree); 
    }
}
