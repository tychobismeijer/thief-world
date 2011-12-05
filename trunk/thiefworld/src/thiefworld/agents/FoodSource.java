package thiefworld.agents;

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
	
	public boolean isActive(){
		return this.foodQuantity != 0.0;
	}
}
