package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.util.Utilities;

public class Thief extends ActiveAgent {
	private static int thiefNo = 0;

	public Thief() {
		// set name
		thiefNo++;
		this.setName("thief #" + thiefNo);

		// set task switching threshold
		this.setSwitchThreshold(Utilities.nextDouble());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -660441960033117671L;

	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub

	}

}
