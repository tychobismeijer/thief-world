package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;

public class Gatherer extends ActiveAgent {
	private static int gathererNo = 0;

	public Gatherer() {
		// set name
		gathererNo++;
		this.setName("gatherer #" + gathererNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3915111858846207408L;

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		dropPheromone(world);
		act(world);
	}

	private void act(ThiefWorld world) {
		// check if the agent is returning food
		if (isReturningFood())
			// return food to the nest
			returnFood(world);
		else
			// search for food
			goAfterFood(world);
	}

	/**
	 * Tries to find and extract food from food sources within the agent's
	 * range.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	private void goAfterFood(ThiefWorld world) {
		FoodSource closestFoodSource = getClosestFoodSource(world,
				FruitSource.class);

		if (closestFoodSource != null) {
			examineFoodSource(world, closestFoodSource);
		} else {
			wonderAround(world);
		}
	}

	/**
	 * Wonders around in search of a fruit source guided by the pheromone trail.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	private void wonderAround(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	/**
	 * Makes a return trip to the nest to drop the food.
	 * 
	 * @param world
	 *            the {@link thiefworld.main.ThiefWorld ThiefWorld} reference.
	 */
	private void returnFood(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	private void dropPheromone(ThiefWorld world) {
		// TODO check if a pheromone is not already in the same position. If so,
		// increase the pheromone strength in that position by the required
		// value (i.e. unit).

		// drop pheromone
		Pheromone pheromone = new Pheromone(
				Pheromone.getDefaultPheromoneStrength(),
				PheromoneType.Gatherer, isReturningFood());
		world.schedule.scheduleRepeating(pheromone);
		world.map.setObjectLocation(pheromone,
				world.map.getObjectLocation(this));
	}
}
