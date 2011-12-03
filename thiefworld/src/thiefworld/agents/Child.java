package thiefworld.agents;

import sim.engine.SimState;

public class Child extends Agent {
	private static int childNo = 0;
	
	public Child(){
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
	public void step(SimState arg0) {
		// TODO Auto-generated method stub

	}

}
