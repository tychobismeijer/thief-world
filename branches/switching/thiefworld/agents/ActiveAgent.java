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

/**
 * The abstraction of an active agent that has the ability of switching between
 * different roles within the environment.
 * 
 * @author Stefan Adrian Boronea, Kees van Gelder, Daniel Karavolos
 * 
 */
public abstract class ActiveAgent extends Agent {

	/**
	 * @uml.property  name="stagnatingFor"
	 */
	protected int stagnatingFor = 0;
	
	/**
	 * The maximum range within which the agent can perform actions.
	 */
	private static double actionRange = 0.5;

	/**
	 * The maximum range within which the agent can observe.
	 */
	private static double agentRange = 30.0;

	/**
	 * The maximum quantity of food which an agent can carry by default.
	 */
	private static double defaultMaxCarriedFood = 1.0;

	/**
	 * The maximum allowed movement step size for an agent.
	 */
	private static double maxStepSize = 1.0;

	/**
	 * Determines how random are the movements of the agent.
	 */
	private static double randomMovementFactor = 0.1;

	private static final long serialVersionUID = 5597485865861516823L;

	/*
	 * Determines decay of skill level per timestep *it's not really a rate
	 * though...*
	 */
	private static double skillDecayRate = 0.005; // Agent loses all skill in
													// 200 time steps

	/*
	 * Determines increase of skill level per dropOffFood action *it's not
	 * really a rate though...*
	 */
	private static double skillIncreaseRate = 0.02; // Agent is fully
													// specialized after 50
													// actions

	protected static double switchProb = 0.05;

	/**
	 * Retrieves the range within which the agent can perform actions.
	 * 
	 * @return the range within which the agent can perform actions.
	 */
	public static double getActionRange() {
		return actionRange;
	}

	/**
	 * Retrieves the range within which the agent can interact with other agents
	 * and can observe the environment.
	 * 
	 * @return the range within which the agent can interact with other agents
	 *         and can observe the environment.
	 */
	public static double getAgentRange() {
		return ActiveAgent.agentRange;
	}

	/**
	 * Retrieves the maximum amount of food which the agent can carry at once by
	 * default.
	 * 
	 * @return the maximum amount of food which the agent can carry at once by
	 *         default.
	 */
	public static double getDefaultMaxCarriedFood() {
		return ActiveAgent.defaultMaxCarriedFood;
	}

	/**
	 * Retrieves the maximum allowed movement step size for an agent.
	 * 
	 * @return the maximum allowed movement step size for an agent.
	 */
	public static double getMaxStepSize() {
		return ActiveAgent.maxStepSize;
	}

	/**
	 * Retrieves the randomness factor for the agent movement.
	 * 
	 * @return the randomness of the agent movement.
	 */
	public static double getRandomMovementFactor() {
		return ActiveAgent.randomMovementFactor;
	}

	/*
	 * Retrieves skill decay rate per timestep for the agent
	 */
	public static double getSkillDecayRate() {
		return skillDecayRate;
	}

	/*
	 * Retrieves skill increase rate per returnFood action for the agent
	 */
	public static double getSkillIncreaseRate() {
		return skillIncreaseRate;
	}

	/**
	 * @return the switchProb
	 */
	public static double getSwitchProbability() {
		return switchProb;
	}

	/**
	 * Sets the range within which the agent can perform actions.
	 * 
	 * @param actionRange
	 *            the range within which the agent can perform actions.
	 */
	public static void setActionRange(double actionRange) {
		ActiveAgent.actionRange = actionRange;
	}

	/**
	 * Sets the range within which the agent can interact with other agents and
	 * can observe the environment.
	 * 
	 * @param agentRange
	 *            the range within which the agent can interact with other
	 *            agents and can observe the environment.
	 */
	public static void setAgentRange(double agentRange) {
		ActiveAgent.agentRange = agentRange;
	}

