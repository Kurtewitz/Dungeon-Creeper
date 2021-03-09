package player;

import java.util.Vector;

import skills.*;

public class Skills {

	boolean active;
	
	@SuppressWarnings("unchecked")
	Vector<Skill>[] skillTree =  new Vector[] {new Vector<Skill>(), new Vector<Skill>(), new Vector<Skill>()};
	
	public Skills(Player p) {
		skillTree[0].add(new PhoenixBlade(p));
		skillTree[0].add(new PhoenixBlade(p));
		skillTree[0].add(new PhoenixBlade(p));
		skillTree[0].add(new PhoenixBlade(p));
		skillTree[0].add(new PhoenixBlade(p));
	}
	
	public Skill getSkill(int treeNr, int skillNr) {
		return skillTree[treeNr].get(skillNr);
	}
}
