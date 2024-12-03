package hva.enums;

/**
 * The {@code HabitatInfluence} enum represents the possible influences that a habitat can have on an animal's health.
 * Each influence is associated with an integer value that can be used to determine the impact of the habitat on the animal.
 */
public enum HabitatInfluence {
    POSITIVE(20),
    NEUTRAL(0),
    NEGATIVE(-20);

    private final int _value;

    HabitatInfluence(int value) {
        _value = value;
    }
    
    public int getValue() {
        return _value;
    }
}
