package thiefworld.agents;

import sim.engine.SimState;

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

	/**
	 * The pheromone strength. This will eventually influence the number of time
	 * steps in which it completely decays.
	 */
	private double strength = 1.0;

	/**
	 * Creates a new unknown type pheromone drop using the default strength of
	 * one unit.
	 */
	public Pheromone() {
		this.type = PheromoneType.Unknown;
	}

	/**
	 * Creates a new specific type pheromone drop using the default strength of
	 * one unit.
	 * 
	 * @param type
	 *            the pheromone type.
	 */
	public Pheromone(PheromoneType type) {
		this.type = type;
	}

	/**
	 * Creates a new specific type pheromone using a specified stength.
	 * 
	 * @param strength
	 *            the pheromone strength.
	 * @param type
	 *            the pheromone type.
	 */
	public Pheromone(double strength, PheromoneType type) {
		this.strength = strength;
		this.type = type;
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

	@Override
	public void step(SimState arg0) {
		// do nothing
	}

	@Override
	public String toString() {
		return "pheromone strength: " + strength;
	}
}
