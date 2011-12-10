package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Stoppable;
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

	public ActiveAgent(ActiveAgent agent) {
		// copy the other agent's properties
		this.setCarriedFood(agent.getCarriedFood());
		this.setGatheringSkill(agent.getGatheringSkill());
		this.setGatheringSuccess(agent.getGatheringSuccess());
		this.setHealth(agent.getHealth());
		this.setHuntingSkill(agent.getHuntingSkill());
		this.setHuntingSuccess(agent.getHuntingSuccess());
		this.setMaxAllowedFood(agent.getMaxAllowedFood());
		this.setName(agent.getName());
		this.setPersonalSuccess(agent.getPersonalSuccess());
		this.setStealingSkill(agent.getStealingSkill());
		this.setSwitchThreshold(agent.getSwitchThreshold());
		this.setTeamNo(agent.getTeamNo());

		this.personalObserver = new Observer(this);
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
		// allow the agent to return only when it's fully loaded
		// return this.getCarriedFood() == this.getMaxAllowedFood();

		// allow the agent to return whenever it has any amount of food loaded
		return this.getCarriedFood() > 0;
	}
	
	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;
		
		// interact with other agents within the world
		observeWorld(world);

		// decrease the skills to simulate the passing of time
		decaySkills();

		// mark the current position with a pheromone
		dropPheromone(world);
		
		// act upon the world
		act(world);

		// check if it's still worth doing the same job
		thinkAboutSwitchingJobs(world);
	}
	
	protected void observeWorld(ThiefWorld world) {
		// TODO Auto-generated method stub
		
	}

	protected abstract void act(ThiefWorld world);

	/**
	 * Drops a default strength pheromone based on the agent's type.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected void dropPheromone(ThiefWorld world) {
		// TODO check if a pheromone is not already in the same position. If so,
		// increase the pheromone strength in that position by the required
		// value (i.e. unit).

		Pheromone pheromone = null;
		if (this.getClass() == Gatherer.class) {
			// drop pheromone
			pheromone = new Pheromone(Pheromone.getDefaultPheromoneStrength(),
					PheromoneType.Gatherer, isReturningFood(),
					!isReturningFood());
		}

		if (this.getClass() == Hunter.class) {
			// drop pheromone
			pheromone = new Pheromone(Pheromone.getDefaultPheromoneStrength(),
					PheromoneType.Hunter, isReturningFood(), !isReturningFood());
		}

		if (this.getClass() == Thief.class) {
			// drop pheromone
			pheromone = new Pheromone(Pheromone.getDefaultPheromoneStrength(),
					PheromoneType.Thief, isReturningFood(), !isReturningFood());
		}

		if (this.getClass() == Protector.class) {
			// drop pheromone
			pheromone = new Pheromone(Pheromone.getDefaultPheromoneStrength(),
					PheromoneType.Protector, isReturningFood(),
					!isReturningFood());
		}

		Stoppable stoppable = world.schedule.scheduleRepeating(pheromone);
		pheromone.stoppable = stoppable;

		world.map.setObjectLocation(pheromone,
				world.map.getObjectLocation(this));
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
		wonderingMovement.normalize();
		wonderingMovement.multiplyIn(ActiveAgent.getMaxStepSize());

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

				// normalize movement
				movementTowardsNest.normalize();
				movementTowardsNest.multiplyIn(ActiveAgent.getMaxStepSize());

				// add current position
				movementTowardsNest.addIn(myPosition);

				// modify the agent's position
				world.map.setObjectLocation(this, new Double2D(
						movementTowardsNest));
			}
		} else {
			// search for nearby returning pheromones
			Bag nearbyPheromones = personalObserver
					.getPheromonesWithinRange(world);

			if (nearbyPheromones != null && nearbyPheromones.size() > 0) {
				Bag comingFromNestPheromones = new Bag();
				Bag returningFromFoodSourcePheromones = new Bag();

				for (int i = 0; i < nearbyPheromones.size(); i++) {
					Pheromone pheromone = (Pheromone) nearbyPheromones.get(i);

					if (pheromone.isReturningFromFoodSource())
						returningFromFoodSourcePheromones.add(pheromone);
					else if (pheromone.isComingFromNest())
						comingFromNestPheromones.add(pheromone);
				}

				if (comingFromNestPheromones.size() > 0) {
					// follow the pheromone trail back to the nest
					followPheromoneTrail(world, comingFromNestPheromones);
				} else {
					// no pheromones point towards the nest, wonder around
					wonderAround(world);
				}
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
		pheromoneTrail.normalize();
		pheromoneTrail.multiplyIn(ActiveAgent.getMaxStepSize());

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
			// try and follow the trail coming back from a food source
			Bag closebyPheromones = null;

			if (foodType == FruitSource.class)
				closebyPheromones = this.personalObserver
						.getPheromonesWithinRange(world, PheromoneType.Gatherer);
			else if (foodType == MeatSource.class)
				closebyPheromones = this.personalObserver
						.getPheromonesWithinRange(world, PheromoneType.Hunter);

			if (closebyPheromones != null && closebyPheromones.size() > 0) {
				// filter for the pheromones coming from a food source

				Bag pheromonesReturningFromFoodSource = new Bag();
				for (int i = 0; i < closebyPheromones.size(); i++) {
					Pheromone pheromone = (Pheromone) closebyPheromones.get(i);

					if (pheromone.isReturningFromFoodSource())
						pheromonesReturningFromFoodSource.add(pheromone);
				}

				if (pheromonesReturningFromFoodSource.size() > 0) {
					followPheromoneTrail(world,
							pheromonesReturningFromFoodSource);
				} else {
					wonderAround(world);
				}
			} else
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

		movementTowardsFoodSource.normalize();
		movementTowardsFoodSource.multiplyIn(ActiveAgent.getMaxStepSize());

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

	/**
	 * Simulates the decay of the success rate.
	 */
	protected void decaySkills() {
		
	}

	/**
	 * Transforms the current agent into another agent.
	 * 
	 * @param newAgentType
	 *            the new agent type.
	 * @return the morphed agent.
	 */
	protected ActiveAgent morph(Class<?> newAgentType) {
		ActiveAgent morphedAgent = null;

		if (newAgentType == Gatherer.class) {
			// morph into a gatherer
			morphedAgent = new Gatherer(this);
		}

		if (newAgentType == Hunter.class) {
			// morph into a hunter
			morphedAgent = new Hunter(this);
		}

		return morphedAgent;
	}

	/**
	 * Morphs the current agent into a new type of agent and replaces the
	 * current agent with it on both the map and the world scheduler.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param newAgentType
	 *            the type of agent into which the current agent will morph
	 * @return the morphed agent
	 */
	protected ActiveAgent replace(ThiefWorld world, Class<?> newAgentType) {
		ActiveAgent morphedAgent = morph(newAgentType);

		if (morphedAgent != null) {
			// add agent on the map in the same place as the current agent
			world.map.setObjectLocation(morphedAgent,
					world.map.getObjectLocation(this));

			// schedule agent firing
			Stoppable stoppable = world.schedule
					.scheduleRepeating(morphedAgent);
			morphedAgent.stoppable = stoppable;

			// remove the current agent
			world.map.remove(this);

			// stop the old agent from firing
			this.stoppable.stop();

			// log the event
			Logger log = Logger.getLogger(this.getName());
			log.log(Level.INFO, this.getName() + " morphed into "
					+ morphedAgent.getName());
		}

		return morphedAgent;
	}

	/**
	 * Makes the agent think about switching jobs based on its previous success
	 * rate and the current information it has from other agents in the world.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected abstract void thinkAboutSwitchingJobs(ThiefWorld world);
}
