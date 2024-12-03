package hva.exceptions.duplicated;

import java.io.Serial;

/**
 * Given id has already been used.
 */
public class DuplicatedEmployeeIdException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee's key. */
    private final String _key;

    /** @param employeeId */
    public DuplicatedEmployeeIdException(String employeeId) {
        _key = employeeId;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}