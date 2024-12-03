package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given id has already been used.
 */
public class DuplicatedTreeIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The tree's key. */
    private final String _key;

    /** @param treeId */
    public DuplicatedTreeIdException(String treeId) {
        _key = treeId;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}