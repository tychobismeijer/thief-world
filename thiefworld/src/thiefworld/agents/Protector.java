package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Protector extends ActiveAgent {
	private static int protectorNo = 0;

	public Protector() {
		// set name
		protectorNo++;
		this.setName("protector #" + protectorNo);

		// set task switching threshold
		this.setSwitchThreshold(Utilities.nextDouble());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 223400888051741701L;

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		// drop pheromone
		Pheromone pheromone = new Pheromone(PheromoneType.Protector);
		world.schedule.scheduleRepeating(pheromone);
	}

	@Override
	protected void act(ThiefWorld world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

}
