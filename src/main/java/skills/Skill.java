package skills;

import player.Player;

public abstract class Skill {

	int nr;
	int lvl;
	Player user;
	//boolean active;
	
	public Skill(Player user, int nr) {
		this.user = user;
		this.nr = nr;
		lvl = 1;
	}
	
	public abstract void use(Player p);
	
	public abstract int dmg();
	
	public abstract String toString();
	
	public abstract String description();
}
