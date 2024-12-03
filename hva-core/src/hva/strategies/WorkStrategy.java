package hva.strategies;

import java.io.Serializable;

/**
 * The {@code WorkStrategy} interface represents a strategy for calculating the work level of an entity.
 * Implementations of this interface should provide a method to calculate the work level of an entity.
 */
public interface WorkStrategy extends Serializable {
    double calculateWork();
}
