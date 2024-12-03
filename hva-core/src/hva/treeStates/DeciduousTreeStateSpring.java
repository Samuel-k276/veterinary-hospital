package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

public class DeciduousTreeStateSpring extends DeciduousTreeState {

    public DeciduousTreeStateSpring(Tree tree) {
        super(tree, 1, "GERARFOLHAS", Season.SPRING);
    }

    @Override
    public void nextTreeState() {
        getTree().setState(new DeciduousTreeStateSummer(getTree()));
    }

    @Override
    public TreeState createForNewTree(Tree newTree) {
        return new DeciduousTreeStateSpring(newTree); 
    }
}
