package thiefworld.agents.misc;

/**
 * An agent's skill set.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class AgentSkills {
	/**
	 * The agent's skill for gathering fruit.
	 */
	private double gatheringSkill;
	/**
	 * The agent's skill for hunting.
	 */
	private double huntingSkill;
	/**
	 * The agent's skill in stealing food from other teams.
	 */
	private double stealingSkill;

	/**
	 * Creates an instantiation of an agent's skills set based on the given
	 * levels.
	 * 
	 * @param gatheringSkill
	 *            how skilled at gathering fruit
	 * @param huntingSkill
	 *            how skilled at hunting
	 * @param stealingSkill
	 *            how skilled at stealing
	 */
	public AgentSkills(double gatheringSkill, double huntingSkill,
			double stealingSkill) {
		this.gatheringSkill = gatheringSkill;
		this.huntingSkill = huntingSkill;
		this.stealingSkill = stealingSkill;
	}

	/**
	 * Retrieves the agent's fruit gathering skill level.
	 * 
	 * @return the agent's fruit gathering skill level.
	 */
	public double getGatheringSkill() {
		return gatheringSkill;
	}

	/**
	 * Retrieves the agent's hunting skill level.
	 * 
	 * @return the agent's hunting skill level.
	 */
	public double getHuntingSkill() {
		return huntingSkill;
	}

	/**
	 * Retrieves the agent's stealing skill level.
	 * 
	 * @return the agent's stealing skill level.
	 */
	public double getStealingSkill() {
		return stealingSkill;
	}

	/**
	 * Sets the agent's fruit gathering skill level.
	 * 
	 * @param gatheringSkill
	 *            the agent's fruit gathering skill level.
	 */
	public void setGatheringSkill(double gatheringSkill) {
		this.gatheringSkill = gatheringSkill;
	}

	/**
	 * Sets the agent's hunting skill level.
	 * 
	 * @param huntingSkill
	 *            the agent's hunting skill level.
	 */
	public void setHuntingSkill(double huntingSkill) {
		this.huntingSkill = huntingSkill;
	}

	/**
	 * Sets the agent's stealing skill level.
	 * 
	 * @param stealingSkill
	 *            the agent's stealing skill level.
	 */
	public void setStealingSkill(double stealingSkill) {
		this.stealingSkill = stealingSkill;
	}
}