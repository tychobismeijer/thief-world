package thiefworld.main;

import java.awt.Color;

import javax.swing.ImageIcon;

import sim.engine.SimState;
import sim.engine.Steppable;
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

final class DisplayUpdater implements Steppable {
	/**
	 * 
	 */
	private ThiefWorldWithUI gui;

	/**
	 * @param thiefWorldWithUI
	 */
	DisplayUpdater(ThiefWorldWithUI thiefWorldWithUI) {
		gui = thiefWorldWithUI;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 6226556354883996558L;

	@Override
	public void step(SimState arg0) {
		ThiefWorld world = (ThiefWorld) arg0;

		displayGUIEntities(world);
		
		gui.display.reset();

		gui.display.repaint();
	}

	private void displayGUIEntities(ThiefWorld world) {
		// set display settings for hunters
		displayHunters(world);

		// set display settings for gatherers
		displayGatherers(world);

		// set display settings for nests
		displayNests(world);

		// set display settings for thieves
		displayThieves(world);
		

		// set display settings for children
		displayChildren(world);

		// set display settings for fruit sources
		displayFruitSources(world);

		// set display settings for meat sources
		displayMeatSources(world);

		// set display settings for protectors
		displayProtectors(world);

		// set display settings for pheromones
		displayPheromones(world);
	}

	private void displayPheromones(ThiefWorld world) {
		if (world.isShowPheromones()) {
			gui.mapPortrayal.setPortrayalForClass(Pheromone.class,
					new LabelledPortrayal2D(new ImagePortrayal2D(
							new ImageIcon("./images/reddot.png")), 0.1,
							null, Color.white, true));
		} else {
			gui.mapPortrayal.setPortrayalForClass(Pheromone.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayProtectors(ThiefWorld world) {
		if (world.isShowProtectors()) {
			gui.mapPortrayal.setPortrayalForClass(Protector.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(
									new ImagePortrayal2D(new ImageIcon(
											"./images/tank.png"), 5.0),
									5.0, null, Color.white, true), 0,
							world.getAgentRange(), Color.green, true)));
		} else {
			// hide protectors
			gui.mapPortrayal.setPortrayalForClass(Protector.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayMeatSources(ThiefWorld world) {
		if (world.isShowMeatSources()) {
			gui.mapPortrayal.setPortrayalForClass(MeatSource.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(
									new ImagePortrayal2D(new ImageIcon(
											"./images/vespene.png"),
											10.0), 5.0, null,
									Color.white, true), 0, 5.0,
							Color.green, true)));
		} else {
			// hide meat sources
			gui.mapPortrayal.setPortrayalForClass(MeatSource.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayFruitSources(ThiefWorld world) {
		if (world.isShowFruitSources()) {
			gui.mapPortrayal.setPortrayalForClass(FruitSource.class,
					new MovablePortrayal2D(new CircledPortrayal2D(
							new LabelledPortrayal2D(
									new ImagePortrayal2D(new ImageIcon(
											"./images/crystal.png"),
											5.0), 5.0, null,
									Color.white, true), 0, 5.0,
							Color.green, true)));
		} else {
			// hide fruit sources
			gui.mapPortrayal.setPortrayalForClass(FruitSource.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayChildren(ThiefWorld world) {
		if (world.isShowChildren()) {
			gui.mapPortrayal
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
			gui.mapPortrayal.setPortrayalForClass(Child.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayThieves(ThiefWorld world) {
		if (world.isShowThieves()) {
			gui.mapPortrayal
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
			gui.mapPortrayal.setPortrayalForClass(Thief.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayNests(ThiefWorld world) {
		if (world.isShowNests()) {
			gui.mapPortrayal
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
			gui.mapPortrayal.setPortrayalForClass(Nest.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayGatherers(ThiefWorld world) {
		if (world.isShowGatherers()) {
			gui.mapPortrayal
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
			gui.mapPortrayal.setPortrayalForClass(Gatherer.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}

	private void displayHunters(ThiefWorld world) {
		if (world.isShowHunters()) {
			gui.mapPortrayal
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
			gui.mapPortrayal.setPortrayalForClass(Hunter.class,
					gui.mapPortrayal.getPortrayalForNull());
		}
	}
}