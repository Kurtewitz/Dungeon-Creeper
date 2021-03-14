package items.weapons;

public class Mace extends Weapon {

    public Mace(int dmg, boolean zh) {
        super(dmg, WeaponType.Mace, zh);
    }

    @Override
    public String toString() {
        String namePattern = "%1$s, %2$d %3$s damage";

        String name = twoHanded() ? "War hammer" : "Mace";
        String dmgType = "blunt";

        return String.format(namePattern, name, damage(), dmgType);

    }
}
