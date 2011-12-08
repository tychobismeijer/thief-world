package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public abstract class ActiveAgent extends Agent {

	Observer personalObserver;

	public ActiveAgent() {
		// set task switching threshold
		switchThreshold = Utilities.nextDouble();
		health = 1.0;
		maxAllowedFood = ActiveAgent.getDefaultMaxCarriedFood();
		personalObserver = new Observer(this);
	}

	private static final long serialVersionUID = 5597485865861516823L;

	/**
	 * The maximum allowed movement step size for an agent.
	 */
	private static double maxStepSize = 1.0;

	/**
	 * Retrieves the maximum allowed movement step size for an agent.
	 * 
	 * @return the maximum allowed movement step size for an agent.
	 */
	public static double getMaxStepSize() {
		return ActiveAgent.maxStepSize;
	}

	/**
	 * Sets the maximum allowed movement step size for an agent.
	 * 
	 * @param maxStepSize
	 *            the maximum allowed movement step size for an agent.
	 */
	public static void setMaxStepSize(double maxStepSize) {
		if (maxStepSize >= 0) {
			ActiveAgent.maxStepSize = maxStepSize;
		}
	}

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

	private static double actionRange = 0.5;

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
		MutableDouble2D wonderingMovement = new MutableDouble2D();

		// add movement randomness
		wonderingMovement.addIn(new Double2D(
				(Utilities.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor(), (Utilities
						.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor()));

		// normalize movement
		if (wonderingMovement.length() > ActiveAgent.getMaxStepSize())
			wonderingMovement.resize(0.0);
		else if (wonderingMovement.length() > 0)
			wonderingMovement.resize(ActiveAgent.getMaxStepSize()
					- wonderingMovement.length());

		// add current position
		Double2D myPosition = world.map.getObjectLocation(this);
		wonderingMovement.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(wonderingMovement));
	}

	/**
	 * Makes a return trip to the nest to drop the food.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected void returnFood(ThiefWorld world, PheromoneType pheromonesType) {
		// search for a nearby nest
		Nest closestNest = personalObserver.searchForNestWithinRange(world);

		if (closestNest != null) {
			// is the nest within drop-off distance?
			Double2D myPosition = world.map.getObjectLocation(this);
			Double2D nestPosition = world.map.getObjectLocation(closestNest);

			if (myPosition.distance(nestPosition) <= ActiveAgent
					.getActionRange()) {
				// drop off food
				dropOffFood(world, closestNest);
			} else {
				// go towards the nest
				MutableDouble2D movementTowardsNest = new MutableDouble2D();

				// add movement towards the food source
				movementTowardsNest.addIn(
						(nestPosition.getX() - myPosition.getX()) * 0.1,
						(nestPosition.getY() - myPosition.getY()) * 0.1);

				// add movement randomness
				movementTowardsNest.addIn(new Double2D(
						(Utilities.nextDouble() * 1.0 - 0.5)
								* ActiveAgent.getRandomMovementFactor(),
						(Utilities.nextDouble() * 1.0 - 0.5)
								* ActiveAgent.getRandomMovementFactor()));

				if (movementTowardsNest.length() > ActiveAgent.getMaxStepSize())
					movementTowardsNest.resize(0.0);
				else if (movementTowardsNest.length() > 0)
					movementTowardsNest.resize(ActiveAgent.getMaxStepSize()
							- movementTowardsNest.length());
				// add current position
				movementTowardsNest.addIn(myPosition);

				// modify the agent's position
				world.map.setObjectLocation(this, new Double2D(
						movementTowardsNest));
			}
		} else {
			// search for nearby returning pheromones
			Bag nearbyPheromones = personalObserver.getPheromonesWithinRange(
					world, pheromonesType);

			Bag goingPheromones = new Bag();
			Bag returningPheromones = new Bag();

			if (nearbyPheromones != null && nearbyPheromones.size() > 0) {
				for (int i = 0; i < nearbyPheromones.size(); i++) {
					Pheromone pheromone = (Pheromone) nearbyPheromones.get(i);

					if (pheromone.isReturning())
						returningPheromones.add(pheromone);
					else
						goingPheromones.add(pheromone);
				}

				// TODO
				// what kind of movement is required here? follow which types of
				// pheromones (in what proportion?)

				followPheromoneTrail(world, nearbyPheromones);

				// if (returningPheromones.size() > 0) {
				// // if found, take the summed vector
				// followPheromoneTrail(world, returningPheromones);
				// } else {
				// // take going pheromones in the reverse order
				// followPheromoneTrail(world, goingPheromones);
				// }
			} else {
				// if no pheromones are available, wonder around
				wonderAround(world);
			}
		}
	}

	/**
	 * Drops off all the food at a specified nest.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param nest
	 *            the nest at which the food is dropped.
	 */
	protected void dropOffFood(ThiefWorld world, Nest nest) {
		Logger log = Logger.getLogger(getName());

		if (this.getClass() == Hunter.class) {
			// drop off meat
			log.log(Level.INFO,
					this.getName() + " dropped off " + this.getCarriedFood()
							+ " meat at " + nest.getName());

			nest.increaseMeatQuantity(this.getCarriedFood());
			this.setCarriedFood(0.0);
		}

		if (this.getClass() == Gatherer.class) {
			// drop off fruit
			log.log(Level.INFO,
					this.getName() + " dropped off " + this.getCarriedFood()
							+ " fruit at " + nest.getName());

			nest.increaseFruitQuantity(this.getCarriedFood());
			this.setCarriedFood(0.0);
		}
	}

	protected void followPheromoneTrail(ThiefWorld world, Bag pheromones) {
		MutableDouble2D pheromoneTrail = new MutableDouble2D();
		Double2D myPosition = world.map.getObjectLocation(this);

		for (int i = 0; i < pheromones.size(); i++) {
			Pheromone pheromone = (Pheromone) pheromones.get(i);
			Double2D pheromonePosition = world.map.getObjectLocation(pheromone);

			pheromoneTrail.addIn(
					(pheromonePosition.getX() - myPosition.getX()) * 0.1,
					(pheromonePosition.getY() - myPosition.getY()) * 0.1);
		}

		// add movement randomness
		pheromoneTrail.addIn(new Double2D((Utilities.nextDouble() * 1.0 - 0.5)
				* ActiveAgent.getRandomMovementFactor(), (Utilities
				.nextDouble() * 1.0 - 0.5)
				* ActiveAgent.getRandomMovementFactor()));

		// normalize movement
		if (pheromoneTrail.length() > ActiveAgent.getMaxStepSize())
			pheromoneTrail.resize(0.0);
		else if (pheromoneTrail.length() > 0)
			pheromoneTrail.resize(ActiveAgent.getMaxStepSize()
					- pheromoneTrail.length());

		// add current position
		pheromoneTrail.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(pheromoneTrail));

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
		FoodSource closestFoodSource = personalObserver
				.getClosestFoodSourceWithinRange(world, foodType);

		if (closestFoodSource != null) {
			examineFoodSource(world, closestFoodSource);
		} else {
			wonderAround(world);
		}
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

		// add movement towards the food source
		movementTowardsFoodSource.addIn(
				(foodSourcePosition.getX() - myPosition.getX()) * 0.1,
				(foodSourcePosition.getY() - myPosition.getY()) * 0.1);

		// add movement randomness
		movementTowardsFoodSource.addIn(new Double2D(
				(Utilities.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor(), (Utilities
						.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor()));

		if (movementTowardsFoodSource.length() > ActiveAgent.getMaxStepSize())
			movementTowardsFoodSource.resize(0.0);
		else if (movementTowardsFoodSource.length() > 0)
			movementTowardsFoodSource.resize(ActiveAgent.getMaxStepSize()
					- movementTowardsFoodSource.length());
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
