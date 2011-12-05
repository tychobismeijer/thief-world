package thiefworld.agents;

import sim.engine.Steppable;

public abstract class Agent implements Steppable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2592497966272000992L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private int teamNo;

	public int getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(int teamNo) {
		this.teamNo = teamNo;
	}
	
	@Override
	public String toString() {
		return name;
	}
}