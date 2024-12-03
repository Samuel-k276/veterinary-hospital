package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class EvergreenTreeStateSpring extends EvergreenTreeState {
    
    public EvergreenTreeStateSpring(Tree tree) {
        super(tree, 1, "GERARFOLHAS", Season.SPRING);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new EvergreenTreeStateSummer(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new EvergreenTreeStateSpring(newTree); 
    }
}
