package items;

public abstract class Item implements Comparable<Item>{

	private int value;
	
	public Item(int value) {
		this.value = value;
	}
	
	public int compareTo(Item i) {
		return Integer.compare(value, i.value());
	}

	public int value() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

}
