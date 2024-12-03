package hva.exceptions;

import java.io.Serial;

/**
 * Launched when an employee doesn't have a certain responsibility.
 */
public class NoSuchResponsabilityException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee's key. */
    private final String _employeeKey;
    
    /** The responsability's key. */
    private final String _responsabilityKey;

    /**
     * @param employeeId
     * @param responsabilityId
     */
    public NoSuchResponsabilityException(String employeeId, String responsabilityId) {
        _employeeKey = employeeId;
        _responsabilityKey = responsabilityId;
    }

    /** @return the employee key */
    public String getEmployeeKey() {
		return _employeeKey;
	}

    /** @return the responsability key */
    public String getResponsabilityKey() {
		return _responsabilityKey;
	}
}
