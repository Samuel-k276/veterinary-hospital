package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given id has already been used.
 */
public class DuplicatedVaccineIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine's key. */
    private final String _key;

    /** @param vaccineId */
    public DuplicatedVaccineIdException(String vaccineId) {
        _key = vaccineId;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}