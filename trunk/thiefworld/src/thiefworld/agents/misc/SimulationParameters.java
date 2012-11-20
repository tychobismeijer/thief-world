package thiefworld.agents.misc;

/**
 * Contains the parameters controlling the simulation.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class SimulationParameters {

	/**
	 * The maximum range within which the agent can perform actions.
	 */
	private static double actionRange = 0.5;

	/**
	 * The maximum range within which the agent can observe.
	 */
	private static double agentRange = 30.0;

	/**
	 * The maximum quantity of food which an agent can carry by default.
	 */
	private static double defaultMaxCarriedFood = 2.0;

	/**
	 * The maximum allowed movement step size for an agent.
	 */
	private static double maxStepSize = 1.0;

	/**
	 * Determines how random are the movements of the agent.
	 */
	private static double randomMovementFactor = 0.1;

	/**
	 * Determines decay of skill level per timestep *it's not really a rate
	 * though...*
	 */
	private static double skillDecayRate = 0.005; // Agent loses all skill in
													// 200 time steps

	/**
	 * Determines increase of skill level per dropOffFood action *it's not
	 * really a rate though...*
	 */
	private static double skillIncreaseRate = 0.02; // Agent is fully
													// specialized after 50
													// actions

	/**
	 * The probability of an agent to switch roles (if it doesn't find food).
	 */
	private static double switchProbability = 0.05;

	/**
	 * Retrieves the range within which the agent can perform actions.
	 * 
	 * @return the range within which the agent can perform actions.
	 */
	public static double getActionRange() {
		return actionRange;
	}

	/**
	 * Retrieves the range within which the agent can interact with other agents
	 * and can observe the environment.
	 * 
	 * @return the range within which the agent can interact with other agents
	 *         and can observe the environment.
	 */
	public static double getAgentRange() {
		return agentRange;
	}

	/**
	 * Retrieves the maximum amount of food which the agent can carry at once by
	 * default.
	 * 
	 * @return the maximum amount of food which the agent can carry at once by
	 *         default.
	 */
	public static double getDefaultMaxCarriedFood() {
		return defaultMaxCarriedFood;
	}

	/**
	 * Retrieves the maximum allowed movement step size for an agent.
	 * 
	 * @return the maximum allowed movement step size for an agent.
	 */
	public static double getMaxStepSize() {
		return maxStepSize;
	}

	/**
	 * Retrieves the randomness factor for the agent movement.
	 * 
	 * @return the randomness of the agent movement.
	 */
	public static double getRandomMovementFactor() {
		return randomMovementFactor;
	}

	/**
	 * Retrieves skill decay rate per timestep for the agent.
	 * 
	 * @return the skill decay rate per timestep for the agent.
	 */
	public static double getSkillDecayRate() {
		return skillDecayRate;
	}

	/**
	 * Retrieves skill increase rate per returnFood action for the agent.
	 * 
	 * @return the skill increase rate per returnFood action for the agent.
	 */
	public static double getSkillIncreaseRate() {
		return skillIncreaseRate;
	}

	/**
	 * @return the switchProb
	 */
	public static double getSwitchProbability() {
		return switchProbability;
	}

	/**
	 * Sets the range within which the agent can perform actions.
	 * 
	 * @param actionRange
	 *            the range within which the agent can perform actions.
	 */
	public static void setActionRange(double actionRange) {
		SimulationParameters.actionRange = actionRange;
	}

	/**
	 * Sets the range within which the agent can interact with other agents and
	 * can observe the environment.
	 * 
	 * @param agentRange
	 *            the range within which the agent can interact with other
	 *            agents and can observe the environment.
	 */
	public static void setAgentRange(double agentRange) {
		SimulationParameters.agentRange = agentRange;
	}

	/**
	 * Sets the maximum amount of food which the agent can carry at once by
	 * default.
	 * 
	 * @param defaultMaxCarriedFood
	 *            the maximum amount of food which the agent can carry at once
	 *            by default.
	 */
	public static void setDefaultMaxCarriedFood(double defaultMaxCarriedFood) {
		if (defaultMaxCarriedFood >= 0) {
			SimulationParameters.defaultMaxCarriedFood = defaultMaxCarriedFood;
		}
	}

	/**
	 * Sets the maximum allowed movement step size for an agent.
	 * 
	 * @param maxStepSize
	 *            the maximum allowed movement step size for an agent.
	 */
	public static void setMaxStepSize(double maxStepSize) {
		if (maxStepSize >= 0) {
			SimulationParameters.maxStepSize = maxStepSize;
		}
	}

	/**
	 * Sets the randomness factor for the agent movement.
	 * 
	 * @param randomMovementFactor
	 *            the randomness of the agent movement.
	 */
	public static void setRandomMovementFactor(double randomMovementFactor) {
		if (randomMovementFactor >= 0)
			SimulationParameters.randomMovementFactor = randomMovementFactor;
	}

	/**
	 * Sets skill decay rate per timestep for the agent.
	 * 
	 * @param newRate
	 *            the skill decay rate per timestep for the agent.
	 */
	public static void setSkillDecayRate(double newRate) {
		skillDecayRate = newRate;
	}

	/**
	 * Sets skill increase rate per returnFood action for the agent.
	 * 
	 * @param newRate
	 *            the skill increase rate per returnFood action for the agent.
	 */
	public static void setSkillIncreaseRate(double newRate) {
		skillIncreaseRate = newRate;
	}

	/**
	 * Sets the probability for the agent to switch roles.
	 * 
	 * @param switchProbability
	 *            the probability for the agent to switch roles.
	 */
	public static void setSwitchProbability(double switchProbability) {
		SimulationParameters.switchProbability = switchProbability;
	}

}
