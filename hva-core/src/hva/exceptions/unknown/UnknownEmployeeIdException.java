package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownEmployeeIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee's key. */
	private final String _key;

	/** @param key */
    public UnknownEmployeeIdException (String employeeId) {
        _key = employeeId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}
