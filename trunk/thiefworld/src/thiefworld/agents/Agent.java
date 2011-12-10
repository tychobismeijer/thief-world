package thiefworld.agents;

import sim.engine.Steppable;
import sim.engine.Stoppable;

public abstract class Agent implements Steppable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2592497966272000992L;
	
	private String name;

	public Stoppable stoppable;

	private int teamNo;

	public String getName() {
		return name;
	}
	
	public int getTeamNo() {
		return teamNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTeamNo(int teamNo) {
		this.teamNo = teamNo;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
