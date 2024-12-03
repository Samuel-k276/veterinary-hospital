package hva.strategies;

import hva.Species;
import hva.employee.Veterinarian;

import java.io.Serial;

public class VeterinarianWorkStrategy implements WorkStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Veterinarian _veterinarian;

    public VeterinarianWorkStrategy(Veterinarian veterinarian) {
        _veterinarian = veterinarian;
    }

    @Override
    public double calculateWork() {
        double work = 0;
        for (Species species : _veterinarian.allSpecies()) {
            work += (double) species.getPopulation()/species.getNumberOfVeterinarians();
        }
        return work;
    }

}
