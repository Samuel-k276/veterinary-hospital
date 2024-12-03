package hva;

import java.io.Serial;
import java.io.Serializable;

import hva.employee.Veterinarian;

/**
 * The {@code Vaccination} class represents a record of a vaccination administered to a specific animal
 * by a veterinarian using a particular vaccine. This class implements {@link Serializable},
 * allowing instances to be serialized.
 * 
 * <p>
 * Each vaccination record contains references to the vaccine used, the veterinarian who administered
 * the vaccination, and the animal that received the vaccination.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_vaccine: The vaccine that was administered.</li>
 *   <li>_veterinarian: The veterinarian who administered the vaccine.</li>
 *   <li>_animal: The animal that received the vaccination.</li>
 * </ul>
 */
public class Vaccination implements Serializable {

    /** Class serial number for serialization purposes. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine that was administered. */  
    private Vaccine _vaccine;

     /** The veterinarian who administered the vaccine. */
    private Veterinarian _veterinarian;

    /** The animal that received the vaccination. */
    private Animal _animal;

    /**
     * Constructs a new {@code Vaccination} record with the specified vaccine, veterinarian, and animal.
     * 
     * @param vaccine The vaccine that was administered.
     * @param veterinarian The veterinarian who administered the vaccine.
     * @param animal The animal that received the vaccination.
     */
    public Vaccination(Vaccine vaccine, Veterinarian veterinarian, Animal animal) {
        _vaccine = vaccine;
        _veterinarian = veterinarian;
        _animal = animal;
    }

    /**
     * Retrieves the vaccine associated with this vaccination.
     *
     * @return the vaccine associated with this vaccination.
     */
    public Vaccine getVaccine() {
        return _vaccine;
    }

    /**
     * Retrieves the veterinarian associated with this vaccination.
     *
     * @return the veterinarian associated with this vaccination.
     */
    public Veterinarian getVeterinarian() {
        return _veterinarian;
    }

    /**
     * Retrieves the animal associated with this vaccination.
     *
     * @return the animal associated with this vaccination
     */
    public Animal getAnimal() {
        return _animal;
    }

    
    /**
     * Returns a string representation of the Vaccination object.
     * The format of the returned string is "REGISTO-VACINA|vaccineId|veterinarianId|animalSpeciesId".
     *
     * @return a formatted string containing the IDs of the vaccine, veterinarian, and animal species.
     */
    @Override
    public String toString() {
        return String.format("REGISTO-VACINA|%s|%s|%s", _vaccine.getId(), _veterinarian.getId(), _animal.getSpecies().getId());
    }
    
}
