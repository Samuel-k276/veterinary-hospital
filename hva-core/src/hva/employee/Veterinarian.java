package hva.employee;

import java.io.Serial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hva.Species;
import hva.Vaccine;
import hva.Vaccination;
import hva.exceptions.NoSuchResponsabilityException;
import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import hva.strategies.SatisfactionStrategy;
import hva.strategies.VaccinationStrategy;
import hva.strategies.VeterinarianSatisfactionStrategy;
import hva.strategies.VeterinarianVaccinationStrategy;
import hva.Animal;

/**
 * The {@code Veterinarian} class represents a veterinarian employed at the veterinary clinic.
 * It contains information about the veterinarian's identifier, name, and the species they are responsible for.
 * This class extends the {@link Employee} class and implements {@link Serializable}, allowing instances to be serialized.
 * 
 * <p>
 * Each veterinarian is uniquely identified by their ID, and equality between two veterinarians is determined by their ID.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_species: A map of species that the veterinarian is responsible for, keyed by species ID.</li>
 * </ul>
 */
public class Veterinarian extends Employee {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;
    
    /** The species that this veterinarian can vaccinate. */
    private Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** The vaccinations administered by the veterinarian. */
    private List<Vaccination> _vaccinations = new ArrayList<>();

    /**
     * Constructs a new {@code Veterinarian} with the specified ID and name.
     * 
     * @param id The unique identifier of the veterinarian.
     * @param name The name of the veterinarian.
     */
    public Veterinarian(String id, String name) {
        super(id, name);
        setSatisfactionStrategy(new VeterinarianSatisfactionStrategy(this));
        setVaccinationStrategy(new VeterinarianVaccinationStrategy(this));
    }

    /**
     * Retrieves an unmodifiable collection of all vaccinations.
     *
     * @return an unmodifiable {@link Collection} of {@link Vaccination} objects.
     */
    public Collection<Vaccination> getAllVaccinations() {
        return Collections.unmodifiableCollection(_vaccinations);
    }

    /**
     * @return all the species that this veterinarian takes care of as an unmodifiable collection.
     */
    public Collection<Species> allSpecies() {
        Collection<Species> speciesCollection = Collections.unmodifiableCollection(_species.values());
        return speciesCollection;
    }

    /**
     * Assign a species to the veterinarian. The veterinarian will be responsible for the species.
     * 
     * @param species the species to assign to the veterinarian.
     */
    public void assignSpecies(Species species) {
        _species.put(species.getId(), species);
        species.assignVeterinarian(this);
    }

    /**
     * Unassign a species from the veterinarian. The veterinarian will no longer be responsible for the species.
     * 
     * @param species the species to unassign from the veterinarian.
     * @throws NoSuchResponsabilityException if the veterinarian is not responsible for the species.
     */
    public void unassignSpecies(Species species) throws NoSuchResponsabilityException {
        if (_species.remove(species.getId()) == null) 
            throw new NoSuchResponsabilityException(this.getId(), species.getId());
        species.unassignVeterinarian(this);
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
     * Vaccinates an animal with the specified vaccine.
     *
     * @param vaccine the vaccine to be administered
     * @param animal the animal to be vaccinated
     * @return the vaccination record
     * @throws UnauthorizedVeterinarianException if the veterinarian is not authorized to vaccinate the animal.
     */
    public Vaccination administerVaccine(Vaccine vaccine, Animal animal) throws UnauthorizedVeterinarianException {
        if (!isVeterinarianAuthorized(animal))
            throw new UnauthorizedVeterinarianException(this.getId(), animal.getSpecies().getId());
        vaccine.actOnAnimal(animal);
        Vaccination vaccination = new Vaccination(vaccine, this, animal);
        return vaccination;
    }

    /**
     * Checks if the veterinarian is authorized to vaccinate the animal.
     * 
     * @param animal the animal to check the authorization against
     * @return {@code true} if the veterinarian is authorized to vaccinate the animal; {@code false} otherwise.
     */
    public boolean isVeterinarianAuthorized(Animal animal) {
        return _species.containsKey(animal.getSpecies().getId());
    }

    /**
     * Check whether two veterinarians are equal. Two veterinarians are considered equal if they
     * have the same identifier.
     * 
     * @param veterinarian the other veterinarian
     * @return true, if they have the same id; false, otherwise.
     */
    @Override
    public boolean equals(Object veterinarian) {
        return veterinarian instanceof Veterinarian && super.equals(veterinarian);
    }

    /**
     * Returns a string representation of the veterinarian in a formatted manner.
     * 
     * <p>The string includes the veterinarian's ID, name, and the species they are responsible for.</p>
     * 
     * @return a string representation of the veterinarian.
     */
    @Override
    public String toString() {
        String speciesString = _species.isEmpty() ? "" : "|" + String.join(",", _species.keySet());
        return "VET|" + super.toString() + speciesString;
    }
}
