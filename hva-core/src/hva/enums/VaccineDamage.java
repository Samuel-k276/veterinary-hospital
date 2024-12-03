package hva.enums;

/**
 * The {@code VaccineDamage} enum represents the possible levels of damage that can be caused by a vaccine.
 * Each level of damage is associated with a string value that describes the severity of the damage.
 */
public enum VaccineDamage {
    NONE("NORMAL"),
    LITTLE("CONFUSÃO"),
    MEDIUM("ACIDENTE"),
    HIGH("ERRO");

    private final String _result;

    VaccineDamage(String result) {
        _result = result;
    }
    
    public String getResult() {
        return _result;
    }
}
