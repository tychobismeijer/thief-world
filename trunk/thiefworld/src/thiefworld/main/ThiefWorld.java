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
	 * Starer method for the simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		doLoop(ThiefWorld.class, args);
		System.exit(0);
	}

	/**
	 * The map structure for the GUI.
	 */
	public Continuous2D map = new Continuous2D(1.0, 100, 100);

	private ThiefWorldDisplayData displayData = new ThiefWorldDisplayData(
			true, true, true, true, true, true, false, true, true);

	public ThiefWorldData data = new ThiefWorldData(0, new Bag(), 10,
			new Bag(), 50, new Bag(), 50, new Bag(), 10,
			new Bag(), 1, new Bag(), 0, new Bag(), 1, 0,
			new Bag());

	private double timeMultipler = 1.0;

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

	/**
	 * Retrieves the visibility range within which the agent can see.
	 * 
	 * @return the visibility range within which the agent can see.
	 */
	public double getAgentRange() {
		return ActiveAgent.getAgentRange();
	}

	/**
	 * Retrieves the number of children with which the simulation starts.
	 * 
	 * @return the number of children with which the simulation starts.
	 */
	public int getChildren() {
		return data.children;
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
	 * Retrieves the default value for a pheromone's strength.
	 * 
	 * @return the default value for a pheromone's strength.
	 */
	public double getDefaultPheromoneStrength() {
		return Pheromone.getDefaultPheromoneStrength();
	}

	/*
	 * Retrieves skill decay rate of agents
	 */
	public double getDefaultSkillDecayStep(){
		return ActiveAgent.getSkillDecayRate();
	}
	
	/*
	 * Retrieves skill increase rate of agents
	 */
	public double getDefaultSkillIncreaseStep() {
		return ActiveAgent.getSkillIncreaseRate();
	}
	
	public static double getDefaultSwitchProb() {
		return ActiveAgent.getSwitchProbability();
	}
	
	/**
	 * Retrieves the number of fruit sources with which the simulation starts.
	 * 
	 * @return the number of fruit sources with which the simulation starts.
	 */
	public int getFruitSources() {
		return data.fruitSources;
	}

	/**
	 * Retrieves the number of gatherers with which the simulation starts.
	 * 
	 * @return the number of gatherers with which the simulation starts.
	 */
	public int getGatherers() {
		return data.gatherers;
	}
	
	public int getNrOfGatherers(){
		Bag gatherers = map.getAllObjects();
		int count = 0;
		for(int idx = 0;idx < gatherers.size(); idx++){
			if(gatherers.get(idx).getClass() == Gatherer.class)
				count++;
		}
		
		return count;
	}

	/**
	 * Retrieves the number of hunters with which the simulation starts.
	 * 
	 * @return the number of hunters with which the simulation starts.
	 */
	public int getHunters() {
		return data.hunters;
	}
	
	public int getNrOfHunters() {
		Bag gatherers = map.getAllObjects();
		int count = 0;
		for(int idx = 0;idx < gatherers.size(); idx++){
			if(gatherers.get(idx).getClass() == Hunter.class)
				count++;
		}
		
		return count;
	}

	/**
	 * Retrieves the number of meat sources with which the simulation starts.
	 * 
	 * @return the number of meat sources with which the simulation starts.
	 */
	public int getMeatSources() {
		return data.meatSources;
	}

	/**
	 * Retrieves the nests present in the system.
	 * 
	 * @return the nests present in the system
	 */
	public int getNests() {
		return data.nests;
	}

	/**
	 * Retrieves the total amount of fruit available in the fruit sources.
	 * 
	 * @return the total amount of fruit available in the fruit sources.
	 */
	public double getOverallAvailableFruit() {
		double overallAvailableFruit = 0.0;

		for (int i = 0; i < this.data.fruitSourcesBag.size(); i++) {
			FruitSource fruitSource = (FruitSource) data.fruitSourcesBag.get(i);

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

		for (int i = 0; i < this.data.meatSourcesBag.size(); i++) {
			MeatSource meatSource = (MeatSource) data.meatSourcesBag.get(i);

			if (meatSource.isActive())
				overallAvailableMeat += meatSource.getMeatQuantity();
		}

		return overallAvailableMeat;
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
	 * Retrieves the number of protectors with which the simulation starts.
	 * 
	 * @return the number of protectors with which the simulation starts.
	 */
	public int getProtectors() {
		return data.protectors;
	}

	public double getRandomMovementFactor() {
		return ActiveAgent.getRandomMovementFactor();
	}

	/**
	 * Retrieves the total amount of fruit stored in the nests.
	 * 
	 * @return the total amount of fruit stored in the nests.
	 */
	public double getStoredFruit() {
		double storedFruit = 0.0;

		for (int i = 0; i < this.data.nestsBag.size(); i++) {
			Nest nest = (Nest) data.nestsBag.get(i);

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

		for (int i = 0; i < this.data.nestsBag.size(); i++) {
			Nest nest = (Nest) data.nestsBag.get(i);

			storedMeat += nest.getMeatQuantity();
		}

		return storedMeat;
	}

	public int getTeams() {
		return data.teams;
	}

	/**
	 * Retrieves the number of thieves with which the simulation starts.
	 * 
	 * @return the number of thieves with which the simulation starts.
	 */
	public int getThieves() {
		return data.thieves;
	}

	public double getTimeMultipler() {
		return timeMultipler;
	}

	/**
	 * Adds the agents within the simulation environment at random locations.
	 */
	private void initializeMap() {
		map.clear();

		int maxWidth = (int) map.getWidth();
		int maxHeight = (int) map.getHeight();

		addEntities(maxWidth, maxHeight);
	}

	private void addEntities(int maxWidth, int maxHeight) {
		// add nests
		for (int team = 0; team < data.teams; team++) {
			addNests(maxWidth, maxHeight, team);

			// add fruit sources
			addFruitSources(maxWidth, maxHeight, team);

			// add meat sources
			addMeatSources(maxWidth, maxHeight, team);

			// add hunters
			addHunters(maxWidth, maxHeight, team);

			// add gatherers
			addGatherers(maxWidth, maxHeight, team);

			// add thieves
			addThieves(maxWidth, maxHeight, team);

			// add children
			addChildren(maxWidth, maxHeight, team);

			// add protectors
			addProtectors(maxWidth, maxHeight, team);
		}
	}

	private void addProtectors(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.protectors; i++) {
			// create protector
			Protector protector = new Protector();
			protector.setTeamNo(team);
			data.protectorsBag.add(protector);

			// position protector
			if (data.nests > 0) {
				int positionAtNest = team * data.nests
						+ random.nextInt(data.nests);
				map.setObjectLocation(protector,
						map.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(protector,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// shedule cycling firing
			Stoppable stoppable = schedule.scheduleRepeating(protector);
			protector.stoppable = stoppable;
		}
	}

	private void addChildren(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.children; i++) {
			// create child
			Child child = new Child();
			child.setTeamNo(team);
			data.childrenBag.add(child);

			// position child
			if (data.nests > 0) {
				int positionAtNest = team * data.nests
						+ random.nextInt(data.nests);
				map.setObjectLocation(child,
						map.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(child,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(child);
			child.stoppable = stoppable;
		}
	}

	private void addThieves(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.thieves; i++) {
			// create thief
			Thief thief = new Thief();
			thief.setTeamNo(team);
			data.thievesBag.add(thief);

			// position thief
			if (data.nests > 0) {
				int positionAtNest = team * data.nests
						+ random.nextInt(data.nests);
				map.setObjectLocation(thief,
						map.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(thief,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(thief);
			thief.stoppable = stoppable;
		}
	}

	private void addGatherers(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.gatherers; i++) {
			// create gatherer
			Gatherer gatherer = new Gatherer();
			gatherer.setTeamNo(team);
			data.gatherersBag.add(gatherer);

			// position gatherer
			if (data.nests > 0) {
				int positionAtNest = team * data.nests
						+ random.nextInt(data.nests);
				map.setObjectLocation(gatherer,
						map.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(gatherer,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(gatherer);
			gatherer.stoppable = stoppable;
		}
	}

	private void addHunters(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.hunters; i++) {
			// create hunter
			Hunter hunter = new Hunter();
			hunter.setTeamNo(team);
			data.huntersBag.add(hunter);

			// position hunter
			if (data.nests > 0) {
				int positionAtNest = team * data.nests
						+ random.nextInt(data.nests);
				map.setObjectLocation(hunter,
						map.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(hunter,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(hunter);
			hunter.stoppable = stoppable;
		}
	}

	private void addMeatSources(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.meatSources; i++) {
			// create meat source
			MeatSource meatSource = new MeatSource();
			meatSource.setTeamNo(team);
			data.meatSourcesBag.add(meatSource);

			// position fruit source
			map.setObjectLocation(meatSource,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(meatSource);
			meatSource.stoppable = stoppable;
		}
	}

	private void addFruitSources(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.fruitSources; i++) {
			// create fruit source
			FruitSource fruitSource = new FruitSource();
			fruitSource.setTeamNo(team);
			data.fruitSourcesBag.add(fruitSource);

			// position fruit source
			map.setObjectLocation(fruitSource,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(fruitSource);
			fruitSource.stoppable = stoppable;
		}
	}

	private void addNests(int maxWidth, int maxHeight, int team) {
		for (int i = 0; i < data.nests; i++) {
			// create nest
			Nest nest = new Nest();
			nest.setTeamNo(team);
			data.nestsBag.add(nest);

			// position nest
			map.setObjectLocation(nest,
					Utilities.getRandomPosition(maxWidth, maxHeight));

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(nest);
			nest.stoppable = stoppable;
		}
	}

	/**
	 * Checks if the children are visible on the map.
	 * 
	 * @return true if the children are visible, false otherwise.
	 */
	public boolean isShowChildren() {
		return displayData.showChildren;
	}

	/**
	 * Checks if the fruit sources are visible on the map.
	 * 
	 * @return true if the fruit sources are visible, false otherwise.
	 */
	public boolean isShowFruitSources() {
		return displayData.showFruitSources;
	}

	/**
	 * Checks if the gatherers are visible on the map.
	 * 
	 * @return true if the gatherers are visible, false otherwise.
	 */
	public boolean isShowGatherers() {
		return displayData.showGatherers;
	}

	/**
	 * Checks if the hunters are visible on the map.
	 * 
	 * @return true if the hunters are visible, false otherwise.
	 */
	public boolean isShowHunters() {
		return displayData.showHunters;
	}

	/**
	 * Checks if the meat sources are visible on the map.
	 * 
	 * @return true if the meat sources are visible, false otherwise.
	 */
	public boolean isShowMeatSources() {
		return displayData.showMeatSources;
	}

	/**
	 * Checks if the nests are visible on the map.
	 * 
	 * @return true if the nests are visible, false otherwise.
	 */
	public boolean isShowNests() {
		return displayData.showNests;
	}

	/**
	 * Checks if the pheromones are visible on the map.
	 * 
	 * @return true if the pheromones are visible, false otherwise.
	 */
	public boolean isShowPheromones() {
		return displayData.showPheromones;
	}

	/**
	 * Checks if the protectors are visible on the map.
	 * 
	 * @return true if the protectors are visible, false otherwise.
	 */
	public boolean isShowProtectors() {
		return displayData.showProtectors;
	}

	/**
	 * Checks if the thieves are visible on the map.
	 * 
	 * @return true if the thieves are visible, false otherwise.
	 */
	public boolean isShowThieves() {
		return displayData.showThieves;
	}

	public double maxStepSize() {
		return ActiveAgent.getMaxStepSize();
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

	/**
	 * Sets the number of children with which the simulation starts.
	 * 
	 * @param children
	 *            the number of children with which the simulation starts.
	 */
	public void setChildren(int children) {
		if (children >= 0)
			this.data.children = children;
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
	 * Sets the default value for a pheromone's strength.
	 * 
	 * @param defaultPheromoneStrength
	 *            the default value for a pheromone's strength.
	 */
	public void setDefaultPheromoneStrength(double defaultPheromoneStrength) {
		if (defaultPheromoneStrength >= 0)
			Pheromone.setDefaultPheromoneStrength(defaultPheromoneStrength);
	}

	/*
	 * Retrieves skill decay rate of agents
	 */
	public void setDefaultSkillDecayStep(double newRate){
		ActiveAgent.setSkillDecayRate(newRate);
	}
	
	/*
	 * Retrieves skill increase rate of agents
	 */
	public void setDefaultSkillIncreaseStep(double newRate) {
		ActiveAgent.setSkillIncreaseRate(newRate);
	}
	
	/**
	 * @param switchProb the switchProbability to set
	 */
	public static void setSwitchProbability(double newProb) {
		ActiveAgent.setSwitchProbability(newProb);
	}

	
	/**
	 * Sets the number of fruit sources with which the simulation starts.
	 * 
	 * @param fruitSources
	 *            the number of fruit sources with which the simulation starts.
	 */
	public void setFruitSources(int fruitSources) {
		if (fruitSources >= 0)
			this.data.fruitSources = fruitSources;
	}

	/**
	 * Sets the number of gatherers with which the simulation starts.
	 * 
	 * @param gatherers
	 *            the number of gatherers with which the simulation starts.
	 */
	public void setGatherers(int gatherers) {
		if (gatherers >= 0)
			this.data.gatherers = gatherers;
	}

	/**
	 * Sets the number of hunters with which the simulation starts.
	 * 
	 * @param hunters
	 *            the number of hunters with which the simulation starts.
	 */
	public void setHunters(int hunters) {
		if (hunters >= 0)
			this.data.hunters = hunters;
	}

	public void setMaxStepSize(double maxStepSize) {
		if (maxStepSize >= 0)
			ActiveAgent.setMaxStepSize(maxStepSize);
	}

	/**
	 * Sets the number of meat sources with which the simulation starts.
	 * 
	 * @param meatSources
	 *            the number of meat sources with which the simulation starts.
	 */
	public void setMeatSources(int meatSources) {
		if (meatSources >= 0)
			this.data.meatSources = meatSources;
	}

	/**
	 * Sets the number of nests with which the simulation starts.
	 * 
	 * @param nests
	 *            the number of nests with which the simulation starts.
	 */
	public void setNests(int nests) {
		if (nests >= 0)
			this.data.nests = nests;
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
	 * Sets the number of protectors with which the simulation starts.
	 * 
	 * @param protectors
	 *            the number of protectors with which the simulation starts.
	 */
	public void setProtectors(int protectors) {
		this.data.protectors = protectors;
	}

	public void setRandomMovementFactor(double randomMovementFactor) {
		if (randomMovementFactor >= 0) {
			ActiveAgent.setRandomMovementFactor(randomMovementFactor);
		}
	}

	/**
	 * Sets if the children are displayed on the map or not.
	 * 
	 * @param showChildren
	 *            true if the children are displayed, false otherwise.
	 */
	public void setShowChildren(boolean showChildren) {
		this.displayData.showChildren = showChildren;
	}

	/**
	 * Sets if the fruit sources are displayed on the map or not.
	 * 
	 * @param showFruitSources
	 *            true if the fruit sources are displayed, false otherwise.
	 */
	public void setShowFruitSources(boolean showFruitSources) {
		this.displayData.showFruitSources = showFruitSources;
	}

	/**
	 * Sets if the gatherers are displayed on the map or not.
	 * 
	 * @param showGatherers
	 *            true if the gatherers are displayed, false otherwise.
	 */
	public void setShowGatherers(boolean showGatherers) {
		this.displayData.showGatherers = showGatherers;
	}

	/**
	 * Sets if the hunters are displayed on the map or not.
	 * 
	 * @param showHunters
	 *            true if the hunters are displayed, false otherwise.
	 */
	public void setShowHunters(boolean showHunters) {
		this.displayData.showHunters = showHunters;
	}

	/**
	 * Sets if the meat sources are displayed on the map or not.
	 * 
	 * @param showMeatSources
	 *            true if the meat sources are displayed, false otherwise.
	 */
	public void setShowMeatSources(boolean showMeatSources) {
		this.displayData.showMeatSources = showMeatSources;
	}

	/**
	 * Sets if the nests are displayed on the map or not.
	 * 
	 * @param showNests
	 *            true if the nests are displayed, false otherwise.
	 */
	public void setShowNests(boolean showNests) {
		this.displayData.showNests = showNests;
	}

	/**
	 * Sets if the pheromones are displayed on the map or not.
	 * 
	 * @param showPheromones
	 *            true if the pheromones are displayed, false otherwise.
	 */
	public void setShowPheromones(boolean showPheromones) {
		this.displayData.showPheromones = showPheromones;
	}

	/**
	 * Sets if the protectors are displayed on the map or not.
	 * 
	 * @param showProtectors
	 *            true if the protectors are displayed, false otherwise.
	 */
	public void setShowProtectors(boolean showProtectors) {
		this.displayData.showProtectors = showProtectors;
	}

	/**
	 * Sets if the thieves are displayed on the map or not.
	 * 
	 * @param showThieves
	 *            true if the thieves are displayed, false otherwise.
	 */
	public void setShowThieves(boolean showThieves) {
		this.displayData.showThieves = showThieves;
	}

	public void setTeams(int teams) {
		this.data.teams = teams;
	}

	/**
	 * Sets the number of thieves present with which the simulation starts.
	 * 
	 * @param thieves
	 *            the number of thieves with which the simulation starts.
	 */
	public void setThieves(int thieves) {
		if (thieves >= 0)
			this.data.thieves = thieves;
	}

	public void setTimeMultipler(double timeMultipler) {
		this.timeMultipler = timeMultipler;
	}

	@Override
	public void start() {
		super.start();

		initializeMap();
	}
}
