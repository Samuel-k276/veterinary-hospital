package hva.enums;

/**
 * The {@code Season} enum represents the four seasons of the year: spring, summer, autumn, and winter.
 * Each season is associated with an integer value that can be used to determine the order of the seasons.
 */
public enum Season {
    SPRING(0), SUMMER(1), AUTUMN(2), WINTER(3);

    private final int _value;

    Season(int seasonValue) {
        _value = seasonValue;
    }

    public int value() {
        return _value;
    }
}