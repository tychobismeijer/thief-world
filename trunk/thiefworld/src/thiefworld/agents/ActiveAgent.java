package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;
import thiefworld.agents.misc.AgentSkills;
import thiefworld.agents.misc.AgentSuccessRates;
import thiefworld.agents.misc.InventoryData;
import thiefworld.agents.misc.SimulationParameters;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

/**
 * The abstraction of an active agent that has the ability of switching between
 * different roles within the environment.
 * 
 * @author Stefan Adrian Boronea
 * @author Kees van Gelder
 * @author Daniel Karavolos
 * 
 */
public abstract class ActiveAgent extends Agent {

	private static final long serialVersionUID = 5597485865861516823L;

	/**
	 * The agent's current health level.
	 */
	private double health;

	/**
	 * The agent's collected resources.
	 */
	protected InventoryData inventory = new InventoryData(2.0);

	/**
	 * The observer which keeps track of the other agents within its observation
	 * range.
	 */
	Observer personalObserver;

	protected AgentSuccessRates successRates = new AgentSuccessRates(0.0, 0.0,
			0.0);

	protected AgentSkills data = new AgentSkills(0.00, 0.00, 0.0);

	/**
	 * The agent's disposition towards changing its role.
	 */
	protected double switchThreshold = 0.1;// Utilities.nextDouble(0.1,0.7);
											// //0.1, 0.7

	/*
	 * Keeps track of time since last role switch, might be used for logarithmic
	 * skill change NOT USED YET
	 */
	protected int timeSinceLastMorph = 0;

	/*
	 * Keeps track of time since last food drop off, used for determining
	 * personalSucces
	 */
	private int timeSinceLastDropOff = 0;

	/**
	 * Creates a new agent and initializes its parameters to default values.
	 */
	public ActiveAgent() {
		// set task switching threshold
		switchThreshold = Utilities.nextDouble();
		health = 1.0;
		inventory.setMaxAllowedFood(SimulationParameters
				.getDefaultMaxCarriedFood());
		personalObserver = new Observer(this);
	}

	/**
	 * Creates a new agent and initializes its parameters using another agent's
	 * current parameters.
	 * 
	 * @param agent
	 *            the agent from which the initial parameters will be copied.
	 */
	public ActiveAgent(ActiveAgent agent) {
		// copy the other agent's properties
		this.setHealth(agent.getHealth());
		this.setName(agent.getName());
		this.setTeamNo(agent.getTeamNo());

		this.personalObserver = new Observer(this);
	}

	/**
	 * Performs agent-specific actions within the environment.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected abstract void act(ThiefWorld world);

	/**
	 * Simulates the decay of the agent's unused skills
	 */
	protected void decaySkills() {
		this.data.setHuntingSkill(this.data.getHuntingSkill()
				- SimulationParameters.getSkillDecayRate());
		this.data.setGatheringSkill(this.data.getGatheringSkill()
				- SimulationParameters.getSkillDecayRate());

		if (this.data.getGatheringSkill() < 0)
			this.data.setGatheringSkill(0);
		if (this.data.getHuntingSkill() < 0)
			this.data.setHuntingSkill(0);
		if (this.data.getStealingSkill() < 0)
			this.data.setStealingSkill(0);

	}

	/**
	 * Decreases the quantity of carried fruit by a specified amount.
	 * 
	 * @param amount
	 *            the amount with which the currently carried fruit will be
	 *            decreased.
	 */
	public void decreaseCarriedFruit(double amount) {
		this.inventory.setCarriedFruit(this.inventory.getCarriedFruit()
				- amount);

		if (this.inventory.getCarriedFruit() < 0.0)
			this.inventory.setCarriedFruit(0.0);
	}

