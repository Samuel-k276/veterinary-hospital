package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownAnimalIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal's key. */
	private final String _key;

	/** @param key */
    public UnknownAnimalIdException (String animalId) {
        _key = animalId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}
