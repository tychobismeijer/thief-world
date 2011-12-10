package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Hunter extends ActiveAgent {
	private static int hunterNo = 0;
	private static double weight = 1.0; //0.5;

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
		Logger log = Logger.getLogger(getName());
		double probability = ActiveAgent.switchProb;
		
		//Gather information about success of other agents
		double currentHuntingSuccess = this.personalObserver.getAverageSuccessWithinRange(world, Hunter.class);
		double currentGatheringSuccess = this.personalObserver.getAverageSuccessWithinRange(world, Gatherer.class);
		
		//Only update information if there are other agents in the neighborhood
		if(currentHuntingSuccess != -1)
			this.huntingSuccess += 0.5 * (currentHuntingSuccess - huntingSuccess);
		if(currentGatheringSuccess != -1)
			this.gatheringSuccess += 0.5 * (currentGatheringSuccess - gatheringSuccess);
		
		//Check whether it is worth it to switch
		/*if agent performs less than average on hunting and gathering is more successful than hunting
		 * add the difference in performance to the switch probability
		*/
		if(this.personalSuccess < this.huntingSuccess && this.gatheringSuccess > this.huntingSuccess){
			probability += weight * (huntingSuccess - this.personalSuccess);
		}
		
		// Regardless of performance, there is always a probability of switching
		if(probability - this.huntingSkill > (this.switchThreshold + Utilities.nextDouble(-0.1, 0.1)) ) {
			log.log(Level.INFO,
					this.getName() + " is switching from Hunter to Gatherer");
			this.replace(world, Gatherer.class);
		}
	}

}
