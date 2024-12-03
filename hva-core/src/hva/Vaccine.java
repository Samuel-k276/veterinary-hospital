package hva;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hva.enums.VaccineDamage;

/**
 * The {@code Vaccine} class represents a vaccine that can be administered to specific species.
 * It contains information about the vaccine's identifier, name and the target species.
 * This class implements {@link Serializable}, allowing instances to be serialized.
 * 
 * <p>
 * Each vaccine is uniquely identified by its ID, and equality between two vaccines is determined by their ID.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_id: The unique identifier of the vaccine.</li>
 *   <li>_name: The name of the vaccine.</li>
 *   <li>_species: A map of species that the vaccine targets, keyed by species ID.</li>
 * </ul>
 */
public class Vaccine implements Serializable {

    /** Class serial number for serialization purposes. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique identifier of the vaccine. */
    private String _id;

    /** The name of the vaccine. */
    private String _name;

     /** The species targeted by the vaccine. */
    private Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** The vaccinations administered using this vaccine. */
    private List<Vaccination> _vaccinations = new ArrayList<>();

    /**
     * Constructs a new {@code Vaccine} with the specified ID, name, and target species.
     * 
     * @param id The unique identifier of the vaccine.
     * @param name The name of the vaccine.
     * @param species A map of species that the vaccine targets.
     */
    public Vaccine(String id, String name, Map<String, Species> species) {
        _id = id;
        _name = name;
        _species = species;
    }

    /**
     * Returns the unique identifier of the vaccine.
     * 
     * @return The vaccine's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Returns the name of the vaccine.
     * 
     * @return The vaccine's name.
     */
    public String getName()  {
        return _name;
    }

    /**
     * Adds a vaccination record to the list of vaccinations.
     *
     * @param vaccination the vaccination record to be added
     */
    public void addVaccination(Vaccination vaccination) {
        _vaccinations.add(vaccination);
    }

    /**
     * Checks if the vaccine is adequate for the given animal.
     *
     * @param animal the animal to check the vaccine against
     * @return {@code true} if the vaccine is adequate for the animal's species, {@code false} otherwise
     */
    public boolean isVaccineAdequate(Animal animal) {
        Species animalSpecies = animal.getSpecies();
        return _species.containsKey(animalSpecies.getId());
    }

    /**
     * Administers a vaccine to the specified animal and determines the level of 
     * vaccine damage based on the adequacy of the vaccine for the animal.
     *
     * @param animal the animal to which the vaccine is administered
     */
    public void actOnAnimal(Animal animal) {
        VaccineDamage vaccineDamage = VaccineDamage.NONE;
        if (!isVaccineAdequate(animal)) {
            int damage = damageOnAnimal(animal);
            if (damage == 0)
                vaccineDamage = VaccineDamage.LITTLE;
            else if (damage <= 4) 
                vaccineDamage = VaccineDamage.MEDIUM;
            else 
                vaccineDamage = VaccineDamage.HIGH;
        }
        animal.takeVaccine(vaccineDamage);
    }

    /**
     * Calculates the damage inflicted on an animal based on the similarity of its species name
     * to other species names in the system.
     *
     * @param animal The animal on which the damage is being calculated.
     * @return The maximum damage value calculated based on the difference in name lengths
     *         and common characters between the animal's species name and other species names.
     */
    private int damageOnAnimal(Animal animal) {
        Species animalSpecies = animal.getSpecies();
        int damage = 0;
        for (Species species : _species.values()) {
            int nameSize = Math.max(animalSpecies.getName().length(), species.getName().length());
            int calculatedDamage = Math.abs(nameSize - commonCharacters(animalSpecies.getName(), species.getName()));
            damage = Math.max(damage, calculatedDamage);
        }
        return damage;
    }

    /**
     * Calculates the number of common characters between two strings.
     *
     * @param name1 the first string to compare
     * @param name2 the second string to compare
     * @return the number of common characters between the two strings
     */
    private int commonCharacters(String name1, String name2) {
        HashMap<Character, Integer> map1 = new HashMap<>();
        
        int commonCharacters = 0;

        for (char c : name1.toUpperCase().toCharArray())
            map1.put(c, map1.containsKey(c) ? map1.get(c) + 1 : 1);

        for (char c : name2.toUpperCase().toCharArray())
            if (map1.containsKey(c) && map1.get(c) > 0) {
                commonCharacters++;
                map1.put(c, map1.get(c) - 1);
            }
        
        return commonCharacters;
    }

    /**
     * Compares this vaccine to the specified object for equality.
     * 
     * <p>Two vaccines are considered equal if they have the same ID.</p>
     * 
     * @param vaccine The object to compare with this vaccine.
     * @return {@code true} if the specified object is a {@code Vaccine} with the same ID; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object vaccine) {
        return vaccine instanceof Vaccine && _id.equals(((Vaccine) vaccine).getId());
    }
 
    /**
     * Returns a string representation of the Vaccine object.
     * The format of the returned string is:
     * <pre>
     * VACINA|id|name|number_of_vaccinations|species_ids
     * </pre>
     * where <code>id</code> is the vaccine ID, <code>name</code> is the vaccine name,
     * <code>number_of_vaccinations</code> is the count of vaccinations,
     * and <code>species_ids</code> is a comma-separated list of species IDs.
     *
     * @return A string representation of the Vaccine object.
     */
    @Override
    public String toString() {
        String speciesIDs = "";
        if (!_species.isEmpty()) {
            speciesIDs = "|";
            speciesIDs += String.join(",", _species.keySet());
        }
        return String.format("VACINA|%s|%s|%d%s", _id, _name, _vaccinations.size(), speciesIDs);
    }
}
