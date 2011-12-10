package thiefworld.agents;

import thiefworld.main.ThiefWorld;

/**
 * Describes a child agent that can only observe others perform actions.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Child extends ActiveAgent {
	private static int childNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4281396198331579903L;

	private int age;

	public Child() {
		childNo++;
		this.setName("child #" + childNo);
	}

	@Override
	protected void act(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	protected void thinkAboutSwitchingJobs(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

}
