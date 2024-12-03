package hva.exceptions;

import java.io.Serial;

public class WrongVaccineException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine's key. */
    private final String _vaccineKey;
    
    /** The animal's key. */
    private final String _animalKey;

    /**
     * @param veterinarianId
     * @param speciesId
     */
    public WrongVaccineException(String vaccineId, String animalId) {
        _vaccineKey = vaccineId;
        _animalKey = animalId;
    }

    /** @return the vaccine key */
    public String getVaccineKey() {
		return _vaccineKey;
	}

    /** @return the animal key */
    public String getAnimalKey() {
		return _animalKey;
	}
}
