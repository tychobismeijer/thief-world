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

	public Hunter(ActiveAgent agent) {
		super(agent);

		hunterNo++;
		this.setName("hunter #" + hunterNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865632885952375111L;

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		decaySkills();

		dropPheromone(world);
		act(world);

		thinkAboutSwitchingJobs(world);
	}

	protected void act(ThiefWorld world) {
		// check if the agent is returning food
		if (isReturningFood())
			// return food to the nest
			returnFood(world, PheromoneType.Hunter);
		else
			// search for meat
			goAfterFood(world, MeatSource.class);
	}

	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		// check if it's better as a Gatherer
	}

}
