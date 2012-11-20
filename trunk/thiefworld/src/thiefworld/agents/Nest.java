package thiefworld.agents;

import sim.engine.SimState;

/**
 * The nest at which the various active agents are spawned and at which food is
 * dropped off.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Nest extends Agent {
	/**
	 * The number of nests created.
	 */
	private static int nestNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988304303551923917L;

	/**
	 * The amount of fruit stored in the nest.
	 */
	private double fruitQuantity;

	/**
	 * The amount of meat stored in the nest.
	 */
	private double meatQuantity;

	/**
	 * Creates a new nest.
	 */
	public Nest() {
		nestNo++;
		this.setName("nest #" + nestNo);
	}

	/**
	 * Decreases the amount of fruit stored in the nest by a specified amount.
	 * 
	 * @param amount
	 *            the amount of fruit taken from the nest.
	 */
	public void decreaseFruitQuantity(double amount) {
		if (amount > 0) {
			this.fruitQuantity -= amount;
		}
	}

	/**
	 * Decreases the amount of meat stored in the nest by a specified amount.
	 * 
	 * @param amount
	 *            the amount of meat taken from the nest.
	 */
	public void decreaseMeatQuantity(double amount) {
		if (amount > 0) {
			this.meatQuantity -= amount;
		}
	}

	/**
	 * Retrieves the amount of food available in the nest.
	 * 
	 * @return the amount of food (fruit & meat) available in the nest.
	 */
	public double getFoodQuantity() {
		return fruitQuantity + meatQuantity;
	}

	/**
	 * Retrieves the amount of fruit available in the nest.
	 * 
	 * @return the amount of fruit available in the nest.
	 */
	public double getFruitQuantity() {
		return fruitQuantity;
	}

	/**
	 * Retrieves the amount of meat available in the nest.
	 * 
	 * @return the amount of meat available in the nest.
	 */
	public double getMeatQuantity() {
		return meatQuantity;
	}

	/**
	 * Increases the amount of fruit available in the nest by a specified
	 * amount.
	 * 
	 * @param amount
	 *            the amount of fruit deposited in the nest.
	 */
	public void increaseFruitQuantity(double amount) {
		if (amount > 0) {
			this.fruitQuantity += amount;
		}
	}

	/**
	 * Increases the amount of meat available in the nest by a specified amount.
	 * 
	 * @param amount
	 *            the amount of meat deposited in the nest.
	 */
	public void increaseMeatQuantity(double amount) {
		if (amount > 0) {
			this.meatQuantity += amount;
		}
	}

	/**
	 * Sets the amount of fruit available in the nest.
	 * 
	 * @param fruitQuantity
	 *            the amount of fruit available in the nest.
	 */
	public void setFruitQuantity(double fruitQuantity) {
		this.fruitQuantity = fruitQuantity;
	}

	/**
	 * Sets the amount of meat available in the nest.
	 * 
	 * @param meatQuantity
	 *            the amount of meat available in the nest.
	 */
	public void setMeatQuantity(double meatQuantity) {
		this.meatQuantity = meatQuantity;
	}

	/**
	 * The nest is silent.
	 */
	@Override
	public void step(SimState state) {

	}

}
