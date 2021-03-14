package items.weapons;

public class Sword extends Weapon{
	
	public Sword(int dmg, boolean zh) {
		super(dmg, WeaponType.Sword, zh);
	}

	@Override
	public String toString() {
		String namePattern = "%1$s, %2$d %3$s damage";

		String name = twoHanded() ? "Zweihänder" : "Sword";
		String dmgType = "slashing";

		return String.format(namePattern, name, damage(), dmgType);
	}
}
