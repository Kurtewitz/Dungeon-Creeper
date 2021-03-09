package spells;
import player.*;

public class Fireball extends DamageSpell {

		public Fireball(Player p) {
			super(p, "Fireball", 1, SpellType.Fire);
		}
		
		public int dmg() {
			return p.stats().Wis() * 10;
		}
		
		public String toString() {
			return "Shoots a Fireball at the opponent. Deals " + dmg() + " fire damage.";
		}
		
		public int cast(Opponent g) {
			target = g;
			return dmg() /* * target.fireResistance()*/;
		}
}