	/**
	 * Decreases the quantity of carried meat by a specified amount.
	 * 
	 * @param amount
	 *            the amount with which the currently carried meat will be
	 *            decreased.
	 */
	public void decreaseCarriedMeat(double amount) {
		this.inventory.setCarriedMeat(this.inventory.getCarriedMeat() - amount);

		if (this.inventory.getCarriedMeat() < 0.0)
			this.inventory.setCarriedMeat(0.0);
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
			/*
			 * log.log(Level.INFO, this.getName() + " dropped off " +
			 * this.getCarriedFood() + " meat at " + nest.getName());
			 */
			nest.increaseMeatQuantity(inventory.getCarriedMeat());
			// keep track of succes just after dropping off
			updatePersonalSuccess(inventory.getCarriedMeat());
			inventory.setCarriedMeat(0.0);
		}

		if (this.getClass() == Gatherer.class) {
			// drop off fruit
			/*
			 * log.log(Level.INFO, this.getName() + " dropped off " +
			 * this.getCarriedFood() + " fruit at " + nest.getName());
			 */
			nest.increaseFruitQuantity(inventory.getCarriedFruit());
			// keep track of succes just after dropping off
			updatePersonalSuccess(inventory.getCarriedFruit());
			inventory.setCarriedFruit(0.0);
		}

