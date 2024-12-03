package hva.strategies;

import hva.Habitat;
import hva.tree.Tree;

import java.io.Serial;

public class HabitatWorkStrategy implements WorkStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Habitat _habitat;

    public HabitatWorkStrategy(Habitat habitat) {
        _habitat = habitat;
    }

    @Override
    public double calculateWork() {
        double work = _habitat.getArea() + 3 * _habitat.getPopulation();
        for (Tree tree : _habitat.allTrees()) {
            work += tree.cleaningEffort();
        }
        return work;
    }
}
