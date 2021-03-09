package spells;
import player.*;

public abstract class Spell implements Comparable<Spell>{
	Player p;
	String name;
	int nr;
	SpellType element;
	
	public Spell(Player p, String name, int nr, SpellType element) {
		this.p = p;
		this.name = name;
		this.nr = nr;
		this.element = element;
	}
	
	public Spell(int nr) {
		this.nr = nr;
	}
	
	public int compareTo(Spell z) {
		return z.nr - nr;
	}
	
	public String name(){
		return name;
	}
	public SpellType element() {
		return element;
	}
	public int nr() {
		return nr;
	}
}
