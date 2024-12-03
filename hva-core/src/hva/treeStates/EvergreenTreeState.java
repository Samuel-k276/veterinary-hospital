package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

/**
 * The {@code EvergreenTreeState} class represents the state of an evergreen tree, including its seasonal effort,
 * biological cycle, and current season. This is an abstract class that must be extended by concrete implementations
 * to define specific evergreen tree states.
 * 
 * <p>This class extends the {@link TreeState} class and provides a common base for all evergreen tree states.</p>
 * 
 * <p>Each {@code EvergreenTreeState} object is associated with a specific {@code Tree} object and contains information
 * about the effort required for the tree in the current season, the biological cycle of the tree, and the current season.</p>
 * 
 * <p>The class provides methods to retrieve the tree, seasonal effort, biological cycle, and season associated with the tree state.
 * It also defines abstract methods for transitioning to the next tree state and creating a new tree state for a different tree.</p>
 * 
 * <p>The {@code equals} method is overridden to compare two {@code EvergreenTreeState} objects based on their season.</p>
 */
public abstract class EvergreenTreeState extends TreeState {

    /**
     * Constructs a new {@code EvergreenTreeState} with the specified tree, seasonal effort, biological cycle, and season.
     *
     * @param tree the tree associated with this state
     * @param seasonalEffort the effort required for the tree in the current season
     * @param biologicalCycle the biological cycle of the tree
     * @param season the current season
     */
    public EvergreenTreeState(Tree tree, int seasonalEffort, String biologicalCycle, Season season) {
        super(tree, seasonalEffort, biologicalCycle, season);
    }
}
