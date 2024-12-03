package hva;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hva.enums.VaccineDamage;
import hva.strategies.AnimalSatisfactionStrategy;
import hva.strategies.SatisfactionStrategy;

/**
 * The {@code Animal} class represents an animal with an ID, name, species, habitat, and health history.
 * It also tracks vaccinations and allows the habitat to be changed. This class implements {@link Serializable},
 * allowing instances to be serialized.
 * 
 * <p>
 * Each animal is uniquely identified by its ID, and equality between two animals is determined by their ID.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_id: The unique identifier of the animal.</li>
 *   <li>_name: The name of the animal.</li>
 *   <li>_species: The species of the animal.</li>
 *   <li>_habitat: The current habitat where the animal is located.</li>
 *   <li>_healthHistory: A list of the animal's past reactions to vaccinations.</li>
 *   <li>_vaccinations: A list of vaccinations the animal has received.</li>
 * </ul>
 */
public class Animal implements Serializable {

    /** Class serial number for serialization purposes. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique identifier of the animal. */
    private String _id;
    
    /** The animal's name. */
    private String _name; 

    /** The animal's species. */
    private Species _species;

    /** The habitat where the animal is. */
    private Habitat _habitat;

    /** The health history of the animal, represented as a list of strings. */
    private List<VaccineDamage> _healthHistory = new ArrayList<>();

    /** The vaccinations received by the animal. */
    private List<Vaccination> _vaccinations = new ArrayList<>();

    /** The strategy used to determine the satisfaction level of the animal. */
    private SatisfactionStrategy _satisfactionStrategy;

    /**
     * Constructs a new {@code Animal} with the specified ID, name, species, and habitat.
     * 
     * @param id The unique identifier of the animal.
     * @param name The name of the animal.
     * @param species The species of the animal.
     * @param habitat The current habitat where the animal is located.
     */
    public Animal(String id, String name, Species species, Habitat habitat) {
        _id = id;
        _name = name;
        _species = species;
        _habitat = habitat;
        _satisfactionStrategy = new AnimalSatisfactionStrategy(this);
    }

    /**
     * Returns the unique identifier of the animal.
     * 
     * @return The animal's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return the animal's name.
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the animal's species.
     */
    public Species getSpecies() {
        return _species;
    }

    /**
     * @return the habitat where the animal is.
     */
    public Habitat getHabitat() {
        return _habitat;
    }

    /**
     * @return the animal's health history.
     */
    public List<String> getHealthHistory() {
        return _healthHistory.stream().map(VaccineDamage::getResult).collect(Collectors.toList());
    }

    /**
     * @return all the vaccinations of this animal as an unmodifiable collection.
     */
    public Collection<Vaccination> allVaccinations() {
        Collection<Vaccination> vaccinationsCollection = Collections.unmodifiableCollection(_vaccinations);
        return vaccinationsCollection;
    }

    
    /**
     * Changes the habitat of the animal to the specified new habitat.
     * 
     * @param newHabitat the new habitat to which the animal will be moved
     */
    public void changeHabitat(Habitat newHabitat) {
        _habitat.removeAnimal(this);
        _habitat = newHabitat;
        newHabitat.addAnimal(this);
    }

    /**
     * Administers a vaccine to the animal and records the vaccine damage in the health history.
     *
     * @param vaccineDamage The damage caused by the vaccine to be recorded in the health history.
     */
    public void takeVaccine(VaccineDamage vaccineDamage) {
        _healthHistory.add(vaccineDamage);
    }

    /**
     * Adds a vaccination to the list of vaccinations for the animal.
     *
     * @param vaccination the Vaccination object to be added
     */
    public void addVaccination(Vaccination vaccination) {
        _vaccinations.add(vaccination);
    }

    /**
     * Calculates and returns the satisfaction value using the defined satisfaction strategy.
     *
     * @return the calculated satisfaction value as a double.
     */
    public double satisfaction() {
        return _satisfactionStrategy.calculateSatisfaction();
    }

    /**
     * Compares this animal to the specified object for equality.
     * 
     * <p>Two animals are considered equal if they have the same ID.</p>
     * 
     * @param animal The object to compare with this animal.
     * @return {@code true} if the specified object is an {@code Animal} with the same ID; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object animal) {
        return animal instanceof Animal && _id.equals(((Animal) animal).getId());
    }

    /**
     * Returns a string representation of the animal in a formatted manner.
     * 
     * <p>The string includes the animal's ID, name, species ID, health history, and habitat ID.</p>
     * 
     * @return A formatted string representing the animal.
     */
    @Override
    public String toString() {
        String healthHistory = "VOID";
        if (!_healthHistory.isEmpty()) {
            healthHistory = String.join(",", getHealthHistory());
        }
        return String.format("ANIMAL|%s|%s|%s|%s|%s", _id, _name, _species.getId(), healthHistory, _habitat.getId());
    }
}
