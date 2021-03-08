package spells;

public enum SpellType {

    Fire(1),
    Air(2),
    Water(3),
    Earth(4);

    private final int spellTypeNr;

    SpellType(int spellTypeNr) {
        this.spellTypeNr = spellTypeNr;
    }

    public int getSpellTypeNr() {
        return spellTypeNr;
    }

}
