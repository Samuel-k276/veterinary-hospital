package hva;

import java.io.Serial;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import hva.employee.Veterinarian;
import hva.employee.Zookeeper;
import hva.enums.HabitatInfluence;
import hva.strategies.HabitatWorkStrategy;
import hva.strategies.WorkStrategy;
import hva.tree.Tree;

/**
 * The {@code Habitat} class represents a natural or artificial environment where animals and trees coexist. 
 * Each habitat is identified by a unique ID and has a name, area, and collections of trees and animals.
 * This class implements {@link Serializable}, allowing habitat instances to be serialized.
 * 
 * <p>
 * A habitat contains and manages multiple trees and animals, providing methods to add and remove them.
 * Each habitat also has an area, which can be adjusted. Equality between two habitats is determined 
 * by their unique ID.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_id: The unique identifier of the habitat.</li>
 *   <li>_name: The name of the habitat.</li>
 *   <li>_area: The total area of the habitat (in square meters).</li>
 *   <li>_trees: A collection of trees located within the habitat, keyed by tree ID (case-insensitive).</li>
 *   <li>_animals: A collection of animals residing in the habitat, keyed by animal ID (case-insensitive).</li>
 * </ul>
 */
public class Habitat implements Serializable {

    /** Class serial number for serialization purposes. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique identifier of the habitat. */
    private String _id;

    /** The habitat's name. */
    private String _name;

    /** The total area of the habitat. */
    private int _area = 0;

    /** The work strategy used to calculate the work done in the habitat. */
    private WorkStrategy _workStrategy;

    /** Trees located within the habitat. */
    private Map<String, Tree> _trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Animals residing in the habitat. */
    private Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Zookeepers assigned to the habitat. */
    private Map<String, Zookeeper> _zookeepers = new HashMap<>();

    /** The influence of the habitat on each species of animal. */
    private Map<Species, HabitatInfluence> _influenceBySpecies = new HashMap<>();

    /**
     * Constructs a new {@code Habitat} with the specified ID, name, and area.
     * 
     * @param id The unique identifier of the habitat.
     * @param name The name of the habitat.
     * @param area The area of the habitat.
     */
    public Habitat(String id, String name, int area) {
        _id = id;
        _name = name;
        _area = area;
        _workStrategy = new HabitatWorkStrategy(this);
    }

    /**
     * @return the habitat's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return the habitat's name.
     */
    public String getName()  {
        return _name;
    }

    /**
     * @return the habitat's area.
     */
    public int getArea()  {
        return _area;
    }

    /**
     * @return the habitat's number of animals.
     */
    public int getPopulation()  {
        return _animals.size();
    }

    /**
     * Returns the number of zookeepers assigned to this habitat.
     *
     * @return the number of zookeepers
     */
    public int getNumberOfZookeepers() {
        return _zookeepers.size();
    }

    /**
     * Returns all the trees in the habitat as an unmodifiable collection.
     * 
     * @return A collection of trees in the habitat.
     */
    public Collection<Tree> allTrees() {
        Collection<Tree> treeCollection = Collections.unmodifiableCollection(_trees.values());
        return treeCollection;
    }

    /**
     * Returns all the animals in the habitat as an unmodifiable collection.
     * 
     * @return A collection of animals in the habitat.
     */
    public Collection<Animal> allAnimals() {
        Collection<Animal> animalCollection = Collections.unmodifiableCollection(_animals.values());
        return animalCollection;
    }

    /**
     * Determines the influence of the habitat on a given animal.
     * If the species of the animal is not already present in the influence map,
     * it will be added with a default influence of NEUTRAL.
     *
     * @param animal the animal whose habitat influence is to be determined
     * @return the influence of the habitat on the specified animal
     */
    public HabitatInfluence influenceOnAnimal(Animal animal) {
        Species species = animal.getSpecies();
        return _influenceBySpecies.computeIfAbsent(species, k -> HabitatInfluence.NEUTRAL);
    }

    /**
     * Changes the habitat's area to the specified value.
     * 
     * @param area The new area of the habitat.
     */
    public void changeArea(int area) {
        _area = area;
    }

