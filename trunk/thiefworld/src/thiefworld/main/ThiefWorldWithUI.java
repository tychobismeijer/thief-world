package thiefworld.main;

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
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import thiefworld.agents.Child;
import thiefworld.agents.FruitSource;
import thiefworld.agents.Gatherer;
import thiefworld.agents.Hunter;
import thiefworld.agents.MeatSource;
import thiefworld.agents.Nest;
import thiefworld.agents.Thief;

public class ThiefWorldWithUI extends GUIState {

	public Display2D display;
	public JFrame displayFrame;
	public ContinuousPortrayal2D mapPortrayal = new ContinuousPortrayal2D();

	public Object getSimulationInspectedObject() {
		return state;
	}

	public Inspector getInspector() {
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}

	public ThiefWorldWithUI() {
		super(new ThiefWorld(System.currentTimeMillis()));
	}

	public ThiefWorldWithUI(SimState state) {
		super(state);
		// TODO Auto-generated constructor stub
	}

	public static String getName() {
		return "thief-world";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThiefWorldWithUI gui = new ThiefWorldWithUI();
		Console c = new Console(gui);
		c.setVisible(true);
	}

	@Override
	public void start() {
		super.start();
		setupPortrayals();
	}

	@Override
	public void load(SimState stateArg) {
		super.load(stateArg);
		setupPortrayals();
	}

	public void setupPortrayals() {
		ThiefWorld world = (ThiefWorld) state;

		mapPortrayal.setField(world.map);

		mapPortrayal.setPortrayalForClass(Hunter.class, new MovablePortrayal2D(
				new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = Color.red;
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		mapPortrayal.setPortrayalForClass(Gatherer.class,
				new MovablePortrayal2D(new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = Color.green;
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		mapPortrayal.setPortrayalForClass(Nest.class, new MovablePortrayal2D(
				new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = Color.yellow;
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		mapPortrayal.setPortrayalForClass(Thief.class, new MovablePortrayal2D(
				new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = Color.gray;
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		mapPortrayal.setPortrayalForClass(Child.class, new MovablePortrayal2D(
				new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = Color.cyan;
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		mapPortrayal.setPortrayalForClass(FruitSource.class,
				new MovablePortrayal2D(new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = new Color(0.0f, 1.0f, 0.0f, 0.5f);
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		mapPortrayal.setPortrayalForClass(MeatSource.class,
				new MovablePortrayal2D(new CircledPortrayal2D(
						new LabelledPortrayal2D(new OvalPortrayal2D() {
							/**
							 * 
							 */
							private static final long serialVersionUID = -666009523425731377L;

							@Override
							public void draw(Object object,
									Graphics2D graphics, DrawInfo2D info) {
								super.draw(object, graphics, info);

								paint = new Color(1.0f, 0.0f, 0.0f, 0.5f);
							}
						}, 5.0, null, Color.white, true), 0, 5.0, Color.green,
						true)));

		display.reset();
		display.setBackdrop(Color.black);

		display.repaint();
	}

	@Override
	public void init(Controller controller) {
		super.init(controller);

		display = new Display2D(600, 600, this);
		display.setClipping(false);

		displayFrame = display.createFrame();
		displayFrame.setTitle("thief-world-map");

		controller.registerFrame(displayFrame);

		displayFrame.setVisible(true);
		display.attach(mapPortrayal, "map");
	}

	@Override
	public void quit() {
		super.quit();

		if (displayFrame != null)
			displayFrame.dispose();

		displayFrame = null;
		display = null;
	}
}
