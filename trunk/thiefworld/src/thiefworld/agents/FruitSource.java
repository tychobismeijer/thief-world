package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class FruitSource extends FoodSource {
	private static int fruitSourceNo = 0;

	// apple harvest begins April 24th (day 114) and ends September 26th
	// (day 269)
	private static final int harvestStartingDay = 114;
	private static final int harvestEndDay = 269;
	private static final int harvestDepletionDay = 299;

	private boolean harvestStarted = false;

	private double maxGeneratedFruit;

	public FruitSource() {
		fruitSourceNo++;
		this.setName("fruit-source #" + fruitSourceNo);

		this.foodQuantity = 0.0;

		// generate maximum quantity of fruit that the fruit source can generate

		// apple trees can produce 40-200 kg each year
		maxGeneratedFruit = Math.abs(Utilities.nextGaussian(40, 200));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1505592202788590256L;

	public double getFruitQuantity() {
		return foodQuantity;
	}

	public void setFruitQuantity(double fruitQuantity) {
		this.foodQuantity = fruitQuantity;
	}

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld)arg0;
		long day = (long)(arg0.schedule.getSteps() / world.getTimeMultipler() + 1) % 365;

		if (harvestStarted) {
			double previousDayIncrease = Utilities.nextSigmoid(day - 1,
					harvestStartingDay, harvestEndDay);
			double currentDayIncrease = Utilities.nextSigmoid(day,
					harvestStartingDay, harvestEndDay);

			double increase = maxGeneratedFruit
					* (currentDayIncrease - previousDayIncrease);
			this.increaseFoodQuantity(increase);
		} else if (foodQuantity > 0) {
			double previousDayDecrease = Utilities.nextSigmoid(day - 1,
					harvestEndDay, harvestDepletionDay);
			double currentDayDecrease = Utilities.nextSigmoid(day,
					harvestEndDay, harvestDepletionDay);

			double decrease = maxGeneratedFruit
					* (currentDayDecrease - previousDayDecrease);
			this.decreaseFoodQuantity(decrease);
		}

		if (!harvestStarted && day % harvestStartingDay == 0
				&& day % (harvestStartingDay * 2) != 0
				&& day % (harvestStartingDay * 3) != 0) {
			// harvest period is beginning
			harvestStarted = true;
		}

		if (harvestStarted && day % harvestEndDay == 0)
			harvestStarted = false;
	}
}
