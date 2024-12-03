package hva.exceptions.unknown;

import java.io.Serial;

/**
 * Launched when a unknown key is used.
 */
public class UnknownTreeIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The tree's key. */
	private final String _key;

	/** @param key */
    public UnknownTreeIdException (String treeId) {
        _key = treeId;
    }

    /** @return the key */
	public String getKey() {
		return _key;
	}
}