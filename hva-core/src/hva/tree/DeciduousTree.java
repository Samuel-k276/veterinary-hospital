package hva.tree;

import java.io.Serial;

import hva.strategies.WorkStrategy;
import hva.treeStates.DeciduousTreeState;
import hva.treeStates.TreeState;

/**
 * The {@code DeciduousTree} class represents a type of tree that loses its leaves seasonally.
 * It extends the {@code Tree} class and adds specific behavior for deciduous trees.
 * 
 * <p>This class includes methods to compare deciduous trees and to generate a string representation of a deciduous tree.</p>
 * 
 */
public class DeciduousTree extends Tree {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;
    
    /**
     * Constructs a new {@code DeciduousTree} with the specified ID, name, age, and base cleaning difficulty.
     * 
     * @param id The unique identifier of the tree.
     * @param name The name of the tree.
     * @param age The age of the tree.
     * @param baseCleaningDificulty The base difficulty of cleaning the tree.
     */
    public DeciduousTree(String id, String name, int age, int baseCleaningDificulty) {
        super(id, name, age, baseCleaningDificulty);
    }

    /**
     * Compares this DeciduousTree to the specified object. The result is true if and only if 
     * the argument is not null and is a DeciduousTree object that is considered equal to this one.
     *
     * @param deciduousTree the object to compare this DeciduousTree against
     * @return true if the given object represents a DeciduousTree equivalent to this tree, false otherwise
     */
    @Override
    public boolean equals(Object deciduousTree) {
        return deciduousTree instanceof DeciduousTree && super.equals(deciduousTree);
    }
   
    /**
     * Returns a string representation of the DeciduousTree object.
     * The string includes the superclass's string representation,
     * followed by "|CADUCA|" and the biological cycle of the tree.
     *
     * @return A string representation of the DeciduousTree object.
     */
    @Override
    public String toString() {
        return super.toString() + "|CADUCA|" + getBiologicalCycle();
    }
}
