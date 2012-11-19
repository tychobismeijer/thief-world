package thiefworld.agents.misc;

public class AgentSuccessRates {
	/**
	 * The gathering success rate of the other agents which the current agent
	 * interacts with over time.
	 */
	private double gatheringSuccess;
	/**
	 * The hunting success rate of the other agents which the current agent
	 * interacts with over time.
	 */
	private double huntingSuccess;
	/**
	 * The agent's success rate on performing the current role it has.
	 */
	private double personalSuccess;

	public AgentSuccessRates(double gatheringSuccess, double huntingSuccess,
			double personalSuccess) {
		this.gatheringSuccess = gatheringSuccess;
		this.huntingSuccess = huntingSuccess;
		this.personalSuccess = personalSuccess;
	}

	public double getGatheringSuccess() {
		return gatheringSuccess;
	}

	public void setGatheringSuccess(double gatheringSuccess) {
		this.gatheringSuccess = gatheringSuccess;
	}

	public double getHuntingSuccess() {
		return huntingSuccess;
	}

	public void setHuntingSuccess(double huntingSuccess) {
		this.huntingSuccess = huntingSuccess;
	}

	public double getPersonalSuccess() {
		return personalSuccess;
	}

	public void setPersonalSuccess(double personalSuccess) {
		this.personalSuccess = personalSuccess;
	}
}