package Things;

public class Cell {

	private boolean currStat;
	private boolean newStat;

	public Cell() {
		this(.5);
	}

	public Cell(double chance) {
		currStat = newStat = Math.random() < chance;
	}

	public Cell(boolean stat) {
		currStat = newStat = stat;
	}

	public boolean isAlive() {
		return currStat;
	}

	public void set(boolean stat) {
		newStat = stat;
	}

	public void update() {
		currStat = newStat;
	}
	

}
