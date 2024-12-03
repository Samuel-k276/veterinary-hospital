package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given name has already been used.
 */
public class DuplicatedSpeciesNameException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The species's name. */
    private final String _name;

    /** @param speciesName */
    public DuplicatedSpeciesNameException(String speciesName) {
        _name = speciesName;
    }

    /** @return the name */
    public String getName() {
        return _name;
    }
}
