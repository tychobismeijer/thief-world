package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Gatherer extends ActiveAgent {
	private static int gathererNo = 0;
	private static double weight = 1.0;
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
		Logger log = Logger.getLogger(getName());
		double probability = ActiveAgent.switchProb;
		//System.out.println("initial switch probability: "+ probability);
		
		//Gather information about success of other agents
		double currentHuntingSuccess = this.personalObserver.getAverageSuccessWithinRange(world, Hunter.class);
		double currentGatheringSuccess = this.personalObserver.getAverageSuccessWithinRange(world, Gatherer.class);
		//System.out.println("Current hunting succes: "+ currentHuntingSuccess);
		//System.out.println("Current Gathering succes: "+ currentGatheringSuccess);
		//Only update information if there are other agents in the neighborhood
		if(currentHuntingSuccess != -1)
			this.huntingSuccess += 0.7 * (currentHuntingSuccess - huntingSuccess);
		if(currentGatheringSuccess != -1)
			this.gatheringSuccess += 0.7 * (currentGatheringSuccess - gatheringSuccess);
		
		System.out.println("New value for internal hunting succes: "+ huntingSuccess);
		System.out.println("New value for internal Gathering succes: "+ gatheringSuccess);
		System.out.println("personal succes: "+ personalSuccess);
		//Check whether it is worth it to switch
		/*if agent performs less than average on hunting and gathering is more successful than hunting
		 * add the difference in performance to the switch probability
		*/
		if(this.personalSuccess <= this.gatheringSuccess && this.gatheringSuccess < this.huntingSuccess){
			probability += weight * (huntingSuccess - gatheringSuccess);
		}
		
		//System.out.println("new switch probability: "+ probability);
		double threshold = 0.05 + Utilities.nextDouble(-0.05, 0.05);//this.switchThreshold + Utilities.nextDouble(-0.05, 0.05);
		System.out.println("switch depends on:\n "+ (probability - gatheringSkill) + "being larger than "
							+ threshold);
		
		// Regardless of performance, there is always a probability of switching
		if(probability - 0.5*this.gatheringSkill > (threshold) ) {
			System.out.println("SWITCHING");
			log.log(Level.INFO,
					this.getName() + " is switching from Gatherer to Hunter");
			this.replace(world, Hunter.class);
		}
		
		
		/*
		 *  Logger log = Logger.getLogger(getName());

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
		//
		if(this.personalSuccess < this.gatheringSuccess && this.gatheringSuccess < this.huntingSuccess){
				probability += weight * (this.gatheringSuccess - this.personalSuccess);
				System.out.println("The conditions are right for switching..");
		}
		
		//Regardless of performance, there is always a probability of switching
		if(probability - this.gatheringSkill > (this.switchThreshold + Utilities.nextDouble(-0.1, 0.1)) ) {
			log.log(Level.INFO,
					this.getName() + " is switching from Gatherer to Hunter");
			System.out.println("The agent is actually switching!!!");
			this.replace(world, Hunter.class);
		}
		*/
	}
	
	protected void thinkAboutSwitchingJobsSimple(ThiefWorld world) {
		Logger log = Logger.getLogger(getName());
		//System.out.println("initial switch probability: "+ probability);
		
		//Gather information about success of other agents
		double currentHuntingSuccess = this.personalObserver.getAverageSuccessWithinRange(world, Hunter.class);
		double currentGatheringSuccess = this.personalObserver.getAverageSuccessWithinRange(world, Gatherer.class);
	
		if(currentHuntingSuccess != -1)
			this.huntingSuccess += 0.7 * (currentHuntingSuccess - huntingSuccess);
		if(currentGatheringSuccess != -1)
			this.gatheringSuccess += 0.7 * (currentGatheringSuccess - gatheringSuccess);
		
		if(this.gatheringSuccess < this.huntingSuccess || gatheringSuccess < Utilities.nextDouble(0,0.1)){
			log.log(Level.INFO, this.getName() + " is switching from Gatherer to Hunter");
			this.replace(world, Hunter.class);
		}
		
	}
}
