package thiefworld.agents.misc;


/**
 * Contains the various food or items that the agent carries.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
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

	/**
	 * Creates a new inventory with a specified limit of carried food.
	 * 
	 * @param maxAllowedFood
	 *            the maximum allowed amount of food.
	 */
	public InventoryData(double maxAllowedFood) {
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

	/**
	 * Retrieves the amount of carried fruit.
	 * 
	 * @return the amount of carried fruit.
	 */
	public double getCarriedFruit() {
		return carriedFruit;
	}

	/**
	 * Retrieves the amount of carried meat.
	 * 
	 * @return the amount of carried meat.
	 */
	public double getCarriedMeat() {
		return carriedMeat;
	}

	/**
	 * Retrieves the maximum allowed amount of carried food.
	 * 
	 * @return the maximum allowed amount of carried food.
	 */
	public double getMaxAllowedFood() {
		return maxAllowedFood;
	}

	/**
	 * Sets the amount of carried fruit.
	 * 
	 * @param carriedFruit
	 *            the amount of carried fruit.
	 */
	public void setCarriedFruit(double carriedFruit) {
		this.carriedFruit = carriedFruit;
	}

	/**
	 * Sets the amount of carried meat.
	 * 
	 * @param carriedMeat
	 *            the amount of carried meat.
	 */
	public void setCarriedMeat(double carriedMeat) {
		this.carriedMeat = carriedMeat;
	}

	/**
	 * Sets the maximum allowed amount of carried food.
	 * 
	 * @param maxAllowedFood
	 *            the maximum allowed amount of carried food.
	 */
	public void setMaxAllowedFood(double maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}
}