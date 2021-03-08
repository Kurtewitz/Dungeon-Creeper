package player;

public enum PlayerType {
    Warrior(1),
    Mage(2),
    Thief(3);

    private final int playeerTypeNr;

    PlayerType(int playeerTypeNr) {
        this.playeerTypeNr = playeerTypeNr;
    }

    public int getPlayeerTypeNr() {
        return playeerTypeNr;
    }
}
