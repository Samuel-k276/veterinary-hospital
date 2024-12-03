package hva.strategies;

import hva.employee.Zookeeper;

import java.io.Serial;

public class ZookeeperSatisfactionStrategy implements SatisfactionStrategy {
     /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Zookeeper _zookeeper;

    public ZookeeperSatisfactionStrategy(Zookeeper zookeeper) {
        _zookeeper = zookeeper;
    }

    @Override
    public double calculateSatisfaction() {
        ZookeeperWorkStrategy workStrategy = new ZookeeperWorkStrategy(_zookeeper);
        return 300 - workStrategy.calculateWork();
    }
}
