package thiefworld.agents;

import sim.engine.SimState;
import sim.engine.Stoppable;
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
	}

	private void act(ThiefWorld world) {
		// check if the agent is returning food
		if (isReturningFood())
			// return food to the nest
			returnFood(world, PheromoneType.Hunter);
		else
			// search for meat
			goAfterFood(world, MeatSource.class);
	}

	private void dropPheromone(ThiefWorld world) {
		// TODO check if a pheromone is not already in the same position. If so,
		// increase the pheromone strength in that position by the required
		// value (i.e. unit).

		// drop pheromone
		Pheromone pheromone = new Pheromone(
				Pheromone.getDefaultPheromoneStrength(), PheromoneType.Hunter,
				isReturningFood(), !isReturningFood());
		Stoppable stoppable = world.schedule.scheduleRepeating(pheromone);
		pheromone.stoppable = stoppable;

		world.map.setObjectLocation(pheromone,
				world.map.getObjectLocation(this));
	}

}
