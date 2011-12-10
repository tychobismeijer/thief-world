package thiefworld.agents;

import sim.engine.SimState;

public class Nest extends Agent {
	private static int nestNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988304303551923917L;

	private double fruitQuantity;

	private double meatQuantity;

	public Nest() {
		nestNo++;
		this.setName("nest #" + nestNo);
	}

	public void decreaseFruitQuantity(double amount) {
		if (amount > 0) {
			this.fruitQuantity -= amount;
		}
	}

	public void decreaseMeatQuantity(double amount) {
		if (amount > 0) {
			this.meatQuantity -= amount;
		}
	}

	public double getFoodQuantity() {
		return fruitQuantity + meatQuantity;
	}

	public double getFruitQuantity() {
		return fruitQuantity;
	}

	public double getMeatQuantity() {
		return meatQuantity;
	}

	public void increaseFruitQuantity(double amount) {
		if (amount > 0) {
			this.fruitQuantity += amount;
		}
	}

	public void increaseMeatQuantity(double amount) {
		if (amount > 0) {
			this.meatQuantity += amount;
		}
	}

	public void setFruitQuantity(double fruitQuantity) {
		this.fruitQuantity = fruitQuantity;
	}

	public void setMeatQuantity(double meatQuantity) {
		this.meatQuantity = meatQuantity;
	}

	@Override
	public void step(SimState state) {
		
	}

}
