package thiefworld.agents.misc;

/**
 * An agent's success rates when performing various tasks in the current role.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
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

	/**
	 * Creates a success rate set based on the input parameters.
	 * 
	 * @param gatheringSuccess
	 *            how successful was the agent at gathering fruit
	 * @param huntingSuccess
	 *            how successful was the agent at hunting
	 * @param personalSuccess
	 *            how successful was the agent overall
	 */
	public AgentSuccessRates(double gatheringSuccess, double huntingSuccess,
			double personalSuccess) {
		this.gatheringSuccess = gatheringSuccess;
		this.huntingSuccess = huntingSuccess;
		this.personalSuccess = personalSuccess;
	}

	/**
	 * Retrieves the agent's success rate in gathering fruit.
	 * 
	 * @return the agent's success rate in gathering fruit.
	 */
	public double getGatheringSuccess() {
		return gatheringSuccess;
	}

	/**
	 * Retrieves the agent's success rate in hunting.
	 * 
	 * @return the agent's success rate in hunting.
	 */
	public double getHuntingSuccess() {
		return huntingSuccess;
	}

	/**
	 * Retrieves the agent's overall success rate.
	 * 
	 * @return the agent's overall success rate.
	 */
	public double getPersonalSuccess() {
		return personalSuccess;
	}

	/**
	 * Sets the agent's success rate in gathering fruit.
	 * 
	 * @param gatheringSuccess
	 *            the agent's success rate in gathering fruit.
	 */
	public void setGatheringSuccess(double gatheringSuccess) {
		this.gatheringSuccess = gatheringSuccess;
	}

	/**
	 * Sets the agent's success rate in hunting.
	 * 
	 * @param huntingSuccess
	 *            the agent's success rate in hunting.
	 */
	public void setHuntingSuccess(double huntingSuccess) {
		this.huntingSuccess = huntingSuccess;
	}

	/**
	 * Sets the agent's overall success rate.
	 * 
	 * @param personalSuccess
	 *            the agent's overall success rate.
	 */
	public void setPersonalSuccess(double personalSuccess) {
		this.personalSuccess = personalSuccess;
	}
}