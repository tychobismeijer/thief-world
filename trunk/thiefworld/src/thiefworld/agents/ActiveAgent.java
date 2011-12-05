package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public abstract class ActiveAgent extends Agent {
	public ActiveAgent() {
		// set task switching threshold
		switchThreshold = Utilities.nextDouble();
		health = 1.0;
		maxAllowedFood = ActiveAgent.getDefaultMaxCarriedFood();
	}

	private static final long serialVersionUID = 5597485865861516823L;

	/**
	 * Determines how random are the movements of the agent.
	 */
	private static double randomMovementFactor = 0.1;

	/**
	 * Retrieves the randomness factor for the agent movement.
	 * 
	 * @return the randomness of the agent movement.
	 */
	public static double getRandomMovementFactor() {
		return ActiveAgent.randomMovementFactor;
	}

	/**
	 * Sets the randomness factor for the agent movement.
	 * 
	 * @param randomMovementFactor
	 *            the randomness of the agent movement.
	 */
	public static void setRandomMovementFactor(double randomMovementFactor) {
		if (randomMovementFactor >= 0)
			ActiveAgent.randomMovementFactor = randomMovementFactor;
	}

	private static double defaultMaxCarriedFood = 1.0;

	public static double getDefaultMaxCarriedFood() {
		return ActiveAgent.defaultMaxCarriedFood;
	}

	public static void setDefaultMaxCarriedFood(double defaultMaxCarriedFood) {
		if (defaultMaxCarriedFood >= 0) {
			ActiveAgent.defaultMaxCarriedFood = defaultMaxCarriedFood;
		}
	}

	private static double actionRange = 0.1;

	public static double getActionRange() {
		return actionRange;
	}

	public static void setActionRange(double actionRange) {
		ActiveAgent.actionRange = actionRange;
	}

	private static double agentRange = 10.0;

	public static double getAgentRange() {
		return ActiveAgent.agentRange;
	}

	public static void setAgentRange(double agentRange) {
		ActiveAgent.agentRange = agentRange;
	}

	private double gatheringSuccess;

	public double getGatheringSuccess() {
		return gatheringSuccess;
	}

	public void setGatheringSuccess(double gatheringSuccess) {
		this.gatheringSuccess = gatheringSuccess;
	}

	private double huntingSuccess;

	public double getHuntingSuccess() {
		return huntingSuccess;
	}

	public void setHuntingSuccess(double huntingSuccess) {
		this.huntingSuccess = huntingSuccess;
	}

	private double personalSuccess;

	public double getPersonalSuccess() {
		return personalSuccess;
	}

	public void setPersonalSuccess(double personalSuccess) {
		this.personalSuccess = personalSuccess;
	}

	private double gatheringSkill;

	public double getGatheringSkill() {
		return gatheringSkill;
	}

	public void setGatheringSkill(double gatheringSkill) {
		this.gatheringSkill = gatheringSkill;
	}

	private double huntingSkill;

	public double getHuntingSkill() {
		return huntingSkill;
	}

	public void setHuntingSkill(double huntingSkill) {
		this.huntingSkill = huntingSkill;
	}

	private double stealingSkill;

	public double getStealingSkill() {
		return stealingSkill;
	}

	public void setStealingSkill(double stealingSkill) {
		this.stealingSkill = stealingSkill;
	}

	private double switchThreshold;

	public double getSwitchThreshold() {
		return switchThreshold;
	}

	public void setSwitchThreshold(double switchThreshold) {
		this.switchThreshold = switchThreshold;
	}

	private double carriedFood;

	public double getCarriedFood() {
		return carriedFood;
	}

	public void setCarriedFood(double carriedFood) {
		this.carriedFood = carriedFood;
	}

	public void increaseCarriedFood(double amount) {
		this.carriedFood += amount;

		if (this.carriedFood > maxAllowedFood)
			this.carriedFood = maxAllowedFood;
	}

	public void decreaseCarriedFood(double amount) {
		this.carriedFood -= amount;

		if (this.carriedFood < 0)
			this.carriedFood = 0;
	}

	private double maxAllowedFood = 2.0;

	public double getMaxAllowedFood() {
		return maxAllowedFood;
	}

	public void setMaxAllowedFood(double maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}

	private double health;

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * Checks to see if the agent has gathered the maximum allowed food and
	 * should thus is on its way back to the nest to drop off the carried food.
	 * 
	 * @return true if the agent cannot carry any more food, false otherwise.
	 */
	public boolean isReturningFood() {
		return this.getCarriedFood() == this.getMaxAllowedFood();
	}

	/**
	 * Wonders around in search of a food source guided by the pheromone trail.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected void wonderAround(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	/**
	 * Makes a return trip to the nest to drop the food.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected void returnFood(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tries to find and extract food from food sources within the agent's
	 * range.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param foodType
	 *            the type of food which the agent searches for.
	 */
	protected void goAfterFood(ThiefWorld world, Class<?> foodType) {
		FoodSource closestFoodSource = getClosestFoodSource(world, foodType);

		if (closestFoodSource != null) {
			examineFoodSource(world, closestFoodSource);
		} else {
			wonderAround(world);
		}
	}

	/**
	 * Check to see if there is a close by food source and if there are
	 * multiple, it will choose the closest to the current agent's position.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param foodSourceType
	 *            the type of food source which you are looking for.
	 * @return the closest food source or null if there is none in the agent's
	 *         range.
	 */
	protected FoodSource getClosestFoodSource(ThiefWorld world,
			Class<?> foodSourceType) {
		Double2D myPosition = world.map.getObjectLocation(this);

		// follow the pheromone trail to a food source
		Bag agentsInRange = world.map.getObjectsWithinDistance(myPosition,
				ActiveAgent.getAgentRange());

		double closestFoodSourceDistance = Double.MAX_VALUE;
		FoodSource closestFoodSource = null;

		// go through all food sources of the required type
		for (int i = 0; i < agentsInRange.size(); i++) {
			if (agentsInRange.get(i).getClass() == foodSourceType) {
				FoodSource foodSource = (FoodSource) agentsInRange.get(i);

				// check to see if there is any food left in them
				if (foodSource.isActive()) {
					Double2D foodSourcePosition = world.map
							.getObjectLocation(foodSource);

					// check if it's the closest food source available
					if (myPosition.distance(foodSourcePosition) < closestFoodSourceDistance) {
						closestFoodSourceDistance = myPosition
								.distance(foodSourcePosition);
						closestFoodSource = foodSource;
					}
				}
			}
		}

		return closestFoodSource;
	}

	/**
	 * Moves in the direction of a food source.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param foodSource
	 *            the food source in which direction the agent must go.
	 */
	protected void moveTowardsFoodSource(ThiefWorld world, FoodSource foodSource) {
		Double2D myPosition = world.map.getObjectLocation(this);
		Double2D foodSourcePosition = world.map.getObjectLocation(foodSource);

		MutableDouble2D movementTowardsFoodSource = new MutableDouble2D();

		movementTowardsFoodSource.addIn(
				(foodSourcePosition.getX() - myPosition.getX()) * 0.1,
				(foodSourcePosition.getY() - myPosition.getY()) * 0.1);

		// add movement randomness
		movementTowardsFoodSource.addIn(new Double2D(
				(Utilities.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor(), (Utilities
						.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor()));

		// add current position
		movementTowardsFoodSource.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(
				movementTowardsFoodSource));
	}

	/**
	 * Examines a food source and gathers the maximum allowed amount.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param foodSource
	 *            the food source which is to be examined.
	 */
	protected void examineFoodSource(ThiefWorld world, FoodSource foodSource) {
		Double2D myPosition = world.map.getObjectLocation(this);
		Double2D foodSourcePosition = world.map.getObjectLocation(foodSource);

		double foodSourceDistance = myPosition.distance(foodSourcePosition);

		if (foodSourceDistance <= ActiveAgent.getActionRange()) {
			// agent can extract food
			extractFood(foodSource);
		} else {
			moveTowardsFoodSource(world, foodSource);
		}
	}

	/**
	 * Extracts the maximum amount of food from a specified
	 * {@link thiefworld.agents.FoodSource FoodSource}.
	 * 
	 * @param foodSource
	 *            the food source to be processed.
	 */
	protected void extractFood(FoodSource foodSource) {
		// check how much food the agent can gather
		double availableFood = foodSource.getFoodQuantiy();
		double maximumFoodToExtract = this.getMaxAllowedFood()
				- this.getCarriedFood();

		double foodToExtract = 0.0;

		if (maximumFoodToExtract > 0)
			foodToExtract = Math.min(availableFood, maximumFoodToExtract);

		if (foodToExtract > 0) {
			// extract food
			foodSource.decreaseFoodQuantity(foodToExtract);
			this.increaseCarriedFood(foodToExtract);

			// log event
			Logger log = Logger.getLogger(this.getName());
			log.log(Level.INFO, this.getName() + " picked up " + foodToExtract
					+ " food from " + foodSource.getName());
		}
	}
}
