package hva.exceptions;

import java.io.Serial;

/**
 * Launched when a veterinarian is unauthorized to vaccinate a certain species.
 */
public class UnauthorizedVeterinarianException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The veterinarian's key. */
    private final String _vetKey;
    
    /** The species's key. */
    private final String _speciesKey;

    /**
     * @param veterinarianId
     * @param speciesId
     */
    public UnauthorizedVeterinarianException(String veterinarianId, String speciesId) {
        _vetKey = veterinarianId;
        _speciesKey = speciesId;
    }

    /** @return the veterinarian key */
    public String getVeterinarianKey() {
		return _vetKey;
	}

    /** @return the species key */
    public String getSpeciesKey() {
		return _speciesKey;
	}
}
