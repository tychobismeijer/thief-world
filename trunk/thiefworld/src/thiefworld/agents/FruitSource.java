package thiefworld.agents;

import sim.engine.SimState;

public class FruitSource extends FoodSource {
	private static int fruitSourceNo = 0;

	public FruitSource() {
		fruitSourceNo++;
		this.setName("fruit-source #" + fruitSourceNo);
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
		// TODO Auto-generated method stub

	}
}
