package items;
import java.util.TreeSet;
import spells.*;

public class Spellbook {
	private final TreeSet<Spell> fire;
	private final TreeSet<Spell> air;
	private final TreeSet<Spell> water;
	private final TreeSet<Spell> earth;
	
	public Spellbook() {
		fire = new TreeSet<>();
		air = new TreeSet<>();
		water = new TreeSet<>();
		earth = new TreeSet<>();
	}
	
	public boolean pickUp(Spell z) {
		switch(z.element()) {
			case Fire: return fire.add(z);
			case Air: return air.add(z);
			case Water: return water.add(z);
			case Earth: return earth.add(z);
		}
		return false;
	}
}
