package thiefworld.agents;

import sim.engine.SimState;

public class Nest extends Agent {
	private static int nestNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988304303551923917L;

	/**
	 * @uml.property name="fruitQuantity"
	 */
	private double fruitQuantity;

	/**
	 * @uml.property name="meatQuantity"
	 */
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

	/**
	 * @return
	 * @uml.property name="fruitQuantity"
	 */
	public double getFruitQuantity() {
		return fruitQuantity;
	}

	/**
	 * @return
	 * @uml.property name="meatQuantity"
	 */
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

	/**
	 * @param fruitQuantity
	 * @uml.property name="fruitQuantity"
	 */
	public void setFruitQuantity(double fruitQuantity) {
		this.fruitQuantity = fruitQuantity;
	}

	/**
	 * @param meatQuantity
	 * @uml.property name="meatQuantity"
	 */
	public void setMeatQuantity(double meatQuantity) {
		this.meatQuantity = meatQuantity;
	}

	@Override
	public void step(SimState state) {

	}

}
