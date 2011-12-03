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

public class ThiefWorld extends SimState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676316886795971516L;

	public Continuous2D map = new Continuous2D(1.0, 100, 100);

	public Bag nestsBag = new Bag();

	private int nests = 1;

	public int getNests() {
		return nests;
	}

	public void setNests(int nests) {
		if (nests >= 0)
			this.nests = nests;
	}

	public Bag huntersBag = new Bag();

	private int hunters = 5;

	public int getHunters() {
		return hunters;
	}

	public void setHunters(int hunters) {
		if (hunters >= 0)
			this.hunters = hunters;
	}

	public Bag gatherersBag = new Bag();

	private int gatherers = 5;

	public int getGatherers() {
		return gatherers;
	}

	public void setGatherers(int gatherers) {
		if (gatherers >= 0)
			this.gatherers = gatherers;
	}

	public Bag thievesBag = new Bag();

	private int thieves = 0;

	public int getThieves() {
		return thieves;
	}

	public void setThieves(int thieves) {
		if (thieves >= 0)
			this.thieves = thieves;
	}

	public Bag childrenBag = new Bag();

	private int children = 0;

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		if (children >= 0)
			this.children = children;
	}

	public Bag fruitSourcesBag = new Bag();

	private int fruitSources = 1;

	public int getFruitSources() {
		return fruitSources;
	}

	public void setFruitSources(int fruitSources) {
		if (fruitSources >= 0)
			this.fruitSources = fruitSources;
	}

	public Bag meatSourcesBag = new Bag();

	private int meatSources = 1;

	public int getMeatSources() {
		return meatSources;
	}

	public void setMeatSources(int meatSources) {
		if (meatSources >= 0)
			this.meatSources = meatSources;
	}

	public ThiefWorld(long seed) {
		super(seed);
	}

	@Override
	public void start() {
		super.start();

		initializeMap();
	}

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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		doLoop(ThiefWorld.class, args);
		System.exit(0);
	}

}
