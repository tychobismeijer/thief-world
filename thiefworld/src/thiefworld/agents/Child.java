package thiefworld.agents;

import thiefworld.main.ThiefWorld;

public class Child extends ActiveAgent {
	private static int childNo = 0;

	public Child() {
		childNo++;
		this.setName("child #" + childNo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4281396198331579903L;

	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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
