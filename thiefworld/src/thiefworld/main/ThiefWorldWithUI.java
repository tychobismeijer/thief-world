package thiefworld.main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.simple.ImagePortrayal2D;

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
	public Display2D display;

	public JFrame displayFrame;

	public ContinuousPortrayal2D mapPortrayal = new ContinuousPortrayal2D();

	public ThiefWorldWithUI() {
		super(new ThiefWorld(System.currentTimeMillis()));
	}

	public ThiefWorldWithUI(SimState state) {
		super(state);
	}

	private void clearWorld() {
		ThiefWorld world = (ThiefWorld) state;
		
		world.data.childrenBag.clear();
		world.data.fruitSourcesBag.clear();
		world.data.gatherersBag.clear();
		world.data.huntersBag.clear();
		world.data.meatSourcesBag.clear();
		world.data.nestsBag.clear();
		world.data.protectorsBag.clear();
		world.data.thievesBag.clear();
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
		scheduleRepeatingImmediatelyBefore(new DisplayUpdater(this));

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
