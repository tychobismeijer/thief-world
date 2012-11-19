package thiefworld.main;

public class ThiefWorldDisplayData {
	/**
	 * Show children on the map?
	 */
	public boolean showChildren;
	/**
	 * Show fruit sources on the map?
	 */
	public boolean showFruitSources;
	/**
	 * Show gatherers on the map?
	 */
	public boolean showGatherers;
	/**
	 * Show hunters on the map?
	 */
	public boolean showHunters;
	/**
	 * Show meat sources on the map?
	 */
	public boolean showMeatSources;
	/**
	 * Show nests on the map?
	 */
	public boolean showNests;
	/**
	 * Show pheromones on the map?
	 */
	public boolean showPheromones;
	/**
	 * Show protectors on the map?
	 */
	public boolean showProtectors;
	/**
	 * Show thieves on the map?
	 */
	public boolean showThieves;

	public ThiefWorldDisplayData(boolean showChildren,
			boolean showFruitSources, boolean showGatherers,
			boolean showHunters, boolean showMeatSources, boolean showNests,
			boolean showPheromones, boolean showProtectors, boolean showThieves) {
		this.showChildren = showChildren;
		this.showFruitSources = showFruitSources;
		this.showGatherers = showGatherers;
		this.showHunters = showHunters;
		this.showMeatSources = showMeatSources;
		this.showNests = showNests;
		this.showPheromones = showPheromones;
		this.showProtectors = showProtectors;
		this.showThieves = showThieves;
	}
}