package thiefworld.agents;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

public class Protector extends ActiveAgent {
	private static int protectorNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 223400888051741701L;

	public Protector() {
		// set name
		protectorNo++;
		this.setName("protector #" + protectorNo);

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
