package thiefworld.agents;

import thiefworld.main.ThiefWorld;
import thiefworld.util.Utilities;

/**
 * Describes an agent that protects the nests from thieves by attacking the
 * thieves from other teams.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Protector extends ActiveAgent {
	private static int protectorNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 223400888051741701L;

	/**
	 * Creates a new protector.
	 */
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
	
	@Override
	protected void thinkAboutSwitchingJobsSimple(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

}
