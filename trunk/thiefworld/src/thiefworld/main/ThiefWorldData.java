package thiefworld.main;

import sim.util.Bag;

/**
 * Contains the instantiations of the various agents in the system.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class ThiefWorldData {
	/**
	 * The number of children with which the simulation starts.
	 */
	public int children;
	/**
	 * The children present in the system.
	 */
	public Bag childrenBag;
	/**
	 * The number of fruit sources with which the simulation starts.
	 */
	public int fruitSources;
	/**
	 * The fruit sources present in the system.
	 */
	public Bag fruitSourcesBag;
	/**
	 * The number of gatherers with which the simulation starts.
	 */
	public int gatherers;
	/**
	 * The gatherers present in the system.
	 */
	public Bag gatherersBag;
	/**
	 * The number of hunters with which the simulation starts.
	 */
	public int hunters;
	/**
	 * The hunters present in the system.
	 */
	public Bag huntersBag;
	/**
	 * The number of meat sources with which the simulation starts.
	 */
	public int meatSources;
	/**
	 * The meat sources present in the system.
	 */
	public Bag meatSourcesBag;
	/**
	 * The number of nests present in the system
	 */
	public int nests;
	/**
	 * The nests present in the system.
	 */
	public Bag nestsBag;
	/**
	 * The number of protectors with which the simulation starts.
	 */
	public int protectors;
	/**
	 * The protectors present in the system.
	 */
	public Bag protectorsBag;
	/**
	 * The number of teams in the simulation.
	 */
	public int teams;
	/**
	 * The number of thieves with which the simulation starts.
	 */
	public int thieves;
	/**
	 * The thieves present in the system.
	 */
	public Bag thievesBag;

	/**
	 * Creates a new environment context containing the specified entities.
	 * @param children the number of children in the system.
	 * @param fruitSources the number of fruit sources in the system.
	 * @param gatherers the number of fruit gatherers in the system.
	 * @param hunters the number of hunters in the system.
	 * @param meatSources the number of meat sources in the system.
	 * @param nests the number of nests in the system.
	 * @param protectors the number of protectors in the system.
	 * @param teams the number of teams in the system.
	 * @param thieves the number of thieves in the system.
	 */
	public ThiefWorldData(int children, int fruitSources,
			int gatherers, int hunters,
			int meatSources, int nests,
			int protectors, int teams,
			int thieves) {
		this.children = children;
		this.childrenBag = new Bag();
		this.fruitSources = fruitSources;
		this.fruitSourcesBag = new Bag();
		this.gatherers = gatherers;
		this.gatherersBag = new Bag();
		this.hunters = hunters;
		this.huntersBag = new Bag();
		this.meatSources = meatSources;
		this.meatSourcesBag = new Bag();
		this.nests = nests;
		this.nestsBag = new Bag();
		this.protectors = protectors;
		this.protectorsBag = new Bag();
		this.teams = teams;
		this.thieves = thieves;
		this.thievesBag = new Bag();
	}
}