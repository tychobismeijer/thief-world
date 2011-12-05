package thiefworld.agents;

import java.util.logging.Level;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Gatherer extends ActiveAgent {
	private static int gathererNo = 0;

	public Gatherer() {
		// set name
		gathererNo++;
		this.setName("gatherer #" + gathererNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3915111858846207408L;

	@Override
	public void step(SimState arg0) {
		dropPheromone(arg0);
		act(arg0);
	}

	private void act(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;
		Double2D myPosition = world.map.getObjectLocation(this);

		// check if the agent is returning food
		if (isReturningFood()) {
			// follow the pheromone trail to the nest

		} else {
			// follow the pheromone trail to a food source
			Bag agentsInRange = world.map.getObjectsWithinDistance(myPosition,
					ActiveAgent.getAgentRange());

			double closestFruitSourceDistance = Double.MAX_VALUE;
			FruitSource closestFruitSource = null;

			for (int i = 0; i < agentsInRange.size(); i++) {
				if (agentsInRange.get(i).getClass() == FruitSource.class) {
					FruitSource fruitSource = (FruitSource) agentsInRange
							.get(i);

					if (fruitSource.isActive()) {
						Double2D fruitSourcePosition = world.map
								.getObjectLocation(fruitSource);

						if (myPosition.distance(fruitSourcePosition) < closestFruitSourceDistance) {
							closestFruitSourceDistance = myPosition
									.distance(fruitSourcePosition);
							closestFruitSource = fruitSource;
						}
					}
				}
			}

			if (closestFruitSource != null) {
				if (closestFruitSourceDistance <= ActiveAgent.getActionRange()) {
					// agent can gather fruit

					// check how much food the agent can gather
					double availableFruit = closestFruitSource
							.getFruitQuantity();
					double maximumFruitToPick = this.getMaxAllowedFood()
							- this.getCarriedFood();

					double fruitToPick = 0.0;

					if (maximumFruitToPick > 0)
						fruitToPick = Math.min(availableFruit,
								maximumFruitToPick);

					if (fruitToPick > 0) {
						// pick fruit
						closestFruitSource.decreaseFruitQuantity(fruitToPick);
						this.increaseCarriedFood(fruitToPick);

						Logger log = Logger.getLogger(this.getName());
						log.log(Level.INFO, this.getName() + " picked up "
								+ fruitToPick + " fruit from "
								+ closestFruitSource.getName());
					}
				} else {
					MutableDouble2D movementTowardsFruitSource = new MutableDouble2D();
					Double2D fruitSourcePosition = world.map
							.getObjectLocation(closestFruitSource);

					movementTowardsFruitSource
							.addIn((fruitSourcePosition.getX() - myPosition
									.getX()) * 0.1,
									(fruitSourcePosition.getY() - myPosition
											.getY()) * 0.1);

					// add randomness
					movementTowardsFruitSource.addIn(new Double2D((Utilities
							.nextDouble() * 1.0 - 0.5) * 0.01, (Utilities
							.nextDouble() * 1.0 - 0.5) * 0.01));

					movementTowardsFruitSource.addIn(myPosition);
					world.map.setObjectLocation(this, new Double2D(
							movementTowardsFruitSource));
				}
			} else {
				// no fruit source in range

				// check which way the pheromone trail is the strongest
			}
		}
	}

	public boolean isReturningFood() {
		return this.getCarriedFood() == this.getMaxAllowedFood();
	}

	private void dropPheromone(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		// TODO check if a pheromone is not already in the same position. If so,
		// increase the pheromone strength in that position by the required
		// value (i.e. unit).

		// drop pheromone
		Pheromone pheromone = new Pheromone(
				Pheromone.getDefaultPheromoneStrength(), PheromoneType.Hunter,
				isReturningFood());
		world.schedule.scheduleRepeating(pheromone);
		world.map.setObjectLocation(pheromone,
				world.map.getObjectLocation(this));
	}
}
