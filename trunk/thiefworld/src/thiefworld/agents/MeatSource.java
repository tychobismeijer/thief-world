package thiefworld.agents;

import sim.engine.SimState;

public class MeatSource extends FoodSource {
	private static int meatSourceNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5901215813515767311L;

	public MeatSource() {
		meatSourceNo++;
		this.setName("meat-source #" + meatSourceNo);

		this.setMeatQuantity(10);
	}

	public double getMeatQuantity() {
		return foodQuantity;
	}

	public void setMeatQuantity(double meatQuantity) {
		this.foodQuantity = meatQuantity;
	}

	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub

	}
}
