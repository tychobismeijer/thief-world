package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import thiefworld.agents.misc.SimulationParameters;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

/**
 * A gatherer that collects food from a fruit source.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Gatherer extends ActiveAgent {
	/**
	 * The number of fruit gatherers created.
	 */
	private static int gathererNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3915111858846207408L;

	/**
	 * Creates a new fruit gatherer.
	 */
	public Gatherer() {
		// set name
		gathererNo++;
		this.setName("gatherer #" + gathererNo);
	}

	/**
	 * Morphs an existing agent into a fruit gatherer.
	 * 
	 * @param agent
	 *            the agent that is morphed.
	 */
	public Gatherer(ActiveAgent agent) {
		super(agent);

		gathererNo++;
		this.setName("gatherer #" + gathererNo);
	}

	/**
	 * Tries to find food or the way back to the nest if it already carries
	 * fruit.
	 */
	protected void act(ThiefWorld world) {
		// check if the agent is returning food
		if (isReturningFood())
			// return food to the nest
			returnFood(world, PheromoneType.Gatherer);
		else
			// search for fruit
			goAfterFood(world, FruitSource.class);
	}

	/**
	 * Checks to see if it still pays off to be a fruit gatherer.
	 */
	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		Logger log = Logger.getLogger(getName());
		double probability = SimulationParameters.getSwitchProbability();
		// System.out.println("initial switch probability: "+ probability);

		// Gather information about success of other agents
		double currentHuntingSuccess = this.personalObserver
				.getAverageSuccessWithinRange(world, Hunter.class);
		double currentGatheringSuccess = this.personalObserver
				.getAverageSuccessWithinRange(world, Gatherer.class);

		if (currentHuntingSuccess != -1)
			this.successRates.setHuntingSuccess(this.successRates
					.getHuntingSuccess()
					+ (0.7 * (currentHuntingSuccess - successRates
							.getHuntingSuccess())));
		if (currentGatheringSuccess != -1)
			this.successRates.setGatheringSuccess(this.successRates
					.getGatheringSuccess()
					+ (0.7 * (currentGatheringSuccess - successRates
							.getGatheringSuccess())));

		System.out.println("New value for internal hunting succes: "
				+ successRates.getHuntingSuccess());
		System.out.println("New value for internal Gathering succes: "
				+ successRates.getGatheringSuccess());
		System.out.println("personal succes: "
				+ successRates.getPersonalSuccess());

		/*
		 * Check whether it is worth it to switch
		 * 
		 * If the agent performs less than average on hunting and gathering is
		 * more successful than hunting add the difference in performance to the
		 * switch probability
		 */
		if (this.successRates.getPersonalSuccess() <= this.successRates
				.getGatheringSuccess()
				&& this.successRates.getGatheringSuccess() < this.successRates
						.getHuntingSuccess()) {
			if (successRates.getGatheringSuccess() != 0)
				probability += (successRates.getHuntingSuccess() / successRates
						.getGatheringSuccess());
			else
				probability += successRates.getHuntingSuccess(); // just in case
		}

		// System.out.println("new switch probability: "+ probability);
		double threshold = 0.05 + Utilities.nextDouble(-0.05, 0.05);// this.switchThreshold
																	// +
																	// Utilities.nextDouble(-0.05,
																	// 0.05);
		System.out.println("switch depends on:\n " + probability + " - "
				+ data.getGatheringSkill() + "being larger than " + threshold);

		// Regardless of performance, there is always a probability of switching
		if (probability - this.data.getGatheringSkill() > threshold
				|| Utilities.nextDouble() < 0.1) {
			System.out.println("SWITCHING");
			log.log(Level.INFO, this.getName()
					+ " is switching from Gatherer to Hunter");
			this.replace(world, Hunter.class);
		}
	}

	/**
	 * Simplified version of the role switching process.
	 */
	protected void thinkAboutSwitchingJobsSimple(ThiefWorld world) {
		Logger log = Logger.getLogger(getName());
		// System.out.println("initial switch probability: "+ probability);

		// Gather information about success of other agents
		double currentHuntingSuccess = this.personalObserver
				.getAverageSuccessWithinRange(world, Hunter.class);
		double currentGatheringSuccess = this.personalObserver
				.getAverageSuccessWithinRange(world, Gatherer.class);

		if (currentHuntingSuccess != -1)
			this.successRates.setHuntingSuccess(this.successRates
					.getHuntingSuccess()
					+ (0.7 * (currentHuntingSuccess - successRates
							.getHuntingSuccess())));
		if (currentGatheringSuccess != -1)
			this.successRates.setGatheringSuccess(this.successRates
					.getGatheringSuccess()
					+ (0.7 * (currentGatheringSuccess - successRates
							.getGatheringSuccess())));

		if (this.successRates.getGatheringSuccess() < this.successRates
				.getHuntingSuccess()
				&& successRates.getGatheringSuccess() < Utilities.nextDouble(0,
						0.1)) {
			log.log(Level.INFO, this.getName()
					+ " is switching from Gatherer to Hunter");
			this.replace(world, Hunter.class);
		}

	}
}
