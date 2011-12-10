package thiefworld.agents;

import sim.engine.Steppable;
import sim.engine.Stoppable;

/**
 * Describes a generic agent within the system.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public abstract class Agent implements Steppable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2592497966272000992L;

	/**
	 * The name which identifies the agent.
	 */
	private String name;

	/**
	 * The {@link Stoppable Stoppable} reference that allows an agent to be
	 * stopped from the MASON scheduling.
	 */
	public Stoppable stoppable;

	/**
	 * The team in which the agent is assigned.
	 */
	private int teamNo;

	/**
	 * Retrieves the identifying name of the agent.
	 * 
	 * @return the identifying name of the agent.
	 */
	public String getName() {
		return "[" + teamNo + "] " + name;
	}

	/**
	 * Retrieves the team into which the agent was assigned.
	 * 
	 * @return the team into which the agent was assigned.
	 */
	public int getTeamNo() {
		return teamNo;
	}

	/**
	 * Sets the identifying name of the agent.
	 * 
	 * @param name
	 *            the identifying name of the agent.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Assigns the agent to a specific team.
	 * 
	 * @param teamNo
	 *            the team into which the agent is assigned.
	 */
	public void setTeamNo(int teamNo) {
		this.teamNo = teamNo;
	}

	@Override
	public String toString() {
		return "[" + teamNo + "] " + name;
	}
}
