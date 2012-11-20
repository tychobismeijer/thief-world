package thiefworld.main;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.continuous.Continuous2D;
import thiefworld.agents.Child;
import thiefworld.agents.FruitSource;
import thiefworld.agents.Gatherer;
import thiefworld.agents.Hunter;
import thiefworld.agents.MeatSource;
import thiefworld.agents.Nest;
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

	/**
	 * The settings for the displayed entities.
	 */
	protected ThiefWorldDisplayData displayData = new ThiefWorldDisplayData(
			true, true, true, true, true, true, false, true, true);

	/**
	 * The agents available in the environment.
	 */
	protected ThiefWorldData data = new ThiefWorldData(0, 10, 50, 50, 10, 1, 0,
			1, 0);

	/**
	 * A value indicating the simulation speed.
	 */
	protected double timeMultipler = 1.0;

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
	 * Adds the children to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the children's team.
	 */
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
				map.setObjectLocation(child, map
						.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(child,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(child);
			child.stoppable = stoppable;
		}
	}

	/**
	 * Adds all the entities to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 */
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

	/**
	 * Adds the fruit sources to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the team controlling the fruit sources.
	 */
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

	/**
	 * Adds the gatherers to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the gatherers' team.
	 */
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
				map.setObjectLocation(gatherer, map
						.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(gatherer,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(gatherer);
			gatherer.stoppable = stoppable;
		}
	}

	/**
	 * Adds the hunters to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the hunters' team.
	 */
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
				map.setObjectLocation(hunter, map
						.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(hunter,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(hunter);
			hunter.stoppable = stoppable;
		}
	}

	/**
	 * Adds the meat sources to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the team controlling the meat sources.
	 */
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

	/**
	 * Adds the nests to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the team that controls the nests.
	 */
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
	 * Adds the protectors to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the protectors' team.
	 */
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
				map.setObjectLocation(protector, map
						.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(protector,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// shedule cycling firing
			Stoppable stoppable = schedule.scheduleRepeating(protector);
			protector.stoppable = stoppable;
		}
	}

	/**
	 * Adds the thieves to the environment.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate for the map.
	 * @param maxHeight
	 *            the maximum y coordinate for the map.
	 * @param team
	 *            the theves' team.
	 */
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
				map.setObjectLocation(thief, map
						.getObjectLocation(data.nestsBag.get(positionAtNest)));
			} else {
				map.setObjectLocation(thief,
						Utilities.getRandomPosition(maxWidth, maxHeight));
			}

			// schedule cyclic firing
			Stoppable stoppable = schedule.scheduleRepeating(thief);
			thief.stoppable = stoppable;
		}
	}

	/**
	 * Retrieves the entities in the environment.
	 * 
	 * @return the entities in the environment.
	 */
	public ThiefWorldData getData() {
		return data;
	}

	/**
	 * Retrieves the display parameters for the simulation.
	 * 
	 * @return the display parameters for the simulation.
	 */
	public ThiefWorldDisplayData getDisplayData() {
		return displayData;
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

	/**
	 * Retrieves the speed multiplier for the simulation.
	 * 
	 * @return the speed multiplier for the simulation.
	 */
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

	/**
	 * Sets the entities in the environment.
	 * 
	 * @param data
	 *            the entities in the environment.
	 */
	public void setData(ThiefWorldData data) {
		this.data = data;
	}

	/**
	 * Sets the parameters for the simulation display.
	 * 
	 * @param displayData
	 *            the parameters for the simulation display.
	 */
	public void setDisplayData(ThiefWorldDisplayData displayData) {
		this.displayData = displayData;
	}

	/**
	 * Sets the speed multiplier for the simulation.
	 * 
	 * @param timeMultipler
	 *            the speed multiplier for the simulation.
	 */
	public void setTimeMultipler(double timeMultipler) {
		this.timeMultipler = timeMultipler;
	}

	/**
	 * Starts the simulation in the console.
	 */
	@Override
	public void start() {
		super.start();

		initializeMap();
	}
}
