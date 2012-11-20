package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

/**
 * A source of fruit modeled using the apple's growing cycle.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class FruitSource extends FoodSource {
	/**
	 * The number of fruit sources created.
	 */
	private static int fruitSourceNo = 0;

	/**
	 * The day in a 365 days cycle when the fruit start rotting.
	 */
	private static final int harvestEndDay = 269;

	/**
	 * The day in a 365 days cycle when the fruit are completely gone (i.e.
	 * winter is coming).
	 */
	private static final int harvestDepletionDay = 299;

	/**
	 * The day in a 365 days cycle when the fruit are ready for harvesting.
	 */
	private static final int harvestStartingDay = 114;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1505592202788590256L;

	/**
	 * Flag showing whether or not the harvest has started in this cycle.
	 */
	private boolean harvestStarted = false;

	/**
	 * A randomly selected value of how much fruit the fruit source can produce.
	 */
	private double maxGeneratedFruit;

	/**
	 * Creates a new fruit source.
	 */
	public FruitSource() {
		fruitSourceNo++;
		this.setName("fruit-source #" + fruitSourceNo);

		this.foodQuantity = 0.0;

		// generate maximum quantity of fruit that the fruit source can generate

		// apple trees can produce 40-200 kg each year
		maxGeneratedFruit = Math.abs(Utilities.nextGaussian(40, 200));
	}

	/**
	 * Retrieves the amount of fruit available in the fruit source.
	 * 
	 * @return the amount of fruit available in the fruit source.
	 */
	public double getFruitQuantity() {
		return foodQuantity;
	}

	/**
	 * Sets the amount of fruit available in the fruit source.
	 * 
	 * @param fruitQuantity
	 *            the amount of fruit available in the fruit source.
	 */
	public void setFruitQuantity(double fruitQuantity) {
		this.foodQuantity = fruitQuantity;
	}

	/**
	 * Provides a lifecycle for the fruit source modeled after the apple's
	 * lifecycle. The fruit source starts producing fruit at
	 * {@link #harvestStartingDay} and is completed by {@link #harvestEndDay}.
	 * After that the fruit start roting until the {@link #harvestDepletionDay}
	 * is reached, when winter is coming.
	 */
	@SuppressWarnings("javadoc")
	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;
		long day = (long) (arg0.schedule.getSteps() / world.getTimeMultipler() + 1) % 365;

		if (harvestStarted) {
			// start producing fruit
			double previousDayIncrease = Utilities.nextSigmoid(day - 1,
					harvestStartingDay, harvestEndDay);
			double currentDayIncrease = Utilities.nextSigmoid(day,
					harvestStartingDay, harvestEndDay);

			double increase = maxGeneratedFruit
					* (currentDayIncrease - previousDayIncrease);
			this.increaseFoodQuantity(increase);
		} else if (foodQuantity > 0) {
			// fruit start to rot
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
			// harvest period ended
			harvestStarted = false;
	}
}
