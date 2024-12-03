package hva.strategies;

import hva.Habitat;
import hva.employee.Zookeeper;

import java.io.Serial;

public class ZookeeperWorkStrategy implements WorkStrategy {
    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Zookeeper _zookeeper;

    public ZookeeperWorkStrategy(Zookeeper zookeeper) {
        _zookeeper = zookeeper;
    }

    @Override
    public double calculateWork() {
        double work = 0;
        for (Habitat habitat : _zookeeper.allHabitats()) {
            work += habitat.workInHabitat()/habitat.getNumberOfZookeepers();
        }
        return work;
    }
}
