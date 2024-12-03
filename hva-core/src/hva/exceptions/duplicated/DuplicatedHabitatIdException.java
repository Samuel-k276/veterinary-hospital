package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given id has already been used.
 */
public class DuplicatedHabitatIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitat's key. */
    private final String _key;

    /** @param habitatId */
    public DuplicatedHabitatIdException(String habitatId) {
        _key = habitatId;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}