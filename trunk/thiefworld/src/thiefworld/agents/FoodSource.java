package thiefworld.agents;

import thiefworld.util.Utilities;

public abstract class FoodSource extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1808736756735801593L;

	protected double foodQuantity = 1.0;

	public double getFoodQuantiy() {
		return foodQuantity;
	}

	public void setFoodQuantity(double foodQuantity) {
		this.foodQuantity = foodQuantity;
	}

	public void increaseFoodQuantity(double amount) {
		if (amount >= 0)
			this.foodQuantity += amount;
	}

	public void decreaseFoodQuantity(double amount) {
		if (amount >= 0) {
			this.foodQuantity -= amount;
		}

		if (this.foodQuantity <= Utilities.theta) {
			this.foodQuantity = 0.0;
		}
	}

	public boolean isActive() {
		return this.foodQuantity != 0.0;
	}
}
