package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given id has already been used.
 */
public class DuplicatedAnimalIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal's key. */
    private final String _key;

    /** @param animalId */
    public DuplicatedAnimalIdException(String animalId) {
        _key = animalId;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}
