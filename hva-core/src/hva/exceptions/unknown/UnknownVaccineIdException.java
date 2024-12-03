package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownVaccineIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The vaccine's key. */
	private final String _key;

	/** @param key */
    public UnknownVaccineIdException (String vaccineId) {
        _key = vaccineId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}
