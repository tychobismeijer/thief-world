package thiefworld.main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.ImagePortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
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
	 * @uml.property  name="display"
	 * @uml.associationEnd  
	 */
	public Display2D display;

	/**
	 * @uml.property  name="displayFrame"
	 * @uml.associationEnd  
	 */
	public JFrame displayFrame;

	/**
	 * @uml.property  name="mapPortrayal"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public ContinuousPortrayal2D mapPortrayal = new ContinuousPortrayal2D();

	public ThiefWorldWithUI() {
		super(new ThiefWorld(System.currentTimeMillis()));
	}

	public ThiefWorldWithUI(SimState state) {
		super(state);
	}

	private void clearWorld() {
		ThiefWorld world = (ThiefWorld) state;
		
		world.childrenBag.clear();
		world.fruitSourcesBag.clear();
		world.gatherersBag.clear();
		world.huntersBag.clear();
		world.meatSourcesBag.clear();
		world.nestsBag.clear();
		world.protectorsBag.clear();
		world.thievesBag.clear();
	}

	@Override
	public void finish() {
		clearWorld();
	}

	public Inspector getInspector() {
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}

	public Object getSimulationInspectedObject() {
		return state;
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
	};

	/**
	 * Loads the GUI components.
	 */
	@Override
	public void load(SimState stateArg) {
		super.load(stateArg);

		// clear the existing components
		clearWorld();

		setupPortrayals();
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

	/**
	 * Sets the display settings for the various agents in the system.
	 */
	public void setupPortrayals() {
		ThiefWorld world = (ThiefWorld) state;

		mapPortrayal.setField(world.map);

		mapPortrayal.setPortrayalForNull(new ImagePortrayal2D(new ImageIcon(
				"./images/none.png")));

		// choose a background based on the season
		Color background = Color.black;

		// schedules a recurring display method for changing the displayed
		// components if that is required (i.e. activating/deactivating
		// components)
		scheduleRepeatingImmediatelyBefore(new Steppable() {

			/**
			 *
			 */
			private static final long serialVersionUID = 6226556354883996558L;

			@Override
			public void step(SimState arg0) {
				ThiefWorld world = (ThiefWorld) arg0;

				// set display settings for hunters
				if (world.isShowHunters()) {
					mapPortrayal
							.setPortrayalForClass(
									Hunter.class,
									new MovablePortrayal2D(
											new CircledPortrayal2D(
													new LabelledPortrayal2D(
															new ImagePortrayal2D(
																	new ImageIcon(
																			"./images/marine.png"),
																	5.0), 5.0,
															null, Color.white,
															true), 0, world
															.getAgentRange(),
													Color.green, true)));
				} else {
					// hide hunters
					mapPortrayal.setPortrayalForClass(Hunter.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for gatherers
				if (world.isShowGatherers()) {
					mapPortrayal
							.setPortrayalForClass(
									Gatherer.class,
									new MovablePortrayal2D(
											new CircledPortrayal2D(
													new LabelledPortrayal2D(
															new ImagePortrayal2D(
																	new ImageIcon(
																			"./images/gatherer.png"),
																	5.0), 5.0,
															null, Color.white,
															true), 0, world
															.getAgentRange(),
													Color.green, true)));
				} else {
					// hide gatherers
					mapPortrayal.setPortrayalForClass(Gatherer.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for nests
				if (world.isShowNests()) {
					mapPortrayal
							.setPortrayalForClass(
									Nest.class,
									new MovablePortrayal2D(
											new CircledPortrayal2D(
													new LabelledPortrayal2D(
															new ImagePortrayal2D(
																	new ImageIcon(
																			"./images/nest.png"),
																	10.0), 5.0,
															null, Color.white,
															true), 0, world
															.getAgentRange(),
													Color.green, true)));
				} else {
					// hide nests
					mapPortrayal.setPortrayalForClass(Nest.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for thieves
				if (world.isShowThieves()) {
					mapPortrayal
							.setPortrayalForClass(
									Thief.class,
									new MovablePortrayal2D(
											new CircledPortrayal2D(
													new LabelledPortrayal2D(
															new ImagePortrayal2D(
																	new ImageIcon(
																			"./images/ghost.png"),
																	5.0), 5.0,
															null, Color.white,
															true), 0, world
															.getAgentRange(),
													Color.green, true)));
				} else {
					// hide thieves
					mapPortrayal.setPortrayalForClass(Thief.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for children
				if (world.isShowChildren()) {
					mapPortrayal
							.setPortrayalForClass(
									Child.class,
									new MovablePortrayal2D(
											new CircledPortrayal2D(
													new LabelledPortrayal2D(
															new ImagePortrayal2D(
																	new ImageIcon(
																			"./images/medic.png"),
																	5.0), 5.0,
															null, Color.white,
															true), 0, world
															.getAgentRange(),
													Color.green, true)));
				} else {
					// hide children
					mapPortrayal.setPortrayalForClass(Child.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for fruit sources
				if (world.isShowFruitSources()) {
					mapPortrayal.setPortrayalForClass(FruitSource.class,
							new MovablePortrayal2D(new CircledPortrayal2D(
									new LabelledPortrayal2D(
											new ImagePortrayal2D(new ImageIcon(
													"./images/crystal.png"),
													5.0), 5.0, null,
											Color.white, true), 0, 5.0,
									Color.green, true)));
				} else {
					// hide fruit sources
					mapPortrayal.setPortrayalForClass(FruitSource.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for meat sources
				if (world.isShowMeatSources()) {
					mapPortrayal.setPortrayalForClass(MeatSource.class,
							new MovablePortrayal2D(new CircledPortrayal2D(
									new LabelledPortrayal2D(
											new ImagePortrayal2D(new ImageIcon(
													"./images/vespene.png"),
													10.0), 5.0, null,
											Color.white, true), 0, 5.0,
									Color.green, true)));
				} else {
					// hide meat sources
					mapPortrayal.setPortrayalForClass(MeatSource.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for protectors
				if (world.isShowProtectors()) {
					mapPortrayal.setPortrayalForClass(Protector.class,
							new MovablePortrayal2D(new CircledPortrayal2D(
									new LabelledPortrayal2D(
											new ImagePortrayal2D(new ImageIcon(
													"./images/tank.png"), 5.0),
											5.0, null, Color.white, true), 0,
									world.getAgentRange(), Color.green, true)));
				} else {
					// hide protectors
					mapPortrayal.setPortrayalForClass(Protector.class,
							mapPortrayal.getPortrayalForNull());
				}

				// set display settings for pheromones
				if (world.isShowPheromones()) {
					mapPortrayal.setPortrayalForClass(Pheromone.class,
							new LabelledPortrayal2D(new ImagePortrayal2D(
									new ImageIcon("./images/reddot.png")), 0.1,
									null, Color.white, true));
				} else {
					mapPortrayal.setPortrayalForClass(Pheromone.class,
							mapPortrayal.getPortrayalForNull());
				}

				display.reset();

				display.repaint();
			}
		});

		display.reset();
		display.setBackdrop(background);

		display.repaint();
	}

	/**
	 * Starts the GUI.
	 */
	@Override
	public void start() {
		super.start();
		setupPortrayals();
	}
}
