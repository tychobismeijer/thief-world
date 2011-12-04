package thiefworld.agents;

import sim.engine.SimState;
import thiefworld.main.ThiefWorld;

public class Child extends Agent {
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
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		// drop pheromone
		Pheromone pheromone = new Pheromone(PheromoneType.Child);
		world.schedule.scheduleRepeating(pheromone);
	}

}
