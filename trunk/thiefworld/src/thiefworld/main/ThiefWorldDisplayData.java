package thiefworld.main;

/**
 * Configures the visual elements that need to be displayed.
 * 
 * @author Stefan Adrian Boronea
 *
 */
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

	/**
	 * Creates a new configuration for the simulation display.
	 * @param showChildren are children visible?
	 * @param showFruitSources are fruit sources visible?
	 * @param showGatherers are fruit gatherers visible?
	 * @param showHunters are hunters visible?
	 * @param showMeatSources are meat sources visible?
	 * @param showNests are nests visible?
	 * @param showPheromones are pheromones visible?
	 * @param showProtectors are protectors visible?
	 * @param showThieves are thieves visible?
	 */
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