package thiefworld.main;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
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
import sim.portrayal.simple.ImagePortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import thiefworld.agents.Child;
import thiefworld.agents.FruitSource;
import thiefworld.agents.Gatherer;
import thiefworld.agents.Hunter;
import thiefworld.agents.MeatSource;
import thiefworld.agents.Nest;
import thiefworld.agents.Pheromone;
import thiefworld.agents.Protector;
import thiefworld.agents.Thief;

/**
 * GUI implementation for the thief-world simulation.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
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
	}

	/**
	 * Retrieves the title of the simulation window. (for display purposes).
	 * 
	 * @return the title of the simulation window.
	 */
	public static String getName() {
		return "thief-world";
	}

	/**
	 * Starter method for the GUI simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ThiefWorldWithUI gui = new ThiefWorldWithUI();
		Console c = new Console(gui);
		c.setVisible(true);
	}

	/**
	 * Starts the GUI.
	 */
	@Override
	public void start() {
		super.start();
		setupPortrayals();
	}

	/**
	 * Loads the GUI components.
	 */
	@Override
	public void load(SimState stateArg) {
		super.load(stateArg);
		setupPortrayals();
	}

	/**
	 * Sets the display settings for the various agents in the system.
	 */
	public void setupPortrayals() {
		ThiefWorld world = (ThiefWorld) state;

		mapPortrayal.setField(world.map);

		mapPortrayal.setPortrayalForNull(new ImagePortrayal2D(new ImageIcon(
				"./images/none.png")));

		// set display settings for hunters
		if (world.isShowHunters()) {
			mapPortrayal.setPortrayalForClass(
					Hunter.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(new ImagePortrayal2D(
									new ImageIcon("./images/marine.png"), 5.0),
									5.0, null, Color.white, true), 0, world
									.getAgentRange(), Color.green, true)));
		} else {
			// TODO hide hunters
		}
		// if (world.isShowHunters()) {
		// mapPortrayal.setPortrayalForClass(Hunter.class,
		// new MovablePortrayal2D(new CircledPortrayal2D(
		// new LabelledPortrayal2D(new OvalPortrayal2D() {
		// /**
		// *
		// */
		// private static final long serialVersionUID = -666009523425731377L;
		//
		// @Override
		// public void draw(Object object,
		// Graphics2D graphics, DrawInfo2D info) {
		// paint = Color.red;
		// super.draw(object, graphics, info);
		// }
		// }, 5.0, null, Color.white, true), 0, world
		// .getAgentRange(), Color.green, true)));
		// } else {
		// // TODO hide hunters
		// }

		// set display settings for gatherers
		if (world.isShowGatherers()) {
			mapPortrayal.setPortrayalForClass(Gatherer.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(new ImagePortrayal2D(
									new ImageIcon("./images/gatherer.jpeg"),
									5.0), 5.0, null, Color.white, true), 0,
							world.getAgentRange(), Color.green, true)));
			// mapPortrayal.setPortrayalForClass(Gatherer.class,
			// new MovablePortrayal2D(new CircledPortrayal2D(
			// new LabelledPortrayal2D(new OvalPortrayal2D() {
			// /**
			// *
			// */
			// private static final long serialVersionUID =
			// -666009523425731377L;
			//
			// @Override
			// public void draw(Object object,
			// Graphics2D graphics, DrawInfo2D info) {
			// paint = Color.green;
			// super.draw(object, graphics, info);
			// }
			// }, 5.0, null, Color.white, true), 0, world
			// .getAgentRange(), Color.green, true)));
		} else {
			// TODO hide gatherers
		}

		// set display settings for nests
		if (world.isShowNests()) {
			mapPortrayal.setPortrayalForClass(
					Nest.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(new ImagePortrayal2D(
									new ImageIcon("./images/nest.gif"), 10.0),
									5.0, null, Color.white, true), 0, world
									.getAgentRange(), Color.green, true)));
			// mapPortrayal.setPortrayalForClass(Nest.class,
			// new MovablePortrayal2D(new CircledPortrayal2D(
			// new LabelledPortrayal2D(new OvalPortrayal2D() {
			// /**
			// *
			// */
			// private static final long serialVersionUID =
			// -666009523425731377L;
			//
			// @Override
			// public void draw(Object object,
			// Graphics2D graphics, DrawInfo2D info) {
			// paint = Color.yellow;
			// super.draw(object, graphics, info);
			// }
			// }, 5.0, null, Color.white, true), 0, world
			// .getAgentRange(), Color.green, true)));
		} else {
			// TODO hide nests
		}

		// set display settings for thieves
		if (world.isShowThieves()) {
			mapPortrayal.setPortrayalForClass(
					Thief.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(new ImagePortrayal2D(
									new ImageIcon("./images/ghost.jpg"), 5.0),
									5.0, null, Color.white, true), 0, world
									.getAgentRange(), Color.green, true)));
			// mapPortrayal.setPortrayalForClass(Thief.class,
			// new MovablePortrayal2D(new CircledPortrayal2D(
			// new LabelledPortrayal2D(new OvalPortrayal2D() {
			// /**
			// *
			// */
			// private static final long serialVersionUID =
			// -666009523425731377L;
			//
			// @Override
			// public void draw(Object object,
			// Graphics2D graphics, DrawInfo2D info) {
			// paint = Color.gray;
			// super.draw(object, graphics, info);
			// }
			// }, 5.0, null, Color.white, true), 0, world
			// .getAgentRange(), Color.green, true)));
		} else {
			// TODO hide thieves
		}

		// set display settings for children
		if (world.isShowChildren()) {
			// mapPortrayal.setPortrayalForClass(Child.class,
			// new MovablePortrayal2D(new CircledPortrayal2D(
			// new LabelledPortrayal2D(new OvalPortrayal2D() {
			// /**
			// *
			// */
			// private static final long serialVersionUID =
			// -666009523425731377L;
			//
			// @Override
			// public void draw(Object object,
			// Graphics2D graphics, DrawInfo2D info) {
			// paint = Color.cyan;
			// super.draw(object, graphics, info);
			// }
			// }, 5.0, null, Color.white, true), 0, world
			// .getAgentRange(), Color.green, true)));
			mapPortrayal.setPortrayalForClass(
					Child.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(new ImagePortrayal2D(
									new ImageIcon("./images/medic.jpg"), 5.0),
									5.0, null, Color.white, true), 0, world
									.getAgentRange(), Color.green, true)));
		} else {
			// TODO hide children
		}

		// set display settings for fruit sources
		if (world.isShowFruitSources()) {
			// mapPortrayal.setPortrayalForClass(FruitSource.class,
			// new MovablePortrayal2D(new CircledPortrayal2D(
			// new LabelledPortrayal2D(new OvalPortrayal2D() {
			// /**
			// *
			// */
			// private static final long serialVersionUID =
			// -666009523425731377L;
			//
			// @Override
			// public void draw(Object object,
			// Graphics2D graphics, DrawInfo2D info) {
			// paint = new Color(0.0f, 1.0f, 0.0f, 0.5f);
			// super.draw(object, graphics, info);
			// }
			// }, 5.0, null, Color.white, true), 0, 5.0,
			// Color.green, true)));
			mapPortrayal.setPortrayalForClass(FruitSource.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(
									new ImagePortrayal2D(new ImageIcon(
											"./images/crystal.jpg"), 5.0), 5.0,
									null, Color.white, true), 0, 5.0,
							Color.green, true)));
		} else {
			// TODO hide fruit sources
		}

		// set display settings for meat sources
		if (world.isShowMeatSources()) {
			// mapPortrayal.setPortrayalForClass(MeatSource.class,
			// new MovablePortrayal2D(new CircledPortrayal2D(
			// new LabelledPortrayal2D(new OvalPortrayal2D() {
			// /**
			// *
			// */
			// private static final long serialVersionUID =
			// -666009523425731377L;
			//
			// @Override
			// public void draw(Object object,
			// Graphics2D graphics, DrawInfo2D info) {
			// paint = new Color(1.0f, 0.0f, 0.0f, 0.5f);
			// super.draw(object, graphics, info);
			// }
			// }, 5.0, null, Color.white, true), 0, 5.0,
			// Color.green, true)));
			mapPortrayal.setPortrayalForClass(MeatSource.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(
									new ImagePortrayal2D(new ImageIcon(
											"./images/vespene.png"), 10.0),
									5.0, null, Color.white, true), 0, 5.0,
							Color.green, true)));
		} else {
			// TODO hide meat sources
		}

		// set display settings for protectors
		if (world.isShowProtectors()) {
			mapPortrayal.setPortrayalForClass(Protector.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(new OvalPortrayal2D() {

								/**
								 * 
								 */
								private static final long serialVersionUID = -6017633649081865999L;

								@Override
								public void draw(Object object,
										Graphics2D graphics, DrawInfo2D info) {
									paint = new Color(255, 255, 255);
									super.draw(object, graphics, info);
								}
							}, 5.0, null, Color.white, true), 0, world
									.getAgentRange(), Color.green, true)));
		} else {
			// TODO hide protectors
		}

		// set display settings for pheromones
		if (world.isShowPheromones()) {
			mapPortrayal.setPortrayalForClass(Pheromone.class,
					new LabelledPortrayal2D(new ImagePortrayal2D(new ImageIcon(
							"./images/reddot.png")), 0.1, null, Color.white,
							true));
		} else {
			mapPortrayal.setPortrayalForClass(Pheromone.class,
					mapPortrayal.getPortrayalForNull());
		}

		// choose a background based on the season
		Color background = Color.black;

		// scheduleRepeatingImmediatelyBefore(new Steppable() {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 6226556354883996558L;
		//
		// @Override
		// public void step(SimState arg0) {
		// ThiefWorld world = (ThiefWorld) arg0;
		//
		// Color background = Color.black;
		//
		// // Color prevernal = new Color(153, 137, 121);
		// // Color vernal = new Color(119, 150, 21);
		// // Color estival = new Color(56, 141, 205);
		// // Color serotinal = new Color(182, 183, 175);
		// // Color autumnal = new Color(239, 106, 31);
		// // Color hibernal = new Color(238, 233, 237);
		// //
		// // long day = (long) (world.schedule.getSteps()
		// // / world.getTimeMultipler() + 1) % 365;
		// // if (day >= 60 && day < 121) {
		// // // prevernal
		// // background = prevernal;
		// // } else if (day >= 121 && day < 166) {
		// // // vernal
		// // background = vernal;
		// // } else if (day >= 166 && day < 227) {
		// // // estival
		// // background = estival;
		// // } else if (day >= 227 && day < 258) {
		// // // serotinal
		// // background = serotinal;
		// // } else if (day >= 258 && day < 305) {
		// // // autumnal
		// // background = autumnal;
		// // } else {
		// // // hibernal
		// // background = hibernal;
		// // }
		//
		// display.reset();
		// display.setBackdrop(background);
		//
		// display.repaint();
		// }
		// });

		display.reset();
		display.setBackdrop(background);

		display.repaint();
	}

	/**
	 * Initializes the GUI window.
	 */
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

	/**
	 * Disposes of the allocated GUI resources and exists the simulation.
	 */
	@Override
	public void quit() {
		super.quit();

		if (displayFrame != null)
			displayFrame.dispose();

		displayFrame = null;
		display = null;
	}
}
