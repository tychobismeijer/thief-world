package thiefworld.agents;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Hunter extends ActiveAgent {
	private static int hunterNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865632885952375111L;

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
	/*
	 * Decide whether or not to switch tasks
	 */
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		// check what other hunters and gatherers are doing
		if (getHuntingSuccess() >= Utilities.theta
				&& getGatheringSuccess() >= Utilities.theta) {
			stagnatingFor = 0;
			// am I informed enough?
			if (getPersonalSuccess() < getGatheringSuccess()
					&& getHuntingSuccess() < getGatheringSuccess()) {
				// do I have a reason to believe that it's better as something
				// else?
				double ratio = getHuntingSuccess() / getGatheringSuccess();

				if (ratio < getSwitchThreshold()) {
					double probability = Utilities.nextDouble(0, 1);
					if (probability > getSwitchThreshold())
						replace(world, Gatherer.class);
				}
			}
		} else if (getHuntingSuccess() < Utilities.theta) {
			stagnatingFor++;

			if (stagnatingFor > 100) {
				double probability = Utilities.nextDouble(0, 1);
				if (probability > getSwitchThreshold()) {
					replace(world, Gatherer.class);
					stagnatingFor = 0;
				}
			}
		}
	}

}
