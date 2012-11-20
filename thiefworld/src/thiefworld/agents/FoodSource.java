package thiefworld.agents;

import thiefworld.util.Utilities;

/**
 * An abstraction of a food source.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public abstract class FoodSource extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1808736756735801593L;

	/**
	 * The amount of food available.
	 */
	protected double foodQuantity = 0.0;

	/**
	 * Decreases the amount of food available for the food source.
	 * 
	 * @param amount
	 *            the amount of food taken from the food source.
	 */
	public void decreaseFoodQuantity(double amount) {
		if (amount >= 0) {
			this.foodQuantity -= amount;
		}

		if (this.foodQuantity <= Utilities.theta) {
			this.foodQuantity = 0.0;
		}
	}

	/**
	 * Retrieves the amount of food available in the food source.
	 * 
	 * @return the amount of food available in the food source.
	 */
	public double getFoodQuantiy() {
		return foodQuantity;
	}

	/**
	 * Increases the food in the food source by a specified amount.
	 * 
	 * @param amount
	 *            the amount of food added to the food source.
	 */
	public void increaseFoodQuantity(double amount) {
		if (amount >= 0)
			this.foodQuantity += amount;
	}

	/**
	 * Checks if the food source is currently active.
	 * 
	 * @return true if the food source is not empty, false otherwise.
	 */
	public boolean isActive() {
		return this.foodQuantity != 0.0;
	}

	/**
	 * Sets the amount of food available in the food source.
	 * 
	 * @param foodQuantity
	 *            the amount of food available in the food source.
	 */
	public void setFoodQuantity(double foodQuantity) {
		this.foodQuantity = foodQuantity;
	}
}
