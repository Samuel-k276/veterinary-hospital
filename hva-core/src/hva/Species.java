package hva;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hva.employee.Veterinarian;

/**
 * The {@code Species} class represents a group of animals that share common characteristics.
 * Each species is identified by a unique ID and has a name, a collection of animals, and a collection of veterinarians.
 * This class implements {@link Serializable}, allowing species instances to be serialized.
 * 
 * <p>
 * A species contains and manages multiple animals, providing methods to add and remove them.
 * Each species can also have veterinarians assigned to it, which can be added or removed.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_id: The unique identifier of the species.</li>
 *   <li>_name: The name of the species.</li>
 *   <li>_animals: A collection of animals that belong to the species, keyed by animal ID.</li>
 *   <li>_veterinarians: A collection of veterinarians assigned to the species, keyed by veterinarian ID.</li>
 * </ul>
 */
public class Species implements Serializable {

    /** Class serial number for serialization purposes. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique identifier of the species. */
    private String _id;

    /** The name of the species. (must be unique) */
    private String _name;

    /** Animals that belong to the species. */
    private Map<String, Animal> _animals = new HashMap<>();

    /** Veterinarians assigned to the species. */
    private Map<String, Veterinarian> _veterinarians = new HashMap<>();

    /**
     * Constructs a new {@code Specie} with the specified ID and name.
     * 
     * @param id The unique identifier of the species.
     * @param name The name of the species.
     */
    public Species (String id, String name) {
        _id = id;
        _name = name;
    }

    /**
     * Returns the unique identifier of the species.
     * 
     * @return The species' ID.
     */
    public String getId() {
        return _id;
    }

     /**
     * Returns the unique name of the species.
     * 
     * @return The species' name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Retrieves the current population of animals of this species.
     *
     * @return the number of animals in the population.
     */
    public int getPopulation() {
        return _animals.size();
    }

    /**
     * Returns the number of veterinarians assigned to this species.
     *
     * @return the number of veterinarians
     */
    public int getNumberOfVeterinarians() {
        return _veterinarians.size();
    }

    /**
     * Adds an animal to the species.
     * 
     * @param animal The animal to be added to the species.
     */
    public void addAnimal(Animal animal) {
        _animals.put(animal.getId(), animal);
    }

    /**
     * Assigns a veterinarian to the species by adding the veterinarian to the internal map.
     *
     * @param veterinarian The veterinarian to be assigned.
     */
    public void assignVeterinarian(Veterinarian veterinarian) {
        _veterinarians.put(veterinarian.getId(), veterinarian);
    }

    /**
     * Unassigns a veterinarian from the species.
     * 
     * @param veterinarian The veterinarian to be unassigned.
     */
    public void unassignVeterinarian(Veterinarian veterinarian) {
        _veterinarians.remove(veterinarian.getId());
    }

    /**
     * Compares this species to the specified object for equality.
     * 
     * <p>Two species are considered equal if they have the same ID or name.</p>
     * 
     * @param specie The object to compare with this species.
     * @return {@code true} if the specified object is a {@code Species} with the same ID or name; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object species) {
        return species instanceof Species && (_id.equals(((Species) species).getId()) || _name.equals(((Species) species).getName()));
    }

    /**
     * Generates a hash code for the Species object based on its unique identifier (_id) and name (_name).
     * This method overrides the default hashCode implementation to ensure that two Species objects
     * with the same _id and _name produce the same hash code.
     *
     * @return an integer hash code value for the Species object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(_id, _name);
    }
}
