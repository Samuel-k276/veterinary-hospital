package hva.treeStates;

import hva.enums.Season;
import hva.tree.Tree;

/**
 * The {@code DeciduousTreeState} class represents the state of a deciduous tree, including its seasonal effort,
 * biological cycle, and current season. This is an abstract class that must be extended by concrete implementations
 * to define specific deciduous tree states.
 * 
 * <p>This class extends the {@link TreeState} class and provides a common base for all deciduous tree states.</p>
 * 
 * <p>Each {@code DeciduousTreeState} object is associated with a specific {@code Tree} object and contains information
 * about the effort required for the tree in the current season, the biological cycle of the tree, and the current season.</p>
 * 
 * <p>The class provides methods to retrieve the tree, seasonal effort, biological cycle, and season associated with the tree state.
 * It also defines abstract methods for transitioning to the next tree state and creating a new tree state for a different tree.</p>
 * 
 * <p>The {@code equals} method is overridden to compare two {@code DeciduousTreeState} objects based on their season.</p>
 */
public abstract class DeciduousTreeState extends TreeState {

    /**
     * Constructs a new {@code DeciduousTreeState} with the specified tree, seasonal effort, biological cycle, and season.
     *
     * @param tree the tree associated with this state
     * @param seasonalEffort the effort required for the tree in the current season
     * @param biologicalCycle the biological cycle of the tree
     * @param season the current season
     */
    public DeciduousTreeState(Tree tree, int seasonalEffort, String biologicalCycle, Season season) {
        super(tree, seasonalEffort, biologicalCycle, season);
    }
}
