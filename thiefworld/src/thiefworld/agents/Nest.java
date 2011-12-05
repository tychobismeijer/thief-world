package thiefworld.agents;

import sim.engine.SimState;

public class Nest extends Agent {
	private static int nestNo = 0;

	public Nest() {
		nestNo++;
		this.setName("nest #" + nestNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988304303551923917L;

	private double fruitQuantity;

	public double getFruitQuantity() {
		return fruitQuantity;
	}

	public void setFruitQuantity(double fruitQuantity) {
		this.fruitQuantity = fruitQuantity;
	}

	public void increaseFruitQuantity(double amount) {
		if (amount > 0) {
			this.fruitQuantity += amount;
		}
	}

	public void decreaseFruitQuantity(double amount) {
		if (amount > 0) {
			this.fruitQuantity -= amount;
		}
	}

	private double meatQuantity;

	public double getMeatQuantity() {
		return meatQuantity;
	}

	public void setMeatQuantity(double meatQuantity) {
		this.meatQuantity = meatQuantity;
	}

	public void increaseMeatQuantity(double amount) {
		if (amount > 0) {
			this.meatQuantity += amount;
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

	@Override
	public void step(SimState state) {
		
	}

}
