package items.weapons;

public enum WeaponType {

	Mace(1),
	Sword(2),
	Dagger(3);
	
	
	private final int typeNr;
	
	WeaponType(int typeNr) {
		this.typeNr = typeNr;
	}
	
	public int getTypeNr() {
		return typeNr;
	}
}
