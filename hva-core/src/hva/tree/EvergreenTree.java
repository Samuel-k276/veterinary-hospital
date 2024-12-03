package hva.tree;

import java.io.Serial;

import hva.strategies.WorkStrategy;
import hva.treeStates.EvergreenTreeState;
import hva.treeStates.TreeState;

/**
 * The {@code EvergreenTree} class represents a type of tree that retains its leaves throughout the year.
 * It extends the {@code Tree} class and adds specific behavior for evergreen trees.
 * 
 * <p>This class includes methods to compare evergreen trees and to generate a string representation of an evergreen tree.</p>
 * 
 */
public class EvergreenTree extends Tree {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * Constructs a new {@code EvergreenTree} with the specified ID, name, age, and base cleaning difficulty.
     * 
     * @param id The unique identifier of the tree.
     * @param name The name of the tree.
     * @param age The age of the tree.
     * @param baseCleaningDificulty The base difficulty of cleaning the tree.
     */
    public EvergreenTree(String id, String name, int age, int baseCleaningDificulty) {
        super(id, name, age, baseCleaningDificulty);
    }
    

    /**
     * Compares this EvergreenTree to the specified object. The result is true if and only if 
     * the argument is not null and is an EvergreenTree object that is considered equal to this one.
     *
     * @param evergreenTree the object to compare this EvergreenTree against
     * @return true if the given object represents an EvergreenTree equivalent to this tree, false otherwise
     */
    @Override
    public boolean equals(Object evergreenTree) {
        return evergreenTree instanceof EvergreenTree && super.equals(evergreenTree);
    }


    /**
     * Returns a string representation of the EvergreenTree object.
     * The string includes the superclass's string representation,
     * followed by "|PERENE|" and the biological cycle of the tree.
     *
     * @return A string representation of the EvergreenTree object.
     */
    @Override
    public String toString() {
        return super.toString() + "|PERENE|" + getBiologicalCycle();
    }
}
