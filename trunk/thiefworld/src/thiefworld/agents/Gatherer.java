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

		// TODO check if a pheromone is not already in the same position. If so,
		// increase the pheromone strength in that position by the required
		// value (i.e. unit).

		// drop pheromone
		Pheromone pheromone = new Pheromone(PheromoneType.Gatherer);
		world.schedule.scheduleRepeating(pheromone);
		world.map.setObjectLocation(pheromone,
				world.map.getObjectLocation(this));
	}

}
