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

	public Gatherer(ActiveAgent agent) {
		super(agent);

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

		decaySkills();

		dropPheromone(world);
		act(world);

		thinkAboutSwitchingJobs(world);
	}

	protected void act(ThiefWorld world) {
		// check if the agent is returning food
		if (isReturningFood())
			// return food to the nest
			returnFood(world, PheromoneType.Gatherer);
		else
			// search for fruit
			goAfterFood(world, FruitSource.class);
	}

	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		
	}
}
