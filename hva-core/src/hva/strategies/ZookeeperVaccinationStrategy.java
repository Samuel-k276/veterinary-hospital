package hva.strategies;

import hva.exceptions.UnauthorizedVeterinarianException;
import hva.exceptions.unknown.UnknownVeterinarianIdException;
import hva.Animal;
import hva.Vaccine;
import hva.employee.Employee;
import hva.employee.Zookeeper;
import hva.Vaccination;

import java.io.Serial;
import java.util.Collection;

public class ZookeeperVaccinationStrategy implements VaccinationStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;
    
    private Zookeeper _zookeeper;

    public ZookeeperVaccinationStrategy(Zookeeper zookeeper) {
        _zookeeper = zookeeper;
    }

    @Override
    public Collection<Vaccination> getVaccinations() throws UnknownVeterinarianIdException {
        throw new UnknownVeterinarianIdException(_zookeeper.getId());
    }
    
    @Override
    public Vaccination vaccinateAnimal(Vaccine vaccine, Animal animal) throws UnknownVeterinarianIdException, UnauthorizedVeterinarianException {
        throw new UnknownVeterinarianIdException(_zookeeper.getId());
    }
}
