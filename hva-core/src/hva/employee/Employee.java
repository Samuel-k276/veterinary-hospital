package hva.employee;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import hva.Animal;
import hva.Vaccine;
import hva.Vaccination;
import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import hva.strategies.SatisfactionStrategy;
import hva.strategies.VaccinationStrategy;

/**
 * The {@code Employee} class represents an employee of the veterinary clinic.
 * It contains information about the employee's identifier and name.
 * This class implements {@link Serializable}, allowing instances to be serialized.
 * 
 * <p>
 * Each employee is uniquely identified by their ID, and equality between two employees is determined by their ID.
 * </p>
 * 
 * <p><b>Attributes:</b></p>
 * <ul>
 *   <li>_id: The unique identifier of the employee.</li>
 *   <li>_name: The name of the employee.</li>
 * </ul>
 */
public abstract class Employee implements Serializable {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee's id. */
    private String _id;

    /** The employee's name. */
    private String _name;

    /**
     * The strategy used to determine the satisfaction level of the employee.
     * This can be set to different implementations of the SatisfactionStrategy interface
     * to customize how employee satisfaction is calculated.
     */
    private SatisfactionStrategy _satisfactionStrategy;

    /**
     * The strategy used to determine the vaccinations administered by the employee.
     * This can be set to different implementations of the VaccinationStrategy interface
     * to customize how vaccinations are administered.
     */
    private VaccinationStrategy _vaccinationStrategy;

    /**
     * @param id
     * @param name
     */
    public Employee(String id, String name) {
        _id = id;
        _name = name;
    }

    /**
     * @return the employee's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return the employee's name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Sets the satisfaction strategy for the employee.
     *
     * @param satisfactionStrategy the strategy to be used for determining employee satisfaction
     */
    protected void setSatisfactionStrategy(SatisfactionStrategy satisfactionStrategy) {
        _satisfactionStrategy = satisfactionStrategy;
    }

    /**
     * Sets the vaccination strategy for the employee.
     *
     * @param vaccinationStrategy the vaccination strategy to be set
     */
    protected void setVaccinationStrategy(VaccinationStrategy vaccinationStrategy) {
        _vaccinationStrategy = vaccinationStrategy;
    }

    /**
     * Retrieves the vaccinations administered by the employee.
     *
     * @return a collection of vaccination records
     * @throws UnknownVeterinarianIdException if the employee is not a veterinarian.
     */
    public Collection<Vaccination> listAdministeredVaccinations() throws UnknownVeterinarianIdException {
        return _vaccinationStrategy.getVaccinations();
    }

    /**
     * Vaccinates an animal with the specified vaccine.
     *
     * @param vaccine the vaccine to be administered
     * @param animal the animal to be vaccinated
     * @return the vaccination record
     * @throws UnauthorizedVeterinarianException if the veterinarian is not authorized to vaccinate the animal
     * @throws UnknownVeterinarianIdException if the veterinarian is unknown
     */
    public Vaccination vaccinateAnimal(Vaccine vaccine, Animal animal) throws UnauthorizedVeterinarianException, UnknownVeterinarianIdException {
        return _vaccinationStrategy.vaccinateAnimal(vaccine, animal);
    }

    /**
     * Calculates the satisfaction level of the employee.
     *
     * @return the satisfaction level of the employee as a double
     */
    public double satisfaction() {
        return _satisfactionStrategy.calculateSatisfaction();
    }

    /**
     * Check whether two employees are equal. Two employees are considered equal if they
     * have the same identifier.
     * 
     * @param employee the other employee
     * @return true, if they have the same id; false, otherwise.
     */
    @Override
    public boolean equals(Object employee) {
        return employee instanceof Employee && _id.equals(((Employee) employee).getId());
    }

    /**
     * Returns a string representation of the employee.
     * 
     * @return the employee's ID and name as a string.
     */
    @Override
    public String toString() {
        return _id + "|" + _name;
    }
}
