package hva.employee;

import java.io.Serial;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hva.Animal;
import hva.Habitat;
import hva.Species;
import hva.Vaccine;
import hva.Vaccination;
import hva.exceptions.NoSuchResponsabilityException;
import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import hva.strategies.SatisfactionStrategy;
import hva.strategies.VaccinationStrategy;
import hva.strategies.ZookeeperSatisfactionStrategy;
import hva.strategies.ZookeeperVaccinationStrategy;

/**
 * The {@code Zookeeper} class represents a zookeeper employed at the zoo.
 * It contains information about the zookeeper's identifier, name, and the habitats they are responsible for.
 * This class extends the {@link Employee} class and implements {@link Serializable}, allowing instances to be serialized.
 * 
 * <p>
 * Each zookeeper is uniquely identified by their ID, and equality between two zookeepers is determined by their ID.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_habitats: A map of habitats that the zookeeper is responsible for, keyed by habitat ID.</li>
 * </ul>
 */
public class Zookeeper extends Employee {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitats that this zookeeper is responsible for. */
    private Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    
    /**
     * Constructs a new {@code Zookeeper} with the specified ID and name.
     * 
     * @param id The unique identifier of the zookeeper.
     * @param name The name of the zookeeper.
     */
    public Zookeeper(String id, String name) {
        super(id, name);
        setSatisfactionStrategy(new ZookeeperSatisfactionStrategy(this));
        setVaccinationStrategy(new ZookeeperVaccinationStrategy(this));
    }

    /**
     * Returns a collection of all habitats that the zookeeper is responsible for.
     * 
     * @return A collection of all habitats that the zookeeper is responsible for.
     */
    public Collection<Habitat> allHabitats() {
        Collection<Habitat> habitatsCollection = Collections.unmodifiableCollection(_habitats.values());
        return habitatsCollection;
    }

    /**
     * Assigns a habitat to the zookeeper.
     * 
     * @param habitat The habitat to assign.
     */
    public void assignHabitat(Habitat habitat) {
        _habitats.put(habitat.getId(), habitat);
        habitat.assignZookeeper(this);
    }

    /**
     * Unassigns the given habitat from the zookeeper.
     *
     * @param habitat The habitat to be unassigned.
     * @throws NoSuchResponsabilityException if the habitat is not assigned to the zookeeper.
     */
    public void unassignHabitat(Habitat habitat) throws NoSuchResponsabilityException {
        if (_habitats.remove(habitat.getId()) == null)
            throw new NoSuchResponsabilityException(this.getId(), habitat.getId());
        habitat.unassignZookeeper(this);
    }

    /**
     * Check whether two zookeepers are equal. Two zookeepers are considered equal if they
     * have the same identifier.
     * 
     * @param zookeeper the other zookeeper
     * @return true, if they have the same id; false, otherwise.
     */
    @Override
    public boolean equals(Object zookeeper) {
        return zookeeper instanceof Zookeeper && super.equals(zookeeper);
    }

    
    /**
     * Returns a string representation of the Zookeeper object.
     * The format of the returned string is "TRT|super.toString()|habitats",
     * where super.toString() is the string representation of the superclass,
     * and habitats is a comma-separated list of habitat keys if any exist.
     * 
     * @return A string representation of the Zookeeper object.
     */
    @Override
    public String toString() {
        String habitatsString = _habitats.isEmpty() ? "" : "|" + String.join(",", _habitats.keySet());
        return "TRT|" + super.toString() + habitatsString;
    }
}
