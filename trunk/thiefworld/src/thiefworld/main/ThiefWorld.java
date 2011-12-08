package thiefworld.main;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.continuous.Continuous2D;
import sim.util.Bag;
import thiefworld.agents.ActiveAgent;
import thiefworld.agents.Child;
import thiefworld.agents.FruitSource;
import thiefworld.agents.Gatherer;
import thiefworld.agents.Hunter;
import thiefworld.agents.MeatSource;
import thiefworld.agents.Nest;
import thiefworld.agents.Pheromone;
import thiefworld.agents.Protector;
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
	 * Retrieves the total amount of fruit available in the fruit sources.
	 * 
	 * @return the total amount of fruit available in the fruit sources.
	 */
	public double getOverallAvailableFruit() {
		double overallAvailableFruit = 0.0;

		for (int i = 0; i < this.fruitSourcesBag.size(); i++) {
			FruitSource fruitSource = (FruitSource) fruitSourcesBag.get(i);

			if (fruitSource.isActive())
				overallAvailableFruit += fruitSource.getFruitQuantity();
		}

		return overallAvailableFruit;
	}

	/**
	 * Retrieves the total amount of meat available in the meat sources.
	 * 
	 * @return the total amount of meat available in the meat sources.
	 */
	public double getOverallAvailableMeat() {
		double overallAvailableMeat = 0.0;

		for (int i = 0; i < this.meatSourcesBag.size(); i++) {
			MeatSource meatSource = (MeatSource) meatSourcesBag.get(i);

			if (meatSource.isActive())
				overallAvailableMeat += meatSource.getMeatQuantity();
		}

		return overallAvailableMeat;
	}

	/**
	 * Retrieves the total amount of fruit stored in the nests.
	 * 
	 * @return the total amount of fruit stored in the nests.
	 */
	public double getStoredFruit() {
		double storedFruit = 0.0;

		for (int i = 0; i < this.nestsBag.size(); i++) {
			Nest nest = (Nest) nestsBag.get(i);

			storedFruit += nest.getFruitQuantity();
		}

		return storedFruit;
	}

	/**
	 * Retrieves the total amount of meat stored in the nests.
	 * 
	 * @return the total amount of fruit stored in the nests.
	 */
	public double getStoredMeat() {
		double storedMeat = 0.0;

		for (int i = 0; i < this.nestsBag.size(); i++) {
			Nest nest = (Nest) nestsBag.get(i);

			storedMeat += nest.getMeatQuantity();
		}

		return storedMeat;
	}

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
	private int hunters = 50;

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
	private int gatherers = 50;

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
	 * The protectors present in the system.
	 */
	public Bag protectorsBag = new Bag();

	/**
	 * The number of protectors with which the simulation starts.
	 */
	private int protectors = 0;

	/**
	 * Retrieves the number of protectors with which the simulation starts.
	 * 
	 * @return the number of protectors with which the simulation starts.
	 */
	public int getProtectors() {
		return protectors;
	}

	/**
	 * Sets the number of protectors with which the simulation starts.
	 * 
	 * @param protectors
	 *            the number of protectors with which the simulation starts.
	 */
	public void setProtectors(int protectors) {
		this.protectors = protectors;
	}

	/**
	 * The fruit sources present in the system.
	 */
	public Bag fruitSourcesBag = new Bag();

	/**
	 * The number of fruit sources with which the simulation starts.
	 */
	private int fruitSources = 10;

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
	private int meatSources = 10;

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
	 * Retrieves the default value for a pheromone's strength.
	 * 
	 * @return the default value for a pheromone's strength.
	 */
	public double getDefaultPheromoneStrength() {
		return Pheromone.getDefaultPheromoneStrength();
	}

	/**
	 * Sets the default value for a pheromone's strength.
	 * 
	 * @param defaultPheromoneStrength
	 *            the default value for a pheromone's strength.
	 */
	public void setDefaultPheromoneStrength(double defaultPheromoneStrength) {
		if (defaultPheromoneStrength >= 0)
			Pheromone.setDefaultPheromoneStrength(defaultPheromoneStrength);
	}

	/**
	 * Retrieves the number of steps in which a pheromone completely decays.
	 * 
	 * @return the number of steps in which a pheromone completely decays.
	 */
	public double getPheromoneDecayRate() {
		return Pheromone.getPheromoneDecayRate();
	}

	/**
	 * Sets the number of steps in which a pheromone completely decays.
	 * 
	 * @param pheromoneDecayRate
	 *            the number of steps in which a pheromone completely decays.
	 */
	public void setPheromoneDecayRate(double pheromoneDecayRate) {
		if (pheromoneDecayRate >= 0)
			Pheromone.setPheromoneDecayRate(pheromoneDecayRate);
	}

	/**
	 * Retrieves the maximum amount of food that can be carried by an agent at
	 * any time.
	 * 
	 * @return the maximum amount of food that can be carried by an agent at any
	 *         time.
	 */
	public double getDefaultMaxCarriedFood() {
		return ActiveAgent.getDefaultMaxCarriedFood();
	}

	/**
	 * Sets the maximum amount of food that can be carried by an agent at any
	 * time.
	 * 
	 * @param defaultMaxCarriedFood
	 *            the maximum amount of food that can be carried by an agent at
	 *            any time.
	 */
	public void setDefaultMaxCarriedFood(double defaultMaxCarriedFood) {
		if (defaultMaxCarriedFood >= 0) {
			ActiveAgent.setDefaultMaxCarriedFood(defaultMaxCarriedFood);
		}
	}

	/**
	 * Retrieves the visibility range within which the agent can see.
	 * 
	 * @return the visibility range within which the agent can see.
	 */
	public double getAgentRange() {
		return ActiveAgent.getAgentRange();
	}

	/**
	 * Sets the visibility range within which the agent can see.
	 * 
	 * @param agentRange
	 *            the visibility range within which the agent can see.
	 */
	public void setAgentRange(double agentRange) {
		if (agentRange > 0) {
			ActiveAgent.setAgentRange(agentRange);
		}
	}

	public double getRandomMovementFactor() {
		return ActiveAgent.getRandomMovementFactor();
	}

	public void setRandomMovementFactor(double randomMovementFactor) {
		if (randomMovementFactor >= 0) {
			ActiveAgent.setRandomMovementFactor(randomMovementFactor);
		}
	}

	public double maxStepSize() {
		return ActiveAgent.getMaxStepSize();
	}

	public void setMaxStepSize(double maxStepSize) {
		if (maxStepSize >= 0)
			ActiveAgent.setMaxStepSize(maxStepSize);
	}

	/**
	 * Show nests on the map?
	 */
	private boolean showNests = true;

	/**
	 * Checks if the nests are visible on the map.
	 * 
	 * @return true if the nests are visible, false otherwise.
	 */
	public boolean isShowNests() {
		return showNests;
	}

	/**
	 * Sets if the nests are displayed on the map or not.
	 * 
	 * @param showNests
	 *            true if the nests are displayed, false otherwise.
	 */
	public void setShowNests(boolean showNests) {
		this.showNests = showNests;
	}

	/**
	 * Show hunters on the map?
	 */
	private boolean showHunters = true;

	/**
	 * Checks if the hunters are visible on the map.
	 * 
	 * @return true if the hunters are visible, false otherwise.
	 */
	public boolean isShowHunters() {
		return showHunters;
	}

	/**
	 * Sets if the hunters are displayed on the map or not.
	 * 
	 * @param showHunters
	 *            true if the hunters are displayed, false otherwise.
	 */
	public void setShowHunters(boolean showHunters) {
		this.showHunters = showHunters;
	}

	/**
	 * Show gatherers on the map?
	 */
	private boolean showGatherers = true;

	/**
	 * Checks if the gatherers are visible on the map.
	 * 
	 * @return true if the gatherers are visible, false otherwise.
	 */
	public boolean isShowGatherers() {
		return showGatherers;
	}

	/**
	 * Sets if the gatherers are displayed on the map or not.
	 * 
	 * @param showGatherers
	 *            true if the gatherers are displayed, false otherwise.
	 */
	public void setShowGatherers(boolean showGatherers) {
		this.showGatherers = showGatherers;
	}

	/**
	 * Show fruit sources on the map?
	 */
	private boolean showFruitSources = true;

	/**
	 * Checks if the fruit sources are visible on the map.
	 * 
	 * @return true if the fruit sources are visible, false otherwise.
	 */
	public boolean isShowFruitSources() {
		return showFruitSources;
	}

	/**
	 * Sets if the fruit sources are displayed on the map or not.
	 * 
	 * @param showFruitSources
	 *            true if the fruit sources are displayed, false otherwise.
	 */
	public void setShowFruitSources(boolean showFruitSources) {
		this.showFruitSources = showFruitSources;
	}

	/**
	 * Show meat sources on the map?
	 */
	private boolean showMeatSources = true;

	/**
	 * Checks if the meat sources are visible on the map.
	 * 
	 * @return true if the meat sources are visible, false otherwise.
	 */
	public boolean isShowMeatSources() {
		return showMeatSources;
	}

	/**
	 * Sets if the meat sources are displayed on the map or not.
	 * 
	 * @param showMeatSources
	 *            true if the meat sources are displayed, false otherwise.
	 */
	public void setShowMeatSources(boolean showMeatSources) {
		this.showMeatSources = showMeatSources;
	}

	/**
	 * Show children on the map?
	 */
	private boolean showChildren = true;

	/**
	 * Checks if the children are visible on the map.
	 * 
	 * @return true if the children are visible, false otherwise.
	 */
	public boolean isShowChildren() {
		return showChildren;
	}

	/**
	 * Sets if the children are displayed on the map or not.
	 * 
	 * @param showChildren
	 *            true if the children are displayed, false otherwise.
	 */
	public void setShowChildren(boolean showChildren) {
		this.showChildren = showChildren;
	}

	/**
	 * Show thieves on the map?
	 */
	private boolean showThieves = true;

	/**
	 * Checks if the thieves are visible on the map.
	 * 
	 * @return true if the thieves are visible, false otherwise.
	 */
	public boolean isShowThieves() {
		return showThieves;
	}

	/**
	 * Sets if the thieves are displayed on the map or not.
	 * 
	 * @param showThieves
	 *            true if the thieves are displayed, false otherwise.
	 */
	public void setShowThieves(boolean showThieves) {
		this.showThieves = showThieves;
	}

	/**
	 * Show protectors on the map?
	 */
	private boolean showProtectors = true;

	/**
	 * Checks if the protectors are visible on the map.
	 * 
	 * @return true if the protectors are visible, false otherwise.
	 */
	public boolean isShowProtectors() {
		return showProtectors;
	}

	/**
	 * Sets if the protectors are displayed on the map or not.
	 * 
	 * @param showProtectors
	 *            true if the protectors are displayed, false otherwise.
	 */
	public void setShowProtectors(boolean showProtectors) {
		this.showProtectors = showProtectors;
	}

	/**
	 * Show pheromones on the map?
	 */
	private boolean showPheromones = false;

	/**
	 * Checks if the pheromones are visible on the map.
	 * 
	 * @return true if the pheromones are visible, false otherwise.
	 */
	public boolean isShowPheromones() {
		return showPheromones;
	}

	/**
	 * Sets if the pheromones are displayed on the map or not.
	 * 
	 * @param showPheromones
	 *            true if the pheromones are displayed, false otherwise.
	 */
	public void setShowPheromones(boolean showPheromones) {
		this.showPheromones = showPheromones;
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
			Stoppable stoppable = schedule.scheduleRepeating(nest);
			nest.stoppable = stoppable;
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
			Stoppable stoppable = schedule.scheduleRepeating(fruitSource);
			fruitSource.stoppable = stoppable;
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
			Stoppable stoppable = schedule.scheduleRepeating(meatSource);
			meatSource.stoppable = stoppable;
		}

		// add hunters
		for (int i = 0; i < hunters; i++) {
			// create hunter
			Hunter hunter = new Hunter();
			huntersBag.add(hunter);

			// position hunter
			int positionAtNest = random.nextInt(nests);
			map.setObjectLocation(hunter,
					map.getObjectLocation(nestsBag.get(positionAtNest)));

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(hunter);
			hunter.stoppable = stoppable;
		}

		// add gatherers
		for (int i = 0; i < gatherers; i++) {
			// create gatherer
			Gatherer gatherer = new Gatherer();
			gatherersBag.add(gatherer);

			// position gatherer
			int positionAtNest = random.nextInt(nests);
			map.setObjectLocation(gatherer,
					map.getObjectLocation(nestsBag.get(positionAtNest)));

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(gatherer);
			gatherer.stoppable = stoppable;
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
			Stoppable stoppable = schedule.scheduleRepeating(thief);
			thief.stoppable = stoppable;
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
			Stoppable stoppable = schedule.scheduleRepeating(child);
			child.stoppable = stoppable;
		}

		// add protectors
		for (int i = 0; i < protectors; i++) {
			// create protector
			Protector protector = new Protector();
			protectorsBag.add(protector);

			// position protector
			map.setObjectLocation(protector,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// shedule cycling firing
			Stoppable stoppable = schedule.scheduleRepeating(protector);
			protector.stoppable = stoppable;
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

	private double timeMultipler = 1.0;

	public double getTimeMultipler() {
		return timeMultipler;
	}

	public void setTimeMultipler(double timeMultipler) {
		this.timeMultipler = timeMultipler;
	}
}
