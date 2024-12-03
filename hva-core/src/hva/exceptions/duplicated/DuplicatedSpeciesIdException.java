package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given id has already been used.
 */
public class DuplicatedSpeciesIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The species's key. */
    private final String _key;

    /** @param speciesId */
    public DuplicatedSpeciesIdException(String speciesId) {
        _key = speciesId;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}