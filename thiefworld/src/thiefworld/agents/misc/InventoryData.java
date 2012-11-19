package thiefworld.agents.misc;

public class InventoryData {
	/**
	 * The fruit quantity which the agent currently carries.
	 */
	private double carriedFruit;
	/**
	 * The meat quantity which the agent currently carries.
	 */
	private double carriedMeat;
	/**
	 * The maximum allowed food quantity that can be carried at once.
	 */
	private double maxAllowedFood;

	public InventoryData(double maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}

	public double getCarriedFruit() {
		return carriedFruit;
	}

	public void setCarriedFruit(double carriedFruit) {
		this.carriedFruit = carriedFruit;
	}

	public double getCarriedMeat() {
		return carriedMeat;
	}

	public void setCarriedMeat(double carriedMeat) {
		this.carriedMeat = carriedMeat;
	}

	public double getMaxAllowedFood() {
		return maxAllowedFood;
	}

	public void setMaxAllowedFood(double maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}
	
	/**
	 * Retrieves the amount of food which the agent currently carries.
	 * 
	 * @return the amount of food which the agent currently carries.
	 */
	public double getCarriedFood() {
		return getCarriedFruit() + getCarriedMeat();
	}
}