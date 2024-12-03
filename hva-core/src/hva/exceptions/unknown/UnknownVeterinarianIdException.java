package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownVeterinarianIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The veterinarian's key. */
	private final String _key;

	/** @param key */
    public UnknownVeterinarianIdException (String veterinarianId) {
        _key = veterinarianId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}
