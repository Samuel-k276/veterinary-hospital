package hva.strategies;

import hva.Animal;
import hva.Habitat;

import java.io.Serial;

public class AnimalSatisfactionStrategy implements SatisfactionStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Animal _animal;

    public AnimalSatisfactionStrategy(Animal animal) {
        _animal = animal;
    }

    @Override
    public double calculateSatisfaction() {
        Habitat habitat = _animal.getHabitat();
        int sameSpeciesBonus = 3 * habitat.sameSpecies(_animal);
        int differentSpeciesPenalty = 2 * habitat.differentSpecies(_animal);
        double areaPerAnimal = (double) habitat.getArea() / habitat.getPopulation();
        int suitabilityScore = habitat.influenceOnAnimal(_animal).getValue();

        return 20 + sameSpeciesBonus - differentSpeciesPenalty + areaPerAnimal + suitabilityScore;
    }

}
