package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import sim.util.Bag;
import sim.util.Double2D;
import thiefworld.agents.misc.SimulationParameters;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

/**
 * Describes an agent that steals food from other nests.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Thief extends ActiveAgent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -660441960033117671L;

	/**
	 * The number of thieves that were created. Used for agent naming purposes.
	 */
	private static int thiefNo = 0;

	/**
	 * Creates a new thief.
	 */
	public Thief() {
		// set name
		thiefNo++;
		this.setName("thief #" + thiefNo);

		// set task switching threshold
		this.setSwitchThreshold(Utilities.nextDouble());
	}

	@Override
	protected void act(ThiefWorld world) {
		if (isReturningFood()) {
			// return food to the nest
			returnFood(world, PheromoneType.Thief);
		} else {
			lookForNextHit(world);
		}
	}

	private void lookForNextHit(ThiefWorld world) {
		Nest closebyEnemyNest = personalObserver.searchForNestWithinRange(
				world, false);

		if (closebyEnemyNest != null) {
			// an enemy nest is close by
			if (closebyEnemyNest.getFoodQuantity() > 0) {
				// worth checking out
				Double2D nestPosition = world.map
						.getObjectLocation(closebyEnemyNest);
				Double2D myPosition = world.map.getObjectLocation(this);

				if (nestPosition.distance(myPosition) <= SimulationParameters
						.getActionRange()) {
					// nest in action range; rob the nest
					robNest(closebyEnemyNest);
				} else {
					// go towards the nest
					moveTowardsNest(world, closebyEnemyNest);
				}
			} else {
				Bag pheromoneTrail = filterPheromones(
						personalObserver.getPheromonesWithinRange(world,
								PheromoneType.Thief), false);
				if (pheromoneTrail != null && pheromoneTrail.size() > 0) {
					followPheromoneTrail(world, pheromoneTrail);
				} else {
					wonderAround(world);
				}
			}
		} else {
			Bag pheromoneTrail = filterPheromones(
					personalObserver.getPheromonesWithinRange(world,
							PheromoneType.Thief), false);
			if (pheromoneTrail != null && pheromoneTrail.size() > 0) {
				followPheromoneTrail(world, pheromoneTrail);
			} else {
				wonderAround(world);
			}
		}
	}

	private void robNest(Nest nest) {
		// check how much food the agent can steal
		double availableFood = nest.getFoodQuantity();
		double maximumFoodToExtract = inventory.getMaxAllowedFood()
				- inventory.getCarriedFood();

		double foodToExtract = 0.0;

		if (maximumFoodToExtract > 0)
			foodToExtract = Math.min(availableFood, maximumFoodToExtract);

		if (foodToExtract > 0) {
			// extract food
			if (nest.getFruitQuantity() >= nest.getMeatQuantity()) {
				foodToExtract = Math.min(nest.getFruitQuantity(),
						maximumFoodToExtract);
				nest.decreaseFruitQuantity(foodToExtract);
				this.increaseCarriedFruit(foodToExtract);
			} else {
				foodToExtract = Math.min(nest.getMeatQuantity(),
						maximumFoodToExtract);
				nest.decreaseMeatQuantity(foodToExtract);
				this.increaseCarriedMeat(foodToExtract);
			}

			// log event
			Logger log = Logger.getLogger(this.getName());
			log.log(Level.INFO, this.getName() + " stole " + foodToExtract
					+ " food from " + nest.getName());
		}
	}

	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void thinkAboutSwitchingJobsSimple(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

}
