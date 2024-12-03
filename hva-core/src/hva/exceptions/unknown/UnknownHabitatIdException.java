package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownHabitatIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitat's key. */
	private final String _key;

	/** @param key */
    public UnknownHabitatIdException (String habitatId) {
        _key = habitatId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}
