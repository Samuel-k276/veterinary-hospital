package hva.tree;

import java.io.Serial;
import java.io.Serializable;

import hva.Habitat;
import hva.enums.Season;
import hva.strategies.TreeWorkStrategy;
import hva.strategies.WorkStrategy;
import hva.treeStates.EvergreenTreeState;
import hva.treeStates.TreeState;


/**
 * The {@code Tree} class represents an abstract concept of a tree with various attributes such as ID, name, age, 
 * cleaning difficulty, and states. It provides methods to manipulate and retrieve these attributes, as well as 
 * to calculate the effort required to clean the tree.
 * 
 * <p>This class implements the {@code Serializable} interface to allow its instances to be serialized and deserialized.</p>
 * 
 * <p>Attributes:</p>
 * <ul>
 *   <li>{@code _id} - The unique identifier of the tree.</li>
 *   <li>{@code _name} - The name of this tree instance.</li>
 *   <li>{@code _age} - The age of the tree.</li>
 *   <li>{@code _cleaningDifficulty} - The base difficulty of cleaning the tree.</li>
 *   <li>{@code _birthState} - The birth state of the tree.</li>
 *   <li>{@code _state} - The current state of the tree.</li>
 *   <li>{@code _workStrategy} - The work strategy used to calculate the effort required to clean the tree.</li>
 * </ul>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@code setBirthState(TreeState birthState)} - Sets the birth state of the tree and updates its current state to the given birth state.</li>
 *   <li>{@code getTreeState()} - Retrieves the current state of the tree.</li>
 *   <li>{@code setState(TreeState newState)} - Sets the state of the tree to the given state.</li>
 *   <li>{@code advanceSeason()} - Advances the season of the tree to the next one.</li>
 *   <li>{@code cleaningEffort()} - Calculates the effort required to clean the tree.</li>
 * </ul>
 * 
 * <p>Note: This class is abstract and cannot be instantiated directly. Subclasses should provide specific implementations.</p>
 */
public abstract class Tree implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;
    
    /** The unique identifier of the tree. */
    private String _id;

    /** The name of this tree instance. */
    private String _name;

    /** The age of the tree. */
    private double _age;

    /** The base difficulty of cleaning the tree. */
    private int _cleaningDifficulty;

    /** The current state of the tree. */
    private TreeState _state;

    /** The work strategy used to calculate the effort required to clean the tree. */
    private WorkStrategy _workStrategy;

    /**
     * Constructs a new Tree object with the specified ID, name, age, and cleaning difficulty.
     *
     * @param id the unique identifier of the tree.
     * @param name the name of the tree.
     * @param age the age of the tree.
     * @param cleaningDifficulty the base difficulty of cleaning the tree.
     */
    public Tree(String id, String name, int age, int cleaningDifficulty) {
        _id = id;
        _name = name;
        _age = age;
        _cleaningDifficulty = cleaningDifficulty;
        _workStrategy = new TreeWorkStrategy(this);
    }

    /**
     * @return the tree's id.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return the tree's name.
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the tree's cleaning difficulty.
     */
    public int getCleaningDificulty() {
        return _cleaningDifficulty;
    }

    /**
     * Retrieves the age of the tree.
     *
     * @return the age of the tree as an integer.
     */
    public int getAge() {
        return (int)Math.floor(_age);
    }

    /**
     * Retrieves the current state of the tree.
     *
     * @return the current state of the tree as a {@link TreeState} object.
     */
    public TreeState getTreeState() {
        return _state;
    }

    /**
     * Sets the state of the tree to the given state.
     *
     * @param newState the new state of the tree to be set
     */
    public void setState(TreeState newState) {
        _state = newState;
    }

    /**
     * Retrieves the biological cycle of the tree.
     *
     * @return the biological cycle of the tree as a string.
     */
    protected String getBiologicalCycle() {
        if (_state != null) {
            return _state.getBiologicalCycle();
        }
        return null;
    }

    
    public int getSeasonalEffort() {
        return _state.getSeasonalEffort();
    }

    /**
     * Advances the season of the tree to the next one.
     */
    public void advanceSeason() {
        _state.nextTreeState();
        incrementAge();
    }

    /**
     * Increments the age of the tree by a season.
     */
    private void incrementAge() {
        _age += 0.25;
    }


    public Season getSeason() {
        return _state.getSeason();
    }

    /**
     * Calculates the effort required to clean the tree.
     *
     * @return the effort required to clean the tree as a double.
     */
    public double cleaningEffort() {
        return _workStrategy.calculateWork();
    }   


    /**
     * Compares this Tree object to the specified object for equality.
     * 
     * @param tree the object to be compared for equality with this Tree.
     * @return true if the specified object is an instance of Tree and has the same ID as this Tree; false otherwise.
     */
    @Override
    public boolean equals(Object tree) {
        return tree instanceof Tree && _id.equals(((Tree) tree).getId());
    }


    /**
     * Returns a string representation of the Tree object.
     * The format of the returned string is:
     * "ÁRVORE|id|name|age|cleaningDifficulty"
     *
     * @return a string representation of the Tree object.
     */
    @Override
    public String toString() {
        return "ÁRVORE|" + _id + "|" + _name + "|" + getAge() + "|" + _cleaningDifficulty;
    }
    
}
