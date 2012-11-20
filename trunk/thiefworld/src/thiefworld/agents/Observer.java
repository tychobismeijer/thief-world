package thiefworld.agents;

import sim.engine.SimState;
import sim.util.Bag;
import sim.util.Double2D;
import thiefworld.agents.misc.SimulationParameters;
import thiefworld.main.ThiefWorld;

/**
 * Describes an internal observer which manages the interactions with other
 * agents and maintains the internal agent representation of the environment.
 * 
 * @author Kees van Gelder
 * 
 */
public class Observer extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2220784702275759607L;

	/**
	 * The agent which the observer is linked to.
	 */
	private ActiveAgent correspondingAgent;

	/**
	 * Creates a new observer for an agent.
	 * 
	 * @param agent
	 *            the agent that observes the environment.
	 */
	public Observer(ActiveAgent agent) {
		correspondingAgent = agent;
	}

	/**
	 * Finds the average success of a task type (hunter/gatherer) within range,
	 * which can be used to adjust internal success value(s)
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @return a value of the average success rate of the specified task within
	 *         range
	 */
	public double getAverageSuccessWithinRange(ThiefWorld world,
			Class<?> taskType) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag agentsNearby = world.map.getObjectsWithinDistance(myPosition,
				SimulationParameters.getAgentRange());

		// Find all agents with desired task
		Bag selectedAgents = new Bag();
		for (int i = 0; i < agentsNearby.size(); i++)
			if (agentsNearby.get(i).getClass() == taskType)
				selectedAgents.add(agentsNearby.get(i));

		// Sum up all the personal success rates
		Double totalSuccessRates = 0.0;
		for (int i = 0; i < selectedAgents.size(); i++) {
			ActiveAgent activeAgent = (ActiveAgent) selectedAgents.get(i);
			totalSuccessRates += activeAgent.successRates.getPersonalSuccess();
		}

		// Return the average success rate
		if (selectedAgents.size() != 0)
			return totalSuccessRates / selectedAgents.size();
		else
			return -1;
	}

	/**
	 * Searches for the food source with the highest quantity of food within
	 * range
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param foodSourceType
	 *            the type of food source which you are looking for.
	 * @return the fullest food source or null if there is none in the agent's
	 *         range.
	 */
	public FoodSource getBestFoodSourceWithinRange(ThiefWorld world,
			Class<?> foodSourceType) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);

		// follow the pheromone trail to a food source
		Bag agentsInRange = world.map.getObjectsWithinDistance(myPosition,
				SimulationParameters.getAgentRange());

		FoodSource bestFoodSource = null;

		// go through all food sources of the required type
		for (int i = 0; i < agentsInRange.size(); i++) {
			if (agentsInRange.get(i).getClass() == foodSourceType) {
				FoodSource foodSource = (FoodSource) agentsInRange.get(i);

				// if the food source is better, replace the best food source so
				// far
				if (foodSource.isActive())
					if (bestFoodSource == null)
						bestFoodSource = foodSource;
					else if (bestFoodSource.getFoodQuantiy() < foodSource
							.getFoodQuantiy())
						bestFoodSource = foodSource;

			}
		}
		return bestFoodSource;
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
	public FoodSource getClosestFoodSourceWithinRange(ThiefWorld world,
			Class<?> foodSourceType) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);

		// follow the pheromone trail to a food source
		Bag agentsInRange = world.map.getObjectsWithinDistance(myPosition,
				SimulationParameters.getAgentRange());

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
	 * Finds all pheromones within range
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @return a Bag of nearby pheromones
	 */
	public Bag getPheromonesWithinRange(ThiefWorld world) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag pheromonesCloseby = world.map.getObjectsWithinDistance(myPosition,
				SimulationParameters.getAgentRange());

		Bag selectedPheromones = new Bag();
		for (int i = 0; i < pheromonesCloseby.size(); i++) {
			if (pheromonesCloseby.get(i).getClass() == Pheromone.class) {
				Pheromone pheromone = (Pheromone) pheromonesCloseby.get(i);
				selectedPheromones.add(pheromone);
			}
		}

		return selectedPheromones;
	}

	/**
	 * Finds desired pheromones within range
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param pheromonesType
	 *            the type of pheromones which you are looking for.
	 * @return a Bag of nearby pheromones
	 */
	public Bag getPheromonesWithinRange(ThiefWorld world,
			PheromoneType pheromonesType) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag pheromonesCloseby = world.map.getObjectsWithinDistance(myPosition,
				SimulationParameters.getAgentRange());

		Bag selectedPheromones = new Bag();
		for (int i = 0; i < pheromonesCloseby.size(); i++) {
			if (pheromonesCloseby.get(i).getClass() == Pheromone.class) {
				Pheromone pheromone = (Pheromone) pheromonesCloseby.get(i);
				if (pheromone.getType() == pheromonesType)
					selectedPheromones.add(pheromone);
			}
		}

		return selectedPheromones;
	}

	/**
	 * Searches for the closest nest, no matter what the range is.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @return the closest nest in the world or null if there is none.
	 */
	public Nest searchForNest(ThiefWorld world) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag allAgents = world.map.getAllObjects();

		double closebyNestDistance = Double.MAX_VALUE;
		Nest closebyNest = null;

		for (int i = 0; i < allAgents.size(); i++) {
			if (allAgents.get(i).getClass() == Nest.class) {
				Nest nest = (Nest) allAgents.get(i);

				Double2D nestPosition = world.map.getObjectLocation(nest);
				if (myPosition.distance(nestPosition) < closebyNestDistance) {
					closebyNestDistance = myPosition.distance(nestPosition);
					closebyNest = nest;
				}
			}
		}

		return closebyNest;
	}

	/**
	 * Searches for a close by nest within a specified range that is owned by
	 * the agent's team.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @return the closest nest within range or null if there is none.
	 */
	public Nest searchForNestWithinRange(ThiefWorld world) {
		return searchForNestWithinRange(world, true);
	}

	/**
	 * Searches for a close by nest within a specified range. The nest's owner
	 * may be specified (owned by the agent's team or by other teams).
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @return the closest nest within range or null if there is none.
	 */
	public Nest searchForNestWithinRange(ThiefWorld world, boolean ownNest) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag closebyAgents = world.map.getObjectsWithinDistance(myPosition,
				SimulationParameters.getAgentRange());

		double closebyNestDistance = Double.MAX_VALUE;
		Nest closebyNest = null;

		for (int i = 0; i < closebyAgents.size(); i++) {
			if (closebyAgents.get(i).getClass() == Nest.class) {
				Nest nest = (Nest) closebyAgents.get(i);

				Double2D nestPosition = world.map.getObjectLocation(nest);
				if (myPosition.distance(nestPosition) < closebyNestDistance) {
					// check which team the nest belongs to

					if ((nest.getTeamNo() == correspondingAgent.getTeamNo() && ownNest)
							|| (nest.getTeamNo() != correspondingAgent
									.getTeamNo() && !ownNest)) {
						closebyNestDistance = myPosition.distance(nestPosition);
						closebyNest = nest;
					}
				}
			}
		}

		return closebyNest;
	}

	/**
	 * The observer is silent.
	 */
	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub

	}
}