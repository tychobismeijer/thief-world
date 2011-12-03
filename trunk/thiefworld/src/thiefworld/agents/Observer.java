package thiefworld.agents;

import sim.engine.SimState;

public class Observer extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2220784702275759607L;
	
	private static int observerNo = 0;
	
	public Observer(){
		observerNo++;
		this.setName("observer #" + observerNo);
	}

	@Override
	public void step(SimState arg0) {
		// TODO Auto-generated method stub
		
	}
}
