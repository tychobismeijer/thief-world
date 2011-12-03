package thiefworld.agents;

import sim.engine.SimState;

public class Nest extends Agent {
	private static int nestNo = 0;
	
	public Nest(){
		nestNo++;
		this.setName("nest #" + nestNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988304303551923917L;

	private double fruitQuantity;

	public double getFruitQuantity() {
		return fruitQuantity;
	}

	public void setFruitQuantity(double fruitQuantity) {
		this.fruitQuantity = fruitQuantity;
	}

	private double meatQuantity;

	public double getMeatQuantity() {
		return meatQuantity;
	}

	public void setMeatQuantity(double meatQuantity) {
		this.meatQuantity = meatQuantity;
	}

	public double getFoodQuantity() {
		return fruitQuantity + meatQuantity;
	}

	@Override
	public void step(SimState state) {
		//TODO
		fruitQuantity++;
	}

}
