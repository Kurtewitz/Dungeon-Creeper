package items.weapons;

public class Dagger extends Weapon {

    //TODO kriegt dmg-bonus von Dex;
    public Dagger(int dmg) {
        super(dmg, WeaponType.Dagger, true);
    }

    @Override
    public String toString() {
        String namePattern = "%1$s, %2$d %3$s damage";

        String name = twoHanded() ? "Daggers" : "Dagger";
        String dmgType = "piercing";

        return String.format(namePattern, name, dmg(), dmgType);

    }
}
