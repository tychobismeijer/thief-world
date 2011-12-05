package thiefworld.agents;

import sim.engine.SimState;
import sim.engine.Stoppable;
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
			returnFood(world, PheromoneType.Gatherer);
		else
			// search for fruit
			goAfterFood(world, FruitSource.class);
	}

	private void dropPheromone(ThiefWorld world) {
		// TODO check if a pheromone is not already in the same position. If so,
		// increase the pheromone strength in that position by the required
		// value (i.e. unit).

		// drop pheromone
		Pheromone pheromone = new Pheromone(
				Pheromone.getDefaultPheromoneStrength(),
				PheromoneType.Gatherer, isReturningFood());
		Stoppable stoppable = world.schedule.scheduleRepeating(pheromone);
		pheromone.stoppable = stoppable;

		world.map.setObjectLocation(pheromone,
				world.map.getObjectLocation(this));
	}
}
