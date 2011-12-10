package thiefworld.test;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;
import sim.field.network.Edge;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Student implements Steppable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 588076798050259922L;
	
	public static final double MAX_FORCE = 3.0;
	
	double friendsClose = 0.0;
	double enemiesCloser = 10.0;
	
	public double getAgitation(){
		return friendsClose + enemiesCloser;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + System.identityHashCode(this) + "] agitation: " + getAgitation();
	}

	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		Students students = (Students)state;
		Continuous2D yard = students.yard;
		
		Double2D me = students.yard.getObjectLocation(this);
		MutableDouble2D sumForces = new MutableDouble2D();
		
		friendsClose = enemiesCloser = 0.0;
		
		MutableDouble2D forceVector = new MutableDouble2D();
		Bag out = students.buddies.getEdges(this, null);
		int len = out.size();
		
		for(int buddy = 0; buddy < len; buddy++){
			Edge e = (Edge)(out.get(buddy));
			double buddiness = ((Double)(e.info)).doubleValue();
			
			Double2D him = students.yard.getObjectLocation(e.getOtherNode(this));
			
			if(buddiness >= 0){
				forceVector.setTo(
						(him.x - me.x) * buddiness,
						(him.y - me.y) * buddiness);
				
				if(forceVector.length() > MAX_FORCE)
					forceVector.resize(MAX_FORCE);
				
				friendsClose += forceVector.length();
			}
			else{
				forceVector.setTo(
						(him.x - me.x) * buddiness,
						(him.y - me.y) * buddiness);
				
				if(forceVector.length() > MAX_FORCE)
					forceVector.resize(0.0);
				else if(forceVector.length() > 0)
					forceVector.resize(MAX_FORCE - forceVector.length());
				
				enemiesCloser += forceVector.length();
			}
			
			sumForces.addIn(forceVector);
		}
		
		sumForces.addIn(new Double2D(
				(yard.width * 0.5 - me.x) * students.forceToSchoolMultiplier,
				(yard.height * 0.5 - me.y) * students.forceToSchoolMultiplier));
		sumForces.addIn(new Double2D(
				students.randomMultiplier * (students.random.nextDouble() * 1.0 - 0.5),
				students.randomMultiplier * (students.random.nextDouble() * 1.0 - 0.5)));
		
		sumForces.addIn(me);
		
		students.yard.setObjectLocation(this, new Double2D(sumForces));
	}

}
