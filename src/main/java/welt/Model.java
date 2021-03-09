package welt;

import items.weapons.Sword;
import player.Opponent;
import player.Player;
import player.PlayerType;

public class Model {

    Player p;
    Opponent o;
    FightJudge fj;

    public Model() {
        p = new Player(0, 0, PlayerType.Warrior, 1, new Sword(1, false));
        fj = new FightJudge(p);
    }

    public FightJudge fightJudge() {
        return fj;
    }

    public Player player() {
        return p;
    }

    public Opponent opponent() {
        return o;
    }

}
