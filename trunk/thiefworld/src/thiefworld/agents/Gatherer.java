package thiefworld.agents;

import sim.engine.SimState;

public class Gatherer extends ActiveAgent {
	private static int gathererNo = 0;

	public Gatherer() {
		// set name
		gathererNo++;
		this.setName("gatherer #" + gathererNo);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3915111858846207408L;

	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub

	}

}
