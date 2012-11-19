package thiefworld.main;

import sim.util.Bag;

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

	public ThiefWorldData(int children, Bag childrenBag, int fruitSources,
			Bag fruitSourcesBag, int gatherers, Bag gatherersBag, int hunters,
			Bag huntersBag, int meatSources, Bag meatSourcesBag, int nests,
			Bag nestsBag, int protectors, Bag protectorsBag, int teams,
			int thieves, Bag thievesBag) {
		this.children = children;
		this.childrenBag = childrenBag;
		this.fruitSources = fruitSources;
		this.fruitSourcesBag = fruitSourcesBag;
		this.gatherers = gatherers;
		this.gatherersBag = gatherersBag;
		this.hunters = hunters;
		this.huntersBag = huntersBag;
		this.meatSources = meatSources;
		this.meatSourcesBag = meatSourcesBag;
		this.nests = nests;
		this.nestsBag = nestsBag;
		this.protectors = protectors;
		this.protectorsBag = protectorsBag;
		this.teams = teams;
		this.thieves = thieves;
		this.thievesBag = thievesBag;
	}
}