package hva.treeStates;

import java.io.Serial;
import java.io.Serializable;

import hva.Habitat;
import hva.enums.Season;
import hva.tree.Tree;


/**
 * The {@code TreeState} class represents the state of a tree, including its seasonal effort,
 * biological cycle, and current season. This is an abstract class that must be extended by 
 * concrete implementations to define specific tree states.
 * 
 * <p>This class implements the {@code Serializable} interface to allow its instances to be 
 * serialized and deserialized.</p>
 * 
 * <p>Each {@code TreeState} object is associated with a specific {@code Tree} object and 
 * contains information about the effort required for the tree in the current season, the 
 * biological cycle of the tree, and the current season.</p>
 * 
 * <p>The class provides methods to retrieve the tree, seasonal effort, biological cycle, 
 * and season associated with the tree state. It also defines abstract methods for transitioning 
 * to the next tree state and creating a new tree state for a different tree.</p>
 * 
 * <p>The {@code equals} method is overridden to compare two {@code TreeState} objects based 
 * on their season.</p>
 */
public abstract class TreeState implements Serializable {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;
    
    /** The tree associated with this state. */
    private Tree _tree;

    /** The effort required for the tree in the current season. */
    private int _seasonalEffort;

    /** The biological cycle of the tree. */
    private String _biologicalCycle;

    /** The current season. */
    private Season _season;

    /**
     * Constructs a new {@code TreeState} with the specified tree, seasonal effort, biological cycle, and season.
     *
     * @param tree the tree associated with this state
     * @param seasonalEffort the effort required for the tree in the current season
     * @param biologicalCycle the biological cycle of the tree
     * @param season the current season
     */
    public TreeState(Tree tree, int seasonalEffort, String biologicalCycle, Season season) {
        _tree = tree;
        _seasonalEffort = seasonalEffort;
        _biologicalCycle = biologicalCycle;
        _season = season;
    }

    /**
     * Retrieves the tree associated with this state.
     *
     * @return the tree associated with this state.
     */
    public Tree getTree() {
        return _tree;
    }

    /**
     * Retrieves the seasonal effort value.
     *
     * @return the seasonal effort as an integer.
     */
    public int getSeasonalEffort() {
        return _seasonalEffort;
    }

    /**
     * Retrieves the biological cycle of the tree.
     *
     * @return the biological cycle as a string.
     */
    public String getBiologicalCycle() {
        return _biologicalCycle;
    }

    /**
     * Retrieves the current season.
     *
     * @return the current season.
     */
    public Season getSeason() {
        return _season;
    }

    /**
     * Updates the tree state to the next season.
     */
    public abstract void nextTreeState();

    /**
     * Creates a new tree state for the specified tree.
     *
     * @param newTree the tree for which to create a new tree state
     * @return a new tree state for the specified tree
     */
    public abstract TreeState createForNewTree(Tree newTree);

    /**
     * Compares this TreeState object to the specified object for equality.
     * 
     * @param treeState the object to compare with this TreeState
     * @return {@code true} if the specified object is an instance of TreeState and 
     *         the season of both TreeState objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object treeState) {
        return treeState instanceof TreeState && _season.equals(((TreeState) treeState).getSeason());
    }
}