	/**
	 * Sets the maximum amount of food which the agent can carry at once by
	 * default.
	 * 
	 * @param defaultMaxCarriedFood
	 *            the maximum amount of food which the agent can carry at once
	 *            by default.
	 */
	public static void setDefaultMaxCarriedFood(double defaultMaxCarriedFood) {
		if (defaultMaxCarriedFood >= 0) {
			ActiveAgent.defaultMaxCarriedFood = defaultMaxCarriedFood;
		}
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
	 * Sets the randomness factor for the agent movement.
	 * 
	 * @param randomMovementFactor
	 *            the randomness of the agent movement.
	 */
	public static void setRandomMovementFactor(double randomMovementFactor) {
		if (randomMovementFactor >= 0)
			ActiveAgent.randomMovementFactor = randomMovementFactor;
	}

	/*
	 * Sets skill decay rate per timestep for the agent
	 */
	public static void setSkillDecayRate(double newRate) {
		skillDecayRate = newRate;
	}

	/*
	 * Sets skill increase rate per returnFood action for the agent
	 */
	public static void setSkillIncreaseRate(double newRate) {
		skillIncreaseRate = newRate;
	}

	/**
	 * @param switchProb
	 *            the switchProb to set
	 */
	public static void setSwitchProbability(double switchProb) {
		ActiveAgent.switchProb = switchProb;
	}

	/**
	 * The fruit quantity which the agent currently carries.
	 * @uml.property  name="carriedFruit"
	 */
	private double carriedFruit;

	/**
	 * The meat quantity which the agent currently carries.
	 * @uml.property  name="carriedMeat"
	 */
	private double carriedMeat;

	/**
	 * The agent's skill for gathering fruit.
	 * @uml.property  name="gatheringSkill"
	 */
	protected double gatheringSkill = 0.00;

	/**
	 * The gathering success rate of the other agents which the current agent interacts with over time.
	 * @uml.property  name="gatheringSuccess"
	 */
	protected double gatheringSuccess = 0.0;

	/**
	 * The agent's current health level.
	 * @uml.property  name="health"
	 */
	private double health;

	/**
	 * The agent's skill for hunting.
	 * @uml.property  name="huntingSkill"
	 */
	protected double huntingSkill = 0.00;

	/**
	 * The hunting success rate of the other agents which the current agent interacts with over time.
	 * @uml.property  name="huntingSuccess"
	 */
	protected double huntingSuccess = 0.0;

	/**
	 * The maximum allowed food quantity that can be carried at once.
	 * @uml.property  name="maxAllowedFood"
	 */
	private double maxAllowedFood = 2.0;

	/**
	 * The observer which keeps track of the other agents within its observation range.
	 * @uml.property  name="personalObserver"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="correspondingAgent:thiefworld.agents.Observer"
	 */
	Observer personalObserver;

	/**
	 * The agent's success rate on performing the current role it has.
	 * @uml.property  name="personalSuccess"
	 */
	protected double personalSuccess = 0.0;

	/**
	 * The agent's skill in stealing food from other teams.
	 * @uml.property  name="stealingSkill"
	 */
	protected double stealingSkill = 0.0;

	/**
	 * @uml.property  name="stealingSuccess"
	 */
	private double stealingSuccess = 0.0;

	/**
	 * The agent's disposition towards changing its role.
	 * @uml.property  name="switchThreshold"
	 */
	protected double switchThreshold = Utilities.nextDouble(0.1, 0.95);

	/*
	 * Keeps track of time since last food drop off, used for determining
	 * personalSucces
	 */
	/**
	 * @uml.property  name="timeSinceLastDropOff"
	 */
	private int timeSinceLastDropOff = 0;
	
	/**
	 * Creates a new agent and initializes its parameters to default values.
	 */
	public ActiveAgent() {
		// set task switching threshold
		switchThreshold = Utilities.nextDouble();
		health = 1.0;
		maxAllowedFood = ActiveAgent.getDefaultMaxCarriedFood();
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
		this.setCarriedFruit(agent.getCarriedFruit());
		this.setCarriedMeat(agent.getCarriedMeat());
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
		this.gatheringSkill -= skillDecayRate;
		this.stealingSkill -= skillDecayRate;
		this.huntingSkill -= skillDecayRate;

		if (this.gatheringSkill < 0)
			this.gatheringSkill = 0;

		if (this.huntingSkill < 0)
			this.huntingSkill = 0;

		if (this.stealingSkill < 0)
			this.stealingSkill = 0;

	}

	/**
	 * Decreases the quantity of carried fruit by a specified amount.
	 * 
	 * @param amount
	 *            the amount with which the currently carried fruit will be
	 *            decreased.
	 */
	public void decreaseCarriedFruit(double amount) {
		this.carriedFruit -= amount;

		if (this.carriedFruit < 0.0)
			this.carriedFruit = 0.0;
	}

	/**
	 * Decreases the quantity of carried meat by a specified amount.
	 * 
	 * @param amount
	 *            the amount with which the currently carried meat will be
	 *            decreased.
	 */
	public void decreaseCarriedMeat(double amount) {
		this.carriedMeat -= amount;

		if (this.carriedMeat < 0.0)
			this.carriedMeat = 0.0;
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

			nest.increaseMeatQuantity(this.getCarriedMeat());
			// keep track of succes just after dropping off
			updatePersonalSucces(this.getCarriedMeat());
			this.setCarriedMeat(0.0);
		}

		if (this.getClass() == Gatherer.class) {
			// drop off fruit
			log.log(Level.INFO,
					this.getName() + " dropped off " + this.getCarriedFood()
							+ " fruit at " + nest.getName());

			nest.increaseFruitQuantity(this.getCarriedFruit());
			// keep track of succes just after dropping off
			updatePersonalSucces(this.getCarriedFruit());
			this.setCarriedFruit(0.0);
		}

		if (this.getClass() == Thief.class) {
			nest.increaseMeatQuantity(this.getCarriedFood());
			// keep track of succes
			updatePersonalSucces(this.getCarriedFood());

			if (this.getCarriedFruit() > 0.0) {
				log.log(Level.INFO,
						this.getName() + " dropped off "
								+ this.getCarriedFruit() + " fruit at "
								+ nest.getName());
				this.setCarriedFruit(0.0);
			}

			if (this.getCarriedMeat() > 0.0) {
				log.log(Level.INFO,
						this.getName() + " dropped off "
								+ this.getCarriedMeat() + " meat at "
								+ nest.getName());
				this.setCarriedMeat(0.0);
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

			if (foodSource.getClass() == FruitSource.class)
				this.increaseCarriedFruit(foodToExtract);
			else if (foodSource.getClass() == MeatSource.class)
				this.increaseCarriedMeat(foodToExtract);

			// log event
			Logger log = Logger.getLogger(this.getName());
			log.log(Level.INFO, this.getName() + " picked up " + foodToExtract
					+ " food from " + foodSource.getName());
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
	 * Retrieves the amount of food which the agent currently carries.
	 * 
	 * @return the amount of food which the agent currently carries.
	 */
	public double getCarriedFood() {
		return getCarriedFruit() + getCarriedMeat();
	}

	/**
	 * @return
	 * @uml.property  name="carriedFruit"
	 */
	public double getCarriedFruit() {
		return carriedFruit;
	}

	/**
	 * @return
	 * @uml.property  name="carriedMeat"
	 */
	public double getCarriedMeat() {
		return carriedMeat;
	}

	/**
	 * Retrieves the skill with which the agent gathers fruit.
	 * @return  the skill with which the agent gathers fruit.
	 * @uml.property  name="gatheringSkill"
	 */
	public double getGatheringSkill() {
		return gatheringSkill;
	}

	/**
	 * @return
	 * @uml.property  name="gatheringSuccess"
	 */
	public double getGatheringSuccess() {
		if (personalObserver != null)
			return personalObserver.getAverageGatheringSuccess();

		return gatheringSuccess;
	}

	/**
	 * @return
	 * @uml.property  name="health"
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * @return
	 * @uml.property  name="huntingSkill"
	 */
	public double getHuntingSkill() {
		return huntingSkill;
	}

	/**
	 * @return
	 * @uml.property  name="huntingSuccess"
	 */
	public double getHuntingSuccess() {
		if (personalObserver != null)
			return personalObserver.getAverageHuntingSuccess();
		return huntingSuccess;
	}

	/**
	 * @return
	 * @uml.property  name="maxAllowedFood"
	 */
	public double getMaxAllowedFood() {
		return maxAllowedFood;
	}

	/**
	 * @return
	 * @uml.property  name="personalSuccess"
	 */
	public double getPersonalSuccess() {
		return personalSuccess;
	}

	/**
	 * @return
	 * @uml.property  name="stealingSkill"
	 */
	public double getStealingSkill() {
		return stealingSkill;
	}

	/**
	 * @return
	 * @uml.property  name="stealingSuccess"
	 */
	public double getStealingSuccess() {
		if (personalObserver != null)
			return personalObserver.getAverageStealingSuccess();
		return stealingSuccess;
	}

	/**
	 * @return
	 * @uml.property  name="switchThreshold"
	 */
	public double getSwitchThreshold() {
		return switchThreshold;
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

	public void increaseCarriedFruit(double amount) {
		this.carriedFruit += amount;

		if (this.carriedFruit > maxAllowedFood)
			this.carriedFruit = maxAllowedFood;
	}

	public void increaseCarriedMeat(double amount) {
		this.carriedMeat += amount;

		if (this.carriedMeat > maxAllowedFood)
			this.carriedMeat = maxAllowedFood;
	}

	protected void increaseSkill() {
		Logger log = Logger.getLogger(getName());

		if (this.getClass() == Gatherer.class) {
			this.gatheringSkill += skillIncreaseRate;
			log.log(Level.INFO, this.getName()
					+ " increased its' gathering skill to "
					+ this.gatheringSkill);
		} else if (this.getClass() == Hunter.class) {
			this.huntingSkill += skillIncreaseRate;
			log.log(Level.INFO, this.getName()
					+ " increased its' hunting skill to " + this.huntingSkill);
		} else if (this.getClass() == Thief.class) {
			this.stealingSkill += skillIncreaseRate;
			log.log(Level.INFO, this.getName()
					+ " increased its' stealing skill to " + this.stealingSkill);
		}

		if (this.gatheringSkill > 1.0)
			this.gatheringSkill = 1.0;
		if (this.huntingSkill > 1.0)
			this.huntingSkill = 1.0;
		if (this.stealingSkill > 1.0)
			this.stealingSkill = 1.0;

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

		if (newAgentType == Thief.class) {
			// morph into a thief
			morphedAgent = new Thief(this);
		}

		if (morphedAgent != null)
			morphedAgent.setPersonalSuccess(0.0);

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
						* ActiveAgent.getRandomMovementFactor(), (Utilities
						.nextDouble() * 1.0 - 0.5)
						* ActiveAgent.getRandomMovementFactor()));

		movementTowardsNest.normalize();
		movementTowardsNest.multiplyIn(ActiveAgent.getMaxStepSize());

		// add current position
		movementTowardsNest.addIn(myPosition);

		// modify the agent's position
		world.map.setObjectLocation(this, new Double2D(movementTowardsNest));
	}

	protected void observeWorld(ThiefWorld world) {
		personalObserver.exchangeInformation(world);
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
			if (newAgentType == Hunter.class) {
				world.huntersBag.add(morphedAgent);
			}
			if (newAgentType == Gatherer.class) {
				world.gatherersBag.add(morphedAgent);
			}
			if (newAgentType == Thief.class) {
				world.thievesBag.add(morphedAgent);
			}

			// schedule agent firing
			Stoppable stoppable = world.schedule
					.scheduleRepeating(morphedAgent);
			morphedAgent.stoppable = stoppable;

			// remove the current agent
			world.map.remove(this);

			if (this.getClass() == Hunter.class) {
				world.huntersBag.remove(this);
			}
			if (this.getClass() == Gatherer.class) {
				world.gatherersBag.remove(this);
			}
			if (this.getClass() == Thief.class) {
				world.thievesBag.remove(this);
			}

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
	 * @param carriedFruit
	 * @uml.property  name="carriedFruit"
	 */
	public void setCarriedFruit(double carriedFruit) {
		this.carriedFruit = carriedFruit;
	}

	/**
	 * @param carriedMeat
	 * @uml.property  name="carriedMeat"
	 */
	public void setCarriedMeat(double carriedMeat) {
		this.carriedMeat = carriedMeat;
	}

	/**
	 * @param gatheringSkill
	 * @uml.property  name="gatheringSkill"
	 */
	public void setGatheringSkill(double gatheringSkill) {
		this.gatheringSkill = gatheringSkill;
	}

	/**
	 * @param gatheringSuccess
	 * @uml.property  name="gatheringSuccess"
	 */
	public void setGatheringSuccess(double gatheringSuccess) {
		this.gatheringSuccess = gatheringSuccess;
	}

	/**
	 * @param health
	 * @uml.property  name="health"
	 */
	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * @param huntingSkill
	 * @uml.property  name="huntingSkill"
	 */
	public void setHuntingSkill(double huntingSkill) {
		this.huntingSkill = huntingSkill;
	}

	/**
	 * @param huntingSuccess
	 * @uml.property  name="huntingSuccess"
	 */
	public void setHuntingSuccess(double huntingSuccess) {
		this.huntingSuccess = huntingSuccess;
	}

	/**
	 * @param maxAllowedFood
	 * @uml.property  name="maxAllowedFood"
	 */
	public void setMaxAllowedFood(double maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}

	/**
	 * @param personalSuccess
	 * @uml.property  name="personalSuccess"
	 */
	public void setPersonalSuccess(double personalSuccess) {
		this.personalSuccess = personalSuccess;
	}

	/**
	 * @param stealingSkill
	 * @uml.property  name="stealingSkill"
	 */
	public void setStealingSkill(double stealingSkill) {
		this.stealingSkill = stealingSkill;
	}

	/**
	 * @param stealingSuccess
	 * @uml.property  name="stealingSuccess"
	 */
	public void setStealingSuccess(double stealingSuccess) {
		this.stealingSuccess = stealingSuccess;
	}

	/**
	 * @param switchThreshold
	 * @uml.property  name="switchThreshold"
	 */
	public void setSwitchThreshold(double switchThreshold) {
		this.switchThreshold = switchThreshold;
	}

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		// update time
		this.timeSinceLastDropOff++;

		// interact with other agents within the world
		observeWorld(world);

		// decrease the skills to simulate the passing of time
		decaySkills();

		// mark the current position with a pheromone
		dropPheromone(world);

		// act upon the world
		act(world);

		// only switch job if agent is not carrying anything
		if (!isReturningFood()) {
			// check if it's still worth doing the same job
			thinkAboutSwitchingJobs(world);
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

	/*
	 * Keeps updates personal success of the agent. Called after each dropOff
	 * action
	 */
	protected void updatePersonalSucces(double retrievedFood) {
		Logger log = Logger.getLogger(getName());
		log.log(Level.INFO, this.getName()
				+ " increased its' gathering skill to " + this.gatheringSkill);

		if (this.timeSinceLastDropOff != 0) {
			double currentSucces = retrievedFood
					/ (0.1 * this.timeSinceLastDropOff); // perhaps per 10
															// timesteps is
															// better
			this.personalSuccess += 0.1 * (currentSucces - this.personalSuccess);
		} else
			System.out
					.println("Division by zero prevented. You're doing something wrong!!!");
		log.log(Level.INFO, this.getName() + "'s current personalSuccess is "
				+ this.personalSuccess + " units of food per 10 time steps");
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
}
