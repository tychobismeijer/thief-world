package thiefworld.agents;

import java.util.Iterator;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
import sim.util.MutableDouble2D;
import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Hunter extends ActiveAgent {
	private static int hunterNo = 0;

	public Hunter() {
		// set name
		hunterNo++;
		this.setName("hunter #" + hunterNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865632885952375111L;

	@Override
	public void step(SimState arg0) {
//		ThiefWorld world = (ThiefWorld) arg0;
//		Continuous2D map = world.map;
//
//		Double2D myPosition = map.getObjectLocation(this);
//		MutableDouble2D sumForces = new MutableDouble2D();
//
//		MutableDouble2D forceVector = new MutableDouble2D();
//
//		// check if carried food limit has been reached
//		if (getCarriedFood() < getMaxAllowedFood()) {
//			// check if there are any meat sources nearby
//			boolean foodSourceSpotted = false;
//			
//			for (Iterator it = world.meatSourcesBag.iterator(); it.hasNext();) {
//				MeatSource meatSource = (MeatSource) it.next();
//				Double2D meatSourcePosition = map.getObjectLocation(meatSource);
//
//				if (myPosition.distance(meatSourcePosition) <= 1.0) {
//					// meat can be gathered
//					foodSourceSpotted = true;
//					
//					double maximumFoodPerTurn = Math.min(0.1,
//							getMaxAllowedFood() - getCarriedFood());
//					double foodAtSource = meatSource.getMeatQuantity();
//
//					if (foodAtSource <= maximumFoodPerTurn) {
//						// clear the food source
//						meatSource.setFoodQuantity(0.0);
//					}
//
//					if (getCarriedFood() + 1.0 > getMaxAllowedFood()) {
//						// decrease food source
//						meatSource.setMeatQuantity(meatSource.getMeatQuantity()
//								- (getMaxAllowedFood() - getCarriedFood()));
//
//						// pick up remaining food
//						setCarriedFood(getMaxAllowedFood() - getCarriedFood());
//					} else {
//						meatSource
//								.setMeatQuantity(meatSource.getMeatQuantity() - 1.0);
//						setCarriedFood(getCarriedFood() + 1.0);
//					}
//				} else if (myPosition.distance(meatSourcePosition) <= 5.0) {
//					// food source is in sight
//					foodSourceSpotted = true;
//					
//					forceVector.setTo(new Double2D(Utilities.nextDouble(),
//							Utilities.nextDouble()));
//					forceVector.resize(5.0);
//					sumForces.addIn(new Double2D(forceVector));
//				}
//			}
//			
//			if(foodSourceSpotted == false){
//				// roam around
//				forceVector.setTo(new Double2D(Utilities.nextDouble(),
//						Utilities.nextDouble()));
//				forceVector.resize(5.0);
//				sumForces.addIn(new Double2D(forceVector));
//			}
//		} else {
//			// agent must return to nest
//
//		}
//
//		// add current position vector
//		sumForces.addIn(myPosition);
//
//		// move the agent
//		map.setObjectLocation(this, new Double2D(sumForces));
	}

}
