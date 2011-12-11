package thiefworld.agents;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Gatherer extends ActiveAgent {
	private static int gathererNo = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3915111858846207408L;

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
		// check what other hunters and gatherers are doing
		if (getHuntingSuccess() >= Utilities.theta
				&& getGatheringSuccess() >= Utilities.theta) {
			// am I informed enough?
			stagnatingFor = 0;
			if (getPersonalSuccess() < getHuntingSuccess()
					&& getGatheringSuccess() < getHuntingSuccess()) {
				// do I have a reason to believe that it's better as something
				// else?
				double ratio = getGatheringSuccess() / getHuntingSuccess();

				if (ratio < getSwitchThreshold()) {
					double probability = Utilities.nextDouble(0, 1);
					if (probability > getSwitchThreshold())
						replace(world, Gatherer.class);
				}
			}
		} else if (getGatheringSuccess() < Utilities.theta) {
			stagnatingFor++;

			if (stagnatingFor > 100) {
				double probability = Utilities.nextDouble(0, 1);
				if (probability > getSwitchThreshold()){
					replace(world, Gatherer.class);
					stagnatingFor = 0;
				}
			}
		}
	}
}
