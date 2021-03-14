package items.weapons;

import player.PlayerType;
import util.RNG;

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

	public static WeaponType getRandomForClass(PlayerType playerType) {
		WeaponType[] relevantTypes;
		switch(playerType) {
			case Warrior: {
				relevantTypes = new WeaponType[]{Mace, Sword};
				break;
			}
			case Thief: {
				relevantTypes = new WeaponType[]{Sword, Dagger};
				break;
			}
			default: {
				relevantTypes = WeaponType.values();
				break;
			}
		}
		int randomIndex = RNG.randomInt(relevantTypes.length);
		return relevantTypes[randomIndex];
	}

}
