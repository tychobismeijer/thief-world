package thiefworld.test;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

public class StudentsWithUI extends GUIState {

	public Display2D display;
	public JFrame displayFrame;
	ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
	NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();

	public StudentsWithUI() {
		super(new Students(System.currentTimeMillis()));
	}

	public StudentsWithUI(SimState state) {
		super(state);
	}

	public static String getName() {
		return "Student Schoolyard Cliques";
	}

	public Object getSimulationInspectedObject() {
		return state;
	}

	public Inspector getInspector() {
		Inspector i = super.getInspector();
		i.setVolatile(true);

		return i;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StudentsWithUI vid = new StudentsWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
		setupPortrayals();
	}

	@Override
	public void load(SimState state) {
		// TODO Auto-generated method stub
		super.load(state);
		setupPortrayals();
	}

	public void setupPortrayals() {
		Students students = (Students) state;

		yardPortrayal.setField(students.yard);
		yardPortrayal.setPortrayalForAll(new MovablePortrayal2D(
				new CircledPortrayal2D(
						new LabelledPortrayal2D(
								new OvalPortrayal2D() {
									/**
									 * 
									 */
									private static final long serialVersionUID = -4410575442367610107L;
		
									@Override
									public void draw(Object object,
											Graphics2D graphics, DrawInfo2D info) {
										// TODO Auto-generated method stub
										Student student = (Student) object;
		
										int agitationShade = (int) (student
												.getAgitation() * 255 / 10.0);
										if (agitationShade > 255)
											agitationShade = 255;
		
										paint = new Color(agitationShade, 0,
												255 - agitationShade);
		
										super.draw(object, graphics, info);
									}
								}, 
								5.0, null, Color.black, true), 
						0, 5.0, Color.green,true)));

		buddiesPortrayal.setField(new SpatialNetwork2D(students.yard,
				students.buddies));
		buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());

		display.reset();
		display.setBackdrop(Color.white);

		display.repaint();
	}

	@Override
	public void init(Controller controller) {
		super.init(controller);

		display = new Display2D(600, 600, this);
		display.setClipping(false);

		displayFrame = display.createFrame();
		displayFrame.setTitle("Schoolyard Display");

		controller.registerFrame(displayFrame);
		displayFrame.setVisible(true);

		display.attach(buddiesPortrayal, "Buddies");
		display.attach(yardPortrayal, "Yard");
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		super.quit();

		if (displayFrame != null)
			displayFrame.dispose();

		displayFrame = null;
		display = null;
	}
}
