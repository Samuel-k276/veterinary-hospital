package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownSpeciesIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The species's key. */
	private final String _key;

	/** @param key */
    public UnknownSpeciesIdException (String speciesId) {
        _key = speciesId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}
