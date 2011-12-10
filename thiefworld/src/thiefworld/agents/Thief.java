package thiefworld.agents;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Thief extends ActiveAgent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -660441960033117671L;

	private static int thiefNo = 0;

	public Thief() {
		// set name
		thiefNo++;
		this.setName("thief #" + thiefNo);

		// set task switching threshold
		this.setSwitchThreshold(Utilities.nextDouble());
	}

	@Override
	protected void act(ThiefWorld world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

}
