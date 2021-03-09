package spells;
import player.*;

public abstract class DamageSpell extends Spell {
	Opponent target;
	
	public DamageSpell(Player p, String s, int nr, SpellType ele) {
		super(p, s, nr, ele);
		if(s == "Feuerball") this.nr = 1;
		if(s == "Blitz") this.nr = 2;
		if(s == "Eisspeer") this.nr = 3;
		if(s == "Steinschleuder") this.nr = 4;
		if(s == "Hoellenfeuer") this.nr = 5;
	}
	
	public DamageSpell(int nr) {
		super(nr);
		if(nr == 1) name = "Feuerball";
		if(nr == 2) name = "Blitz";
		if(nr == 3) name = "Eisspeer";
		if(nr == 4) name = "Steinschleuder";
		if(nr == 5) name = "Hoellenfeuer";
	}
}