		if (this.getClass() == Thief.class) {
			nest.increaseMeatQuantity(inventory.getCarriedFood());
			// keep track of succes
			updatePersonalSuccess(inventory.getCarriedFood());

			if (inventory.getCarriedFruit() > 0.0) {
				log.log(Level.INFO,
						this.getName() + " dropped off "
								+ inventory.getCarriedFruit() + " fruit at "
								+ nest.getName());
				inventory.setCarriedFruit(0.0);
			}

			if (inventory.getCarriedMeat() > 0.0) {
				log.log(Level.INFO,
						this.getName() + " dropped off "
								+ inventory.getCarriedMeat() + " meat at "
								+ nest.getName());
				inventory.setCarriedMeat(0.0);
			}
		}
		// increase skill after dropping of
		increaseSkill();
		this.timeSinceLastDropOff = 0;
	}

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

		if (foodSourceDistance <= SimulationParameters.getActionRange()) {
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
		double maximumFoodToExtract = inventory.getMaxAllowedFood()
				- inventory.getCarriedFood();

		double foodToExtract = 0.0;

		if (maximumFoodToExtract > 0)
			foodToExtract = Math.min(availableFood, maximumFoodToExtract);

		if (foodToExtract > 0) {
			// extract food
			foodSource.decreaseFoodQuantity(foodToExtract);

			if (foodSource.getClass() == FruitSource.class)
				this.increaseCarriedFruit(foodToExtract);
			else if (foodSource.getClass() == MeatSource.class)
				this.increaseCarriedMeat(foodToExtract);
		}
	}

	protected Bag filterPheromones(Bag pheromones, boolean comingFromNest) {
		Bag filteredPheromones = new Bag();

		if (pheromones != null) {
			for (int i = 0; i < pheromones.size(); i++) {
				Pheromone pheromone = (Pheromone) pheromones.get(i);
				if (pheromone.isComingFromNest() == comingFromNest) {
					filteredPheromones.add(pheromone);
				}
			}
		}

		return filteredPheromones;
	}

	/**
	 * Follows a pheromone trail specified by a collection of pheromones. The
	 * agent will make a step toward the averaged normalized vector for each
	 * pheromone and will also take into account a random movement.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param pheromones
	 *            the collection of pheromones which the agent should follow.
	 */
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
				* SimulationParameters.getRandomMovementFactor(), (Utilities
				.nextDouble() * 1.0 - 0.5)
				* SimulationParameters.getRandomMovementFactor()));

		// normalize movement
		pheromoneTrail.normalize();
		pheromoneTrail.multiplyIn(SimulationParameters.getMaxStepSize());

		// add current position
		pheromoneTrail.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(pheromoneTrail));

	}

	/**
	 * Retrieves the health level of the agent (normalized).
	 * 
	 * @return the current health level.
	 */
	public double getHealth() {
		return health;
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
	 * Increases the amount of carried fruit by the specified amount. The agent
	 * will pick up ONLY the maximum allowed amount as specified in the
	 * {@link InventoryData#getMaxAllowedFood()}.
	 * 
	 * @param amount
	 *            the fruit amount that the agent wants to carry.
	 */
	public void increaseCarriedFruit(double amount) {
		this.inventory.setCarriedFruit(this.inventory.getCarriedFruit()
				+ amount);

		if (this.inventory.getCarriedFruit() > inventory.getMaxAllowedFood())
			this.inventory.setCarriedFruit(inventory.getMaxAllowedFood());
	}

	/**
	 * Increases the amount of carried meat by the specified amount. The agent
	 * will pick up ONLY the maximum allowed amount as specified in the
	 * {@link InventoryData#getMaxAllowedFood()}.
	 * 
	 * @param amount
	 *            the meat amount that the agent wants to carry.
	 */
	public void increaseCarriedMeat(double amount) {
		this.inventory.setCarriedMeat(this.inventory.getCarriedMeat() + amount);

		if (this.inventory.getCarriedMeat() > inventory.getMaxAllowedFood())
			this.inventory.setCarriedMeat(inventory.getMaxAllowedFood());
	}

	/**
	 * Increases the skill level of the agent depending on its current role.
	 */
	protected void increaseSkill() {
		Logger log = Logger.getLogger(getName());

		if (this.getClass() == Gatherer.class) {
			this.data.setGatheringSkill(this.data.getGatheringSkill()
					+ SimulationParameters.getSkillIncreaseRate());
			log.log(Level.INFO,
					this.getName() + " increased its' gathering skill to "
							+ this.data.getGatheringSkill());
		} else if (this.getClass() == Hunter.class) {
			this.data.setHuntingSkill(this.data.getHuntingSkill()
					+ SimulationParameters.getSkillIncreaseRate());
			log.log(Level.INFO,
					this.getName() + " increased its' hunting skill to "
							+ this.data.getHuntingSkill());
		} else if (this.getClass() == Thief.class) {
			this.data.setStealingSkill(this.data.getStealingSkill()
					+ SimulationParameters.getSkillIncreaseRate());
			log.log(Level.INFO,
					this.getName() + " increased its' stealing skill to "
							+ this.data.getStealingSkill());
		}

		if (this.data.getGatheringSkill() > 1.0)
			this.data.setGatheringSkill(1.0);
		if (this.data.getHuntingSkill() > 1.0)
			this.data.setHuntingSkill(1.0);
		if (this.data.getStealingSkill() > 1.0)
			this.data.setStealingSkill(1.0);

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
		return inventory.getCarriedFood() > 0;
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
		this.timeSinceLastMorph = 0;

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
						* SimulationParameters.getRandomMovementFactor(),
				(Utilities.nextDouble() * 1.0 - 0.5)
						* SimulationParameters.getRandomMovementFactor()));

		movementTowardsFoodSource.normalize();
		movementTowardsFoodSource.multiplyIn(SimulationParameters
				.getMaxStepSize());

		// add current position
		movementTowardsFoodSource.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(
				movementTowardsFoodSource));
	}

	/**
	 * Moves in the direction of a nest.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param nest
	 *            the nest in which direction the agent must go.
	 */
	protected void moveTowardsNest(ThiefWorld world, Nest nest) {
		Double2D myPosition = world.map.getObjectLocation(this);
		Double2D nestPosition = world.map.getObjectLocation(nest);

		MutableDouble2D movementTowardsNest = new MutableDouble2D();

		// add movement towards the food source
		movementTowardsNest.addIn(
				(nestPosition.getX() - myPosition.getX()) * 0.1,
				(nestPosition.getY() - myPosition.getY()) * 0.1);

		// add movement randomness
		movementTowardsNest.addIn(new Double2D(
				(Utilities.nextDouble() * 1.0 - 0.5)
						* SimulationParameters.getRandomMovementFactor(),
				(Utilities.nextDouble() * 1.0 - 0.5)
						* SimulationParameters.getRandomMovementFactor()));

		movementTowardsNest.normalize();
		movementTowardsNest.multiplyIn(SimulationParameters.getMaxStepSize());

		// add current position
		movementTowardsNest.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(movementTowardsNest));
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
	 * Makes a return trip to the nest to drop the food.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected void returnFood(ThiefWorld world, PheromoneType pheromonesType) {
		// search for a nearby nest
		Nest closestNest = personalObserver.searchForNestWithinRange(world,
				true);

		if (closestNest != null) {
			// is the nest within drop-off distance?
			Double2D myPosition = world.map.getObjectLocation(this);
			Double2D nestPosition = world.map.getObjectLocation(closestNest);

			if (myPosition.distance(nestPosition) <= SimulationParameters
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
								* SimulationParameters
										.getRandomMovementFactor(), (Utilities
								.nextDouble() * 1.0 - 0.5)
								* SimulationParameters
										.getRandomMovementFactor()));

				// normalize movement
				movementTowardsNest.normalize();
				movementTowardsNest.multiplyIn(SimulationParameters
						.getMaxStepSize());

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
	 * Sets the health of an agent.
	 * 
	 * @param health
	 *            the health value (normalized)
	 */
	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * @param switchProb
	 *            the switchProb to set
	 */
	public void setSwitchProbability(double switchProb) {
		SimulationParameters.setSwitchProbability(switchProb);
	}

	/**
	 * Sets the threshold at which an agent starts thinking of switching roles.
	 * 
	 * @param switchThreshold
	 *            the threshold at which the agent starts thinking of switching
	 *            roles.
	 */
	public void setSwitchThreshold(double switchThreshold) {
		this.switchThreshold = switchThreshold;
	}

	/**
	 * Activates the agent and sets its tasks depending on its role and the
	 * environment information.
	 */
	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		// update time
		this.timeSinceLastDropOff++;
		this.timeSinceLastMorph++;

		// decrease the skills to simulate the passing of time
		decaySkills();

		// mark the current position with a pheromone
		dropPheromone(world);

		// act upon the world
		act(world);

		// check if it's still worth doing the same job
		if (timeSinceLastMorph > 50)
			thinkAboutSwitchingJobsSimple(world);

		if (timeSinceLastDropOff > 100) {
			updatePersonalSuccess(0);
		}

	}

	/**
	 * Makes the agent think about switching jobs based on its previous success
	 * rate and the current information it has from other agents in the world.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	protected abstract void thinkAboutSwitchingJobs(ThiefWorld world);

	protected abstract void thinkAboutSwitchingJobsSimple(ThiefWorld world);

	/**
	 * Keeps updates personal success of the agent. Called after each dropOff
	 * action
	 */
	protected void updatePersonalSuccess(double retrievedFood) {
		Logger log = Logger.getLogger(getName());

		if (this.timeSinceLastDropOff != 0) {
			double currentSucces = retrievedFood
					/ (0.01 * this.timeSinceLastDropOff); // retrieved food per
															// 100 time steps
			this.successRates.setPersonalSuccess(this.successRates
					.getPersonalSuccess()
					+ (0.1 * (currentSucces - this.successRates
							.getPersonalSuccess())));
		} else
			System.out
					.println("Division by zero prevented. You're doing something wrong!!!");
		log.log(Level.INFO, this.getName() + "'s current personalSuccess is "
				+ this.successRates.getPersonalSuccess()
				+ " units of food per 100 time steps");
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
						* SimulationParameters.getRandomMovementFactor(),
				(Utilities.nextDouble() * 1.0 - 0.5)
						* SimulationParameters.getRandomMovementFactor()));

		// normalize movement
		wonderingMovement.normalize();
		wonderingMovement.multiplyIn(SimulationParameters.getMaxStepSize());

		// add current position
		Double2D myPosition = world.map.getObjectLocation(this);
		wonderingMovement.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(wonderingMovement));
	}
}
