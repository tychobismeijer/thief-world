package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;

public class Hunter extends ActiveAgent {
	private static int hunterNo = 0;

	public Hunter() {
		// set name
		hunterNo++;
		this.setName("hunter #" + hunterNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865632885952375111L;

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld)arg0;
		
		// drop pheromone
		Pheromone pheromone = new Pheromone(PheromoneType.Hunter);
		world.schedule.scheduleRepeating(pheromone);
	}

}