    /**
     * Changes the influence of a given species on the habitat.
     *
     * @param species the species whose influence is to be changed
     * @param influence the new influence to be set for the species
     */
    public void changeInfluenceOnSpecies(Species species, HabitatInfluence influence) {
        _influenceBySpecies.put(species, influence);
    }

    /**
     * Adds a tree to the habitat, effectively "planting" it.
     * 
     * @param tree The tree to be added to the habitat.
     */
    public void addTree(Tree tree) {
        _trees.put(tree.getId(), tree);
    }

    /**
     * Adds an animal to the habitat.
     * 
     * @param animal The animal to be added to the habitat.
     */
    public void addAnimal(Animal animal) {
        _animals.put(animal.getId(), animal);
    } 

    /**
     * Removes an animal from the habitat.
     * 
     * @param animal The animal to be removed.
     * @return The animal that was removed from the habitat.
     */
    public Animal removeAnimal(Animal animal) {
        return _animals.remove(animal.getId());
    }

    /**
     * Assigns a zookeeper to the habitat.
     *
     * @param zookeeper the Zookeeper object to be assigned to the habitat.
     */
    public void assignZookeeper(Zookeeper zookeeper) {
        _zookeepers.put(zookeeper.getId(), zookeeper);
    }

    /**
     * Unassigns a zookeeper from the habitat.
     * 
     * @param zookeeper The zookeeper to be unassigned.
     */
    public void unassignZookeeper(Zookeeper zookeeper) {
        _zookeepers.remove(zookeeper.getId());
    }

    /**
     * Counts the number of animals in the habitat that are of the same species as the given animal,
     * excluding the given animal itself.
     *
     * @param animal the animal whose species is to be matched against other animals in the habitat
     * @return the number of animals in the habitat that are of the same species as the given animal
     */
    public int sameSpecies(Animal animal) {
        int sameSpeciesAnimalCount = 0;
        for (Animal habitatAnimal : _animals.values()) {
            if (animal.getSpecies().equals(habitatAnimal.getSpecies()) && !animal.equals(habitatAnimal)) 
                sameSpeciesAnimalCount++;
        }
        return sameSpeciesAnimalCount;
    }

    /**
     * Calculates the number of different species in the habitat excluding the given animal.
     *
     * @param animal the animal to be excluded from the count of different species
     * @return the number of different species in the habitat excluding the given animal
     */
    public int differentSpecies(Animal animal) {
        return getPopulation() - sameSpecies(animal) - 1;
    }

    /**
     * Calculates the work done in the habitat using the current work strategy.
     *
     * @return the amount of work calculated by the work strategy
     */
    public double workInHabitat() {
        return _workStrategy.calculateWork();
    }

    /**
     * Generates a string representation of all trees in the habitat.
     * Each tree's string representation is appended to the result, 
     * separated by a line separator.
     *
     * @return A string containing the string representations of all trees, 
     *         each followed by a line separator.
     */
    private String allTreesString() {
        StringBuilder allTrees = new StringBuilder();
        for (Tree tree : _trees.values()) {
            allTrees.append(tree.toString()).append(System.lineSeparator());
        }
        return allTrees.toString();
    }

    /**
     * Compares this habitat to the specified object for equality.
     * 
     * <p>Two habitats are considered equal if they have the same ID.</p>
     * 
     * @param habitat The object to compare with this habitat.
     * @return {@code true} if the specified object is a {@code Habitat} with the same ID; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object habitat) {
        return habitat instanceof Habitat && _id.equals(((Habitat) habitat).getId());
    }

    /**
     * Returns a string representation of the habitat in a formatted manner.
     * 
     * <p>The string includes the habitat's ID, name, area, number of trees, and a list of all trees.</p>
     * 
     * @return A formatted string representing the habitat.
     */
    @Override
    public String toString() {
        return String.format("HABITAT|%s|%s|%d|%d%s%s", _id, _name, _area, _trees.size(), System.lineSeparator(), allTreesString()).trim();
    }
}
