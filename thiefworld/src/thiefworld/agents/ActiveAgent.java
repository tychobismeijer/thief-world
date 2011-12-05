package thiefworld.agents;

import thiefworld.util.Utilities;

public abstract class ActiveAgent extends Agent {
	public ActiveAgent() {
		// set task switching threshold
		switchThreshold = Utilities.nextDouble();
		health = 1.0;
		maxAllowedFood = ActiveAgent.getDefaultMaxCarriedFood();
	}

	private static final long serialVersionUID = 5597485865861516823L;

	private static double defaultMaxCarriedFood = 1.0;

	public static double getDefaultMaxCarriedFood() {
		return ActiveAgent.defaultMaxCarriedFood;
	}

	public static void setDefaultMaxCarriedFood(double defaultMaxCarriedFood) {
		if (defaultMaxCarriedFood >= 0) {
			ActiveAgent.defaultMaxCarriedFood = defaultMaxCarriedFood;
		}
	}

	private static double actionRange = 0.1;

	public static double getActionRange() {
		return actionRange;
	}

	public static void setActionRange(double actionRange) {
		ActiveAgent.actionRange = actionRange;
	}

	private static double agentRange = 10.0;

	public static double getAgentRange() {
		return ActiveAgent.agentRange;
	}

	public static void setAgentRange(double agentRange) {
		ActiveAgent.agentRange = agentRange;
	}

	private double gatheringSuccess;

	public double getGatheringSuccess() {
		return gatheringSuccess;
	}

	public void setGatheringSuccess(double gatheringSuccess) {
		this.gatheringSuccess = gatheringSuccess;
	}

	private double huntingSuccess;

	public double getHuntingSuccess() {
		return huntingSuccess;
	}

	public void setHuntingSuccess(double huntingSuccess) {
		this.huntingSuccess = huntingSuccess;
	}

	private double personalSuccess;

	public double getPersonalSuccess() {
		return personalSuccess;
	}

	public void setPersonalSuccess(double personalSuccess) {
		this.personalSuccess = personalSuccess;
	}

	private double gatheringSkill;

	public double getGatheringSkill() {
		return gatheringSkill;
	}

	public void setGatheringSkill(double gatheringSkill) {
		this.gatheringSkill = gatheringSkill;
	}

	private double huntingSkill;

	public double getHuntingSkill() {
		return huntingSkill;
	}

	public void setHuntingSkill(double huntingSkill) {
		this.huntingSkill = huntingSkill;
	}

	private double stealingSkill;

	public double getStealingSkill() {
		return stealingSkill;
	}

	public void setStealingSkill(double stealingSkill) {
		this.stealingSkill = stealingSkill;
	}

	private double switchThreshold;

	public double getSwitchThreshold() {
		return switchThreshold;
	}

	public void setSwitchThreshold(double switchThreshold) {
		this.switchThreshold = switchThreshold;
	}

	private double carriedFood;

	public double getCarriedFood() {
		return carriedFood;
	}

	public void setCarriedFood(double carriedFood) {
		this.carriedFood = carriedFood;
	}

	public void increaseCarriedFood(double amount) {
		this.carriedFood += amount;

		if (this.carriedFood > maxAllowedFood)
			this.carriedFood = maxAllowedFood;
	}

	public void decreaseCarriedFood(double amount) {
		this.carriedFood -= amount;

		if (this.carriedFood < 0)
			this.carriedFood = 0;
	}

	private double maxAllowedFood = 2.0;

	public double getMaxAllowedFood() {
		return maxAllowedFood;
	}

	public void setMaxAllowedFood(double maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}

	private double health;

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
}
