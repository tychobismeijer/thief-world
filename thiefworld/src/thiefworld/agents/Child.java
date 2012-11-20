package thiefworld.agents;

import thiefworld.main.ThiefWorld;

/**
 * Describes a child agent that can only observe others perform actions.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Child extends ActiveAgent {
	/**
	 * The number of children created in the system.
	 */
	private static int childNo = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4281396198331579903L;

	/**
	 * The child's age
	 */
	private int age;

	/**
	 * Creates a new child.
	 */
	public Child() {
		childNo++;
		this.setName("child #" + childNo);
	}

	@Override
	protected void act(ThiefWorld world) {
		// TODO Auto-generated method stub

	}

	/**
	 * Retrieves the child's age.
	 * 
	 * @return the child's age.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the child's age.
	 * 
	 * @param age
	 *            the child's age.
	 */
	public void setAge(int age) {
		this.age = age;
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
