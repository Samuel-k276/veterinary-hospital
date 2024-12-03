package hva.strategies;

import hva.Animal;
import hva.Habitat;
import hva.employee.Veterinarian;

import java.io.Serial;

public class VeterinarianSatisfactionStrategy implements SatisfactionStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Veterinarian _veterinarian;

    public VeterinarianSatisfactionStrategy(Veterinarian veterinarian) {
        _veterinarian = veterinarian;
    }

    @Override
    public double calculateSatisfaction() {
        VeterinarianWorkStrategy workStrategy = new VeterinarianWorkStrategy(_veterinarian);
        return 20 - workStrategy.calculateWork();
    }
}
