package thiefworld.agents;

import sim.engine.SimState;
import sim.util.Bag;
import sim.util.Double2D;
import thiefworld.main.ThiefWorld;

public class Observer extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2220784702275759607L;
	private ActiveAgent correspondingAgent;
	
	public Observer(ActiveAgent agent){
		correspondingAgent = agent;
	}

	/**
	 * Searches for a close by nest within a specified range.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param range
	 *            the max distance to the agent at which the nest is searched for.
	 * @return the closest nest around the nest or null if there is none.
	 */	
	protected Nest searchForNest(ThiefWorld world, double range) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag closebyAgents = world.map.getObjectsWithinDistance(myPosition,
				range);

		double closebyNestDistance = Double.MAX_VALUE;
		Nest closebyNest = null;

		for (int i = 0; i < closebyAgents.size(); i++) {
			if (closebyAgents.get(i).getClass() == Nest.class) {
				Nest nest = (Nest) closebyAgents.get(i);

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
	 * Check to see if there is a close by food source and if there are
	 * multiple, it will choose the closest to the current agent's position.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param range
	 * 			  the max distance to the agent at which the pheromones are searched for.
	 * @return a Bag of nearby pheromones
	 */
	protected Bag getNearbyPheromones(ThiefWorld world, double range,
			PheromoneType pheromonesType) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);
		Bag pheromonesCloseby = world.map.getObjectsWithinDistance(myPosition,
				range);

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
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);

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
	
	//NOT DONE
	/**
	 * Searches for the food source with the highest quantity of food within range
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 * @param foodSourceType
	 *            the type of food source which you are looking for.
	 * @return the closest food source or null if there is none in the agent's
	 *         range.
	 */
	protected FoodSource getBestFoodSource(ThiefWorld world,
			Class<?> foodSourceType) {
		Double2D myPosition = world.map.getObjectLocation(correspondingAgent);

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
	
	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub
		
	}
}

//Unused code:

//private static int observerNo = 0;
//
//public Observer(){
//	observerNo++;
//	this.setName("observer #" + observerNo);
//}
