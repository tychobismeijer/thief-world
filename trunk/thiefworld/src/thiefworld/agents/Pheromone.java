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
	 * 
	 */
	private static final long serialVersionUID = -1180313912816310627L;

	private static double defaultPheromoneStrength = 1.0;

	public static double getDefaultPheromoneStrength() {
		return Pheromone.defaultPheromoneStrength;
	}

	public static void setDefaultPheromoneStrength(
			double defaultPheromoneStrength) {
		if (defaultPheromoneStrength >= 0)
			Pheromone.defaultPheromoneStrength = defaultPheromoneStrength;
	}

	private static double pheromoneDecayRate = 1000;

	public static double getPheromoneDecayRate() {
		return pheromoneDecayRate;
	}

	public static void setPheromoneDecayRate(double pheromoneDecayRate) {
		Pheromone.pheromoneDecayRate = pheromoneDecayRate;
	}

	/**
	 * The pheromone strength. This will eventually influence the number of time
	 * steps in which it completely decays.
	 */
	private double strength = 1.0;

	/**
	 * Flag describing whether the pheromone was placed on the path returning
	 * from the food source or going towards the food source.
	 */
	private boolean returning;

	/**
	 * Creates a new unknown type pheromone drop using the default strength of
	 * one unit.
	 */
	public Pheromone() {
		this.type = PheromoneType.Unknown;
		this.returning = false;
	}

	/**
	 * Creates a new specific type pheromone drop using the default strength of
	 * one unit. The trip direction is towards the food source.
	 * 
	 * @param type
	 *            the pheromone type.
	 */
	public Pheromone(PheromoneType type) {
		this.type = type;
		this.returning = false;
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
		this.strength = strength;
		this.type = type;
		this.returning = false;
	}

	/**
	 * Creates a new specific type pheromone using a specified stength. The trip
	 * direction is specified.
	 * 
	 * @param strength
	 * @param type
	 * @param returning
	 */
	public Pheromone(double strength, PheromoneType type, boolean returning) {
		this.strength = strength;
		this.type = type;
		this.returning = returning;
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
	 * Sets the strength of the pheromone.
	 * 
	 * @param strength
	 *            the strength of the pheromone.
	 */
	public void setStrength(double strength) {
		this.strength = strength;
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
	 * Decrements the strength of the pheromone by a specific value.
	 * 
	 * @param decrement
	 *            the pheromone strength decrement.
	 */
	public void decrementStrength(double decrement) {
		this.strength -= decrement;
	}

	/**
	 * The type of pheromone.
	 */
	private PheromoneType type;

	/**
	 * Retrieves the type of pheromone.
	 * 
	 * @return the type of pheromone.
	 */
	public PheromoneType getType() {
		return type;
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
	 * Checks if the pheromone was dropped on the way to the nest or not.
	 * 
	 * @return true if the pheromone was dropped on the way back from a food
	 *         source and false if it was dropped on the way from the nest to
	 *         the food source.
	 */
	public boolean isReturning() {
		return returning;
	}

	/**
	 * Sets if the pheromone was dropped on the way to the nest or not.
	 * 
	 * @param returning
	 *            true if the pheromone was dropped on the way back from a food
	 *            source and false if it was dropped on the way from the nest to
	 *            the food source.
	 */
	public void setReturning(boolean returning) {
		this.returning = returning;
	}

	@Override
	public void step(SimState arg0) {
		if (this.getStrength() <= Utilities.theta){
			this.setStrength(0.0);
			
			// remove pheromone from map
			ThiefWorld world = (ThiefWorld) arg0;
			world.map.remove(this);
			
			// stop pheromone from firing
			if(this.stoppable != null){
				stoppable.stop();
			}
		}

		if (Pheromone.getPheromoneDecayRate() >= 0 && this.getStrength() > 0) {
			// decrease the pheromone strength
			this.strength -= 1.0 / pheromoneDecayRate;
		}
	}

	@Override
	public String toString() {
		return "pheromone strength: " + strength;
	}
}
