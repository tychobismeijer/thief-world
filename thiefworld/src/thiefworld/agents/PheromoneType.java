package thiefworld.agents;

/**
 * The type of pheromone left behind, dependent on the agent that leaves it.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public enum PheromoneType {
	/**
	 * A child dropped the pheromone.
	 */
	Child, 
	
	/**
	 * A fruit gatherer dropped the pheromone.
	 */
	Gatherer,
	
	/**
	 * A hunter dropped the pheromone.
	 */
	Hunter, 
	
	/**
	 * A nest guardian dropped the pheromone.
	 */
	Protector, 
	
	/**
	 * A thief dropped the pheromone.
	 */
	Thief, 
	
	/**
	 * An unknown agent dropped the pheromone.
	 */
	Unknown
}
