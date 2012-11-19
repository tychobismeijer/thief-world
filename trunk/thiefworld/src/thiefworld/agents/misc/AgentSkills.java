package thiefworld.agents.misc;

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

	public AgentSkills(double gatheringSkill, double huntingSkill,
			double stealingSkill) {
		this.gatheringSkill = gatheringSkill;
		this.huntingSkill = huntingSkill;
		this.stealingSkill = stealingSkill;
	}

	public double getGatheringSkill() {
		return gatheringSkill;
	}

	public void setGatheringSkill(double gatheringSkill) {
		this.gatheringSkill = gatheringSkill;
	}

	public double getHuntingSkill() {
		return huntingSkill;
	}

	public void setHuntingSkill(double huntingSkill) {
		this.huntingSkill = huntingSkill;
	}

	public double getStealingSkill() {
		return stealingSkill;
	}

	public void setStealingSkill(double stealingSkill) {
		this.stealingSkill = stealingSkill;
	}
}