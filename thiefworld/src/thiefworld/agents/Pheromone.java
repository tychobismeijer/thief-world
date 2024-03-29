package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

/**
 * The pheromone trail agent. It consists of a single pheromone drop with a
 * certain strength and a certain type dependent on the agent that drops it.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Pheromone extends Agent {
	/**
	 * The strength of the pheromone when it is first placed.
	 */
	private static double defaultPheromoneStrength = 1.0;

	/**
	 * The number of cycles in which the pheromone completely decays.
	 */
	private static double pheromoneDecayRate = 50;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1180313912816310627L;

	/**
	 * Retrieves the pheromone strength when it is first placed.
	 * 
	 * @return the pheromone strength when it is first placed.
	 */
	public static double getDefaultPheromoneStrength() {
		return Pheromone.defaultPheromoneStrength;
	}

	/**
	 * Retrieves the number of cycles in which the pheromone completely decays.
	 * 
	 * @return the number of cycles in which the pheromone completely decays.
	 */
	public static double getPheromoneDecayRate() {
		return pheromoneDecayRate;
	}

	/**
	 * Sets the pheromone strength when it is first placed.
	 * 
	 * @param defaultPheromoneStrength
	 *            the pheromone strength when it is first placed.
	 */
	public static void setDefaultPheromoneStrength(
			double defaultPheromoneStrength) {
		if (defaultPheromoneStrength >= 0)
			Pheromone.defaultPheromoneStrength = defaultPheromoneStrength;
	}

	/**
	 * Sets the number of cycles in which the pheromone completely decays.
	 * 
	 * @param pheromoneDecayRate
	 *            the amount of cycles in which the pheromone completely decays.
	 */
	public static void setPheromoneDecayRate(double pheromoneDecayRate) {
		Pheromone.pheromoneDecayRate = pheromoneDecayRate;
	}

	/**
	 * Flag describing whether the pheromone was placed on the path from the
	 * nest towards a food source.
	 */
	private boolean comingFromNest;

	/**
	 * Flag describing whether the pheromone was placed on the path returning
	 * from the food source or going towards the food source.
	 */
	private boolean returningFromFoodSource;

	/**
	 * The pheromone strength. This will eventually influence the number of time
	 * steps in which it completely decays.
	 */
	private double strength = 1.0;

	/**
	 * The type of pheromone.
	 */
	private PheromoneType type;

	/**
	 * Creates a new unknown type pheromone drop using the default strength of
	 * one unit.
	 */
	public Pheromone() {
		this.setType(PheromoneType.Unknown);

		this.setReturningFromFoodSource(false);
		this.setComingFromNest(false);
	}

	/**
	 * Creates a new specific type pheromone using a specified stength. The trip
	 * direction is towards the food source.
	 * 
	 * @param strength
	 *            the pheromone strength.
	 * @param type
	 *            the pheromone type.
	 */
	public Pheromone(double strength, PheromoneType type) {
		this.type = type;
		this.strength = strength;

		this.returningFromFoodSource = false;
	}

	/**
	 * Creates a new specific type pheromone using a specified stength. The trip
	 * direction is specified.
	 * 
	 * @param strength
	 *            the strength of the pheromone.
	 * @param type
	 *            the type of the pheromone (corresponding to the agent that
	 *            dropped it).
	 * @param returningFromFoodSource
	 *            is the pheromone dropped by an agent returning from a food
	 *            source?
	 * @param returningFromNest
	 *            is the pheromone dropped by an agent in search of food?
	 */
	public Pheromone(double strength, PheromoneType type,
			boolean returningFromFoodSource, boolean returningFromNest) {
		this.setStrength(strength);
		this.setType(type);

		this.setReturningFromFoodSource(returningFromFoodSource);
		this.setComingFromNest(returningFromNest);
	}

	/**
	 * Creates a new specific type pheromone drop using the default strength of
	 * one unit. The trip direction is towards the food source.
	 * 
	 * @param type
	 *            the pheromone type.
	 */
	public Pheromone(PheromoneType type) {
		this.setType(type);

		this.setReturningFromFoodSource(false);
		this.setComingFromNest(false);
	}

	/**
	 * Decrements the strength of the pheromone by a specific value.
	 * 
	 * @param decrement
	 *            the pheromone strength decrement.
	 */
	public void decrementStrength(double decrement) {
		this.strength -= decrement;
	}

	/**
	 * Retrieves the current pheromone strength.
	 * 
	 * @return the current pheromone strength.
	 */
	public double getStrength() {
		return strength;
	}

	/**
	 * Retrieves the type of pheromone.
	 * 
	 * @return the type of pheromone.
	 */
	public PheromoneType getType() {
		return type;
	}

	/**
	 * Increments the strength of the pheromone with a specific value.
	 * 
	 * @param increment
	 *            the pheromone strength increment.
	 */
	public void incrementStrength(double increment) {
		this.strength += increment;
	}

	/**
	 * Checks if the pheromone has been dropped by an agent that was in search
	 * of food.
	 * 
	 * @return true if the pheromone was dropped by an agent in search of food,
	 *         false otherwise.
	 */
	public boolean isComingFromNest() {
		return comingFromNest;
	}

	/**
	 * Checks if the pheromone was dropped on the way to the nest or not.
	 * 
	 * @return true if the pheromone was dropped on the way back from a food
	 *         source and false if it was dropped on the way from the nest to
	 *         the food source.
	 */
	public boolean isReturningFromFoodSource() {
		return returningFromFoodSource;
	}

	/**
	 * Sets whether or not the pheromone was dropped by an agent in search of
	 * food.
	 * 
	 * @param comingFromNest
	 *            true if the pheromone was dropped by an agent in search of
	 *            food, false otherwise.
	 */
	public void setComingFromNest(boolean comingFromNest) {
		this.comingFromNest = comingFromNest;
	}

	/**
	 * Sets if the pheromone was dropped on the way to the nest or not.
	 * 
	 * @param returningFromFoodSource
	 *            true if the pheromone was dropped on the way back from a food
	 *            source and false if it was dropped on the way from the nest to
	 *            the food source.
	 */
	public void setReturningFromFoodSource(boolean returningFromFoodSource) {
		this.returningFromFoodSource = returningFromFoodSource;
	}

	/**
	 * Sets the strength of the pheromone.
	 * 
	 * @param strength
	 *            the strength of the pheromone.
	 */
	public void setStrength(double strength) {
		this.strength = strength;
	}

	/**
	 * Sets the type of pheromone.
	 * 
	 * @param type
	 *            the type of pheromone.
	 */
	public void setType(PheromoneType type) {
		this.type = type;
	}

	/**
	 * The pheromone slowly decays with each cycle.
	 */
	@Override
	public void step(SimState arg0) {
		if (this.getStrength() <= Utilities.theta) {
			this.setStrength(0.0);

			// remove pheromone from map
			ThiefWorld world = (ThiefWorld) arg0;
			world.map.remove(this);

			// stop pheromone from firing
			if (this.stoppable != null) {
				stoppable.stop();
			}
		}

		if (Pheromone.getPheromoneDecayRate() >= 0 && this.getStrength() > 0) {
			// decrease the pheromone strength
			this.strength -= 1.0 / pheromoneDecayRate;
		}
	}

	/**
	 * Returns the pheromone's current strength.
	 */
	@Override
	public String toString() {
		return "pheromone strength: " + strength;
	}
}
