package thiefworld.agents;

import sim.engine.SimState;

/**
 * A source of meat that uses a constant (unreplenishable) quantity.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class MeatSource extends FoodSource {
	private static int meatSourceNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5901215813515767311L;

	/**
	 * Creates a new meat source with a fixed amount of available meat.
	 */
	public MeatSource() {
		meatSourceNo++;
		this.setName("meat-source #" + meatSourceNo);

		this.setMeatQuantity(10);
	}

	/**
	 * Retrieves the amount of meat available in the meat source.
	 * 
	 * @return the amount of meat available in the meat source.
	 */
	public double getMeatQuantity() {
		return foodQuantity;
	}

	/**
	 * Sets the amount of meat available in the meat source.
	 * 
	 * @param meatQuantity
	 *            the amount of meat available in the meat source.
	 */
	public void setMeatQuantity(double meatQuantity) {
		this.foodQuantity = meatQuantity;
	}

	/**
	 * The meat source is silent.
	 */
	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub

	}
}
