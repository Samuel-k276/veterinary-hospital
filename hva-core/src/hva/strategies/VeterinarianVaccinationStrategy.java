package hva.strategies;

import hva.Animal;
import hva.Vaccine;
import hva.employee.Employee;
import hva.employee.Veterinarian;
import hva.Vaccination;
import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;

import java.io.Serial;
import java.util.Collection;

public class VeterinarianVaccinationStrategy implements VaccinationStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Veterinarian _veterinarian;

    public VeterinarianVaccinationStrategy(Veterinarian veterinarian) {
        _veterinarian = veterinarian;
    }
    
    @Override
    public Collection<Vaccination> getVaccinations() throws UnknownVeterinarianIdException {
        return _veterinarian.getAllVaccinations();
    }

    @Override
    public Vaccination vaccinateAnimal(Vaccine vaccine, Animal animal) throws UnknownVeterinarianIdException, UnauthorizedVeterinarianException {
        return _veterinarian.administerVaccine(vaccine, animal);
    }
}
