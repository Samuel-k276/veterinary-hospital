package hva.strategies;

import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import hva.Animal;
import hva.Vaccine;
import hva.employee.Employee;
import hva.Vaccination;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code VaccinationStrategy} interface represents a strategy for administering vaccinations to animals.
 * Implementations of this interface should provide methods to get the vaccinations administered by the veterinarian
 * and to vaccinate an animal.
 */
public interface VaccinationStrategy extends Serializable {
    Collection<Vaccination> getVaccinations() throws UnknownVeterinarianIdException;
    Vaccination vaccinateAnimal(Vaccine vaccine, Animal animal) throws UnknownVeterinarianIdException, UnauthorizedVeterinarianException;
}
