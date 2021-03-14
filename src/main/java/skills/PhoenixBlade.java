package skills;

import player.Player;

public class PhoenixBlade extends Skill{
	
	public PhoenixBlade(Player p) {
		super(p, 1);
	}

	@Override
	public void use(Player target) {
		target.loseHP(dmg());
		System.out.println(target.getCurrentHealth()  + " / " + target.getMaxHealth());
	}
	
	public int dmg() {
		return user.dmg() + user.inventory().equippedWeapon().damage();
	}
	
	public String toString() {
		return "Phoenix Blade";
	}
	
	public String description() {
		return "You invoke the power of the Phoenix doubling your weapon dmg for the next strike. Deals " + user.inventory().equippedWeapon().damage() + " additional damage.";
	}
	
	
}
