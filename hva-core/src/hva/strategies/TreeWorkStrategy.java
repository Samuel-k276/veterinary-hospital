package hva.strategies;

import hva.tree.Tree;

import java.io.Serial;

public class TreeWorkStrategy implements WorkStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Tree _tree;

    public TreeWorkStrategy(Tree tree) {
        _tree = tree;
    }

    @Override
    public double calculateWork() {
        int cleaningDifficulty = _tree.getCleaningDificulty();
        int seasonalEffort = _tree.getSeasonalEffort();
        double ageFactor = Math.log(_tree.getAge() + 1);
        double work = cleaningDifficulty * seasonalEffort * ageFactor;
        return work;
    }    
}
