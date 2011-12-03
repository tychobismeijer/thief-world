package thiefworld.main;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Bag;
import thiefworld.agents.Child;
import thiefworld.agents.FruitSource;
import thiefworld.agents.Gatherer;
import thiefworld.agents.Hunter;
import thiefworld.agents.MeatSource;
import thiefworld.agents.Nest;
import thiefworld.agents.Thief;
import thiefworld.util.Utilities;

/**
 * The world class for the thief-world simulation. It contains the starting
 * parameters of the simulation and the instances of the different agents
 * created for the simulation.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class ThiefWorld extends SimState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676316886795971516L;

	/**
	 * The map structure for the GUI.
	 */
	public Continuous2D map = new Continuous2D(1.0, 100, 100);

	/**
	 * The nests present in the system.
	 */
	public Bag nestsBag = new Bag();

	/**
	 * The number of nests present in the system
	 */
	private int nests = 1;

	/**
	 * Retrieves the nests present in the system.
	 * 
	 * @return the nests present in the system
	 */
	public int getNests() {
		return nests;
	}

	/**
	 * Sets the number of nests with which the simulation starts.
	 * 
	 * @param nests
	 *            the number of nests with which the simulation starts.
	 */
	public void setNests(int nests) {
		if (nests >= 0)
			this.nests = nests;
	}

	/**
	 * The hunters present in the system.
	 */
	public Bag huntersBag = new Bag();

	/**
	 * The number of hunters with which the simulation starts.
	 */
	private int hunters = 5;

	/**
	 * Retrieves the number of hunters with which the simulation starts.
	 * 
	 * @return the number of hunters with which the simulation starts.
	 */
	public int getHunters() {
		return hunters;
	}

	/**
	 * Sets the number of hunters with which the simulation starts.
	 * 
	 * @param hunters
	 *            the number of hunters with which the simulation starts.
	 */
	public void setHunters(int hunters) {
		if (hunters >= 0)
			this.hunters = hunters;
	}

	/**
	 * The gatherers present in the system.
	 */
	public Bag gatherersBag = new Bag();

	/**
	 * The number of gatherers with which the simulation starts.
	 */
	private int gatherers = 5;

	/**
	 * Retrieves the number of gatherers with which the simulation starts.
	 * 
	 * @return the number of gatherers with which the simulation starts.
	 */
	public int getGatherers() {
		return gatherers;
	}

	/**
	 * Sets the number of gatherers with which the simulation starts.
	 * 
	 * @param gatherers
	 *            the number of gatherers with which the simulation starts.
	 */
	public void setGatherers(int gatherers) {
		if (gatherers >= 0)
			this.gatherers = gatherers;
	}

	/**
	 * The thieves present in the system.
	 */
	public Bag thievesBag = new Bag();

	/**
	 * The number of thieves with which the simulation starts.
	 */
	private int thieves = 0;

	/**
	 * Retrieves the number of thieves with which the simulation starts.
	 * 
	 * @return the number of thieves with which the simulation starts.
	 */
	public int getThieves() {
		return thieves;
	}

	/**
	 * Sets the number of thieves present with which the simulation starts.
	 * 
	 * @param thieves
	 *            the number of thieves with which the simulation starts.
	 */
	public void setThieves(int thieves) {
		if (thieves >= 0)
			this.thieves = thieves;
	}

	/**
	 * The children present in the system.
	 */
	public Bag childrenBag = new Bag();

	/**
	 * The number of children with which the simulation starts.
	 */
	private int children = 0;

	/**
	 * Retrieves the number of children with which the simulation starts.
	 * 
	 * @return the number of children with which the simulation starts.
	 */
	public int getChildren() {
		return children;
	}

	/**
	 * Sets the number of children with which the simulation starts.
	 * 
	 * @param children
	 *            the number of children with which the simulation starts.
	 */
	public void setChildren(int children) {
		if (children >= 0)
			this.children = children;
	}

	/**
	 * The fruit sources present in the system.
	 */
	public Bag fruitSourcesBag = new Bag();

	/**
	 * The number of fruit sources with which the simulation starts.
	 */
	private int fruitSources = 5;

	/**
	 * Retrieves the number of fruit sources with which the simulation starts.
	 * 
	 * @return the number of fruit sources with which the simulation starts.
	 */
	public int getFruitSources() {
		return fruitSources;
	}

	/**
	 * Sets the number of fruit sources with which the simulation starts.
	 * 
	 * @param fruitSources
	 *            the number of fruit sources with which the simulation starts.
	 */
	public void setFruitSources(int fruitSources) {
		if (fruitSources >= 0)
			this.fruitSources = fruitSources;
	}

	/**
	 * The meat sources present in the system.
	 */
	public Bag meatSourcesBag = new Bag();

	/**
	 * The number of meat sources with which the simulation starts.
	 */
	private int meatSources = 5;

	/**
	 * Retrieves the number of meat sources with which the simulation starts.
	 * 
	 * @return the number of meat sources with which the simulation starts.
	 */
	public int getMeatSources() {
		return meatSources;
	}

	/**
	 * Sets the number of meat sources with which the simulation starts.
	 * 
	 * @param meatSources
	 *            the number of meat sources with which the simulation starts.
	 */
	public void setMeatSources(int meatSources) {
		if (meatSources >= 0)
			this.meatSources = meatSources;
	}

	/**
	 * The number of steps in which a pheromone completely decays. Default value
	 * is 1000 steps.
	 */
	private double pheromoneDecayRate = 1000;

	/**
	 * Retrieves the number of steps in which a pheromone completely decays.
	 * 
	 * @return the number of steps in which a pheromone completely decays.
	 */
	public double getPheromoneDecayRate() {
		return this.pheromoneDecayRate;
	}

	/**
	 * Sets the number of steps in which a pheromone completely decays.
	 * 
	 * @param pheromoneDecayRate
	 *            the number of steps in which a pheromone completely decays.
	 */
	public void setPheromoneDecayRate(double pheromoneDecayRate) {
		if (pheromoneDecayRate >= 0)
			this.pheromoneDecayRate = pheromoneDecayRate;
	}

	/**
	 * Creates a new simulation environment.
	 * 
	 * @param seed
	 *            initial random seed for the simulation random number
	 *            generator.
	 */
	public ThiefWorld(long seed) {
		super(seed);
	}

	@Override
	public void start() {
		super.start();

		initializeMap();
	}

	/**
	 * Adds the agents within the simulation environment at random locations.
	 */
	private void initializeMap() {
		map.clear();

		int maxWidth = (int) map.getWidth();
		int maxHeight = (int) map.getHeight();

		// add nests
		for (int i = 0; i < nests; i++) {
			// create nest
			Nest nest = new Nest();
			nestsBag.add(nest);

			// position nest
			map.setObjectLocation(nest,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(nest);
		}

		// add fruit sources
		for (int i = 0; i < fruitSources; i++) {
			// create fruit source
			FruitSource fruitSource = new FruitSource();
			fruitSourcesBag.add(fruitSource);

			// position fruit source
			map.setObjectLocation(fruitSource,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(fruitSource);
		}

		// add meat sources
		for (int i = 0; i < meatSources; i++) {
			// create meat source
			MeatSource meatSource = new MeatSource();
			meatSourcesBag.add(meatSource);

			// position fruit source
			map.setObjectLocation(meatSource,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(meatSource);
		}

		// add hunters
		for (int i = 0; i < hunters; i++) {
			// create hunter
			Hunter hunter = new Hunter();
			huntersBag.add(hunter);

			// position hunter
			map.setObjectLocation(hunter,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(hunter);
		}

		// add gatherers
		for (int i = 0; i < gatherers; i++) {
			// create gatherer
			Gatherer gatherer = new Gatherer();
			gatherersBag.add(gatherer);

			// position gatherer
			map.setObjectLocation(gatherer,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(gatherer);
		}

		// add thieves
		for (int i = 0; i < thieves; i++) {
			// create thief
			Thief thief = new Thief();
			thievesBag.add(thief);

			// position thief
			map.setObjectLocation(thief,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(thief);
		}

		// add children
		for (int i = 0; i < children; i++) {
			// create child
			Child child = new Child();
			childrenBag.add(child);

			// position child
			map.setObjectLocation(child,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			schedule.scheduleRepeating(child);
		}
	}

	/**
	 * Starer method for the simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		doLoop(ThiefWorld.class, args);
		System.exit(0);
	}

}
