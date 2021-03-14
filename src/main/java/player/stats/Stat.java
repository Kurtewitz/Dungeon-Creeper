package player.stats;

public enum Stat {

    Health("Health"),
    Strength("Strength"),
    Intelligence("Intelligence"),
    Speed("Speed"),
    Wisdom("Wisdom"),
    Dexterity("Dexterity");

    private final String statName;

    Stat(String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }
}
