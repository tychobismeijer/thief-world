package thiefworld.test;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.field.network.Network;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Interval;

public class Students extends SimState {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8876530699799880349L;

	public Continuous2D yard = new Continuous2D(1.0, 100, 100);
	public int numStudents = 50;

	public double forceToSchoolMultiplier = 0.01;
	public double randomMultiplier = 0.1;

	public int getNumStudents() {
		return numStudents;
	}

	public void setNumStudents(int val) {
		if (val > 0)
			numStudents = val;
	}

	public double getForceToSchoolMultiplier() {
		return forceToSchoolMultiplier;
	}

	public void setForceToSchoolMultiplier(double val) {
		if (val >= 0.0)
			forceToSchoolMultiplier = val;
	}

	public double getRandomMultiplier() {
		return randomMultiplier;
	}

	public void setRandomMultiplier(double val) {
		if (val >= 0.0)
			randomMultiplier = val;
	}

	public Object domRandomMultiplier() {
		return new Interval(0.0, 100.0);
	}

	public double[] getAgitationDistribution() {
		Bag students = buddies.getAllNodes();
		double[] distro = new double[students.numObjs];

		for (int i = 0; i < students.numObjs; i++) {
			distro[i] = ((Student) (students.objs[i])).getAgitation();
		}

		return distro;
	}

	public Network buddies = new Network(false);

	public Students(long seed) {
		super(seed);
	}

	public void start() {
		super.start();

		yard.clear();

		for (int i = 0; i < numStudents; i++) {
			Student student = new Student();

			yard.setObjectLocation(student, new Double2D(yard.getWidth() * 0.5
					+ random.nextDouble() - 0.5, yard.getHeight() * 0.5
					+ random.nextDouble() - 0.5));

			buddies.addNode(student);

			schedule.scheduleRepeating(student);
		}

		Bag students = buddies.getAllNodes();
		for (int i = 0; i < students.size(); i++) {
			Object student = students.get(i);

			Object studentB = null;
			do {
				studentB = students.get(random.nextInt(students.numObjs));
			} while (student == studentB);

			double buddiness = random.nextDouble();
			buddies.addEdge(student, studentB, new Double(buddiness));

			do {
				studentB = students.get(random.nextInt(students.numObjs));
			} while (student == studentB);

			buddiness = random.nextDouble();
			buddies.addEdge(student, studentB, new Double(-buddiness));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doLoop(Students.class, args);
		System.exit(0);
	}

}
