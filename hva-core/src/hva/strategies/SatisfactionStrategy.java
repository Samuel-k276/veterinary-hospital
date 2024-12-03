package hva.strategies;

import java.io.Serializable;

/**
 * The {@code SatisfactionStrategy} interface represents a strategy for calculating the satisfaction level of an entity.
 * Implementations of this interface should provide a method to calculate the satisfaction level of an entity.
 */
public interface SatisfactionStrategy extends Serializable {
    double calculateSatisfaction();
}
