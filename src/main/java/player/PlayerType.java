package player;

import util.RNG;

public enum PlayerType {
    Warrior(1),
    Mage(2),
    Thief(3);

    private final int playerTypeNr;

    PlayerType(int playerTypeNr) {
        this.playerTypeNr = playerTypeNr;
    }

    public int getPlayerTypeNr() {
        return playerTypeNr;
    }

    public static PlayerType getRandom() {
        int random = RNG.randomInt(PlayerType.values().length);
        return PlayerType.values()[random];
    }
}
