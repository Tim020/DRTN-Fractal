package io.github.teamfractal.actors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.AbstractAnimationScreen;
import io.github.teamfractal.screens.GameScreen;

public class GameScreenActors {
	private final Stage stage;
	private RoboticonQuest game;
	private GameScreen screen;
	private Label phaseInfo;
	private Label playerStats;
	private TextButton buyLandPlotBtn;
	private TextButton installRoboticonBtn;
	private TextButton installRoboticonBtnCancel;
	private SelectBox<String> installRoboticonSelect;
	private Label plotStats;
	private TextButton nextButton;
	private boolean listUpdated;

	/**
	 * Initialise the main game screen components.
	 * @param game         The game manager {@link RoboticonQuest}
	 * @param screen       Current screen to display on.
	 */
	public GameScreenActors(final RoboticonQuest game, GameScreen screen) {
		this.game = game;
		this.screen = screen;
		this.stage = screen.getStage();
	}

	/**
	 * Setup buttons.
	 */
	public void initialiseButtons() {
		// Create UI components
		phaseInfo = new Label("", game.skin);
		plotStats = new Label("", game.skin);
		playerStats = new Label("Ore:\nEnergy:\nFood\nMoney:", game.skin);
		nextButton = new TextButton("Next Phase", game.skin);
		buyLandPlotBtn = new TextButton("Buy Plot", game.skin);
		createRoboticonInstallMenu();

		// Adjust properties.
		listUpdated = false;
		hideInstallRoboticon();
		buyLandPlotBtn.setVisible(false);
		buyLandPlotBtn.pad(2, 10, 2, 10);
		phaseInfo.setAlignment(Align.right);
		plotStats.setAlignment(Align.topLeft);
		installRoboticonSelect.setSelected(null);

		// Bind events
		bindEvents();

		// Add to the stage for rendering.
		stage.addActor(nextButton);
		stage.addActor(buyLandPlotBtn);
		stage.addActor(installRoboticonTable);
		stage.addActor(phaseInfo);
		stage.addActor(plotStats);
		stage.addActor(playerStats);

		// Update UI positions.
		AbstractAnimationScreen.Size size = screen.getScreenSize();
		//resizeScreen(size.Width, size.Height);
		phaseInfo.setWidth(size.Width - 10);
		phaseInfo.setPosition(0, size.Height - 20);

		playerStats.setPosition(10, size.Height - playerStats.getHeight() - 10);
		nextButton.setPosition(size.Width - nextButton.getWidth() - 10, 10);
	}

	private Table installRoboticonTable;

	/**
	 * Create the roboticon installation menu.
	 */
	private void createRoboticonInstallMenu() {
		installRoboticonTable = new Table();
		Table t = installRoboticonTable;

		installRoboticonSelect = new SelectBox<String>(game.skin);
		installRoboticonSelect.setItems(game.getPlayer().getRoboticonAmountList());

		Label installRoboticonLabel = new Label("Install Roboticon: ", game.skin);
		installRoboticonBtn = new TextButton("Confirm", game.skin);
		installRoboticonBtnCancel = new TextButton("Cancel", game.skin);

		t.add(installRoboticonLabel).colspan(2);
		t.row();
		t.add(installRoboticonSelect).colspan(2);
		t.row();
		t.add(installRoboticonBtn);
		t.add(installRoboticonBtnCancel);
		t.row();
	}


	/**
	 * Bind all button events.
	 */
	private void bindEvents() {
		buyLandPlotBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				buyLandPlotFunction();
			}
		});

		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				nextButtonFunction();
			}
		});

		installRoboticonBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				installRoboticonFunction();
			}
		});

		installRoboticonBtnCancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				hideInstallRoboticon();
			}
		});
	}

	/**
	 * Tile click callback event.
	 *
	 * @param plot The landplot tileClicked.
	 * @param x    Current mouse x position
	 * @param y    Current mouse y position
	 */
	public void tileClicked(LandPlot plot, float x, float y) {
		Player player = game.getPlayer();

		switch (game.getPhase()) {
			// Phase 1:
			// Purchase LandPlot.
			case 1:
				buyLandPlotBtn.setPosition(x + 10, y);
				if (game.canPurchaseLandThisTurn()
						&& !plot.hasOwner()) {
					buyLandPlotBtn.setDisabled(false);
				} else {
					buyLandPlotBtn.setDisabled(true);
				}
				showPlotStats(plot, x + 10, y);

				buyLandPlotBtn.setVisible(true);
				break;

			// Phase 3:
			// Install Roboticon 
			case 3:
				if (player == plot.getOwner()) {
					installRoboticonTable.setPosition(x, y, Align.center);
					updateRoboticonList();
					installRoboticonTable.setVisible(true);
				} else {
					hideInstallRoboticon();
				}
		}


	}

	/**
	 * Update the dropdown list of roboticon available.
	 */
	private void updateRoboticonList() {
		installRoboticonSelect.setItems(game.getPlayer().getRoboticonAmountList());
	}

	/**
	 * Get the "Buy Land" button.
	 * @return "Buy Land" button.
	 */
	public TextButton getBuyLandPlotBtn() {
		return buyLandPlotBtn;
	}

	/**
	 * Updates the UI display.
	 */
	public void textUpdate() {
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase() + " - " + game.getPhaseString();
		phaseInfo.setText(phaseText);

		String statText = "Ore: " + game.getPlayer().getOre()
				+ "\nEnergy: " + game.getPlayer().getEnergy()
				+ "\nFood: " + game.getPlayer().getFood()
				+ "\nMoney: " + game.getPlayer().getMoney();

		playerStats.setText(statText);
	}

	/**
	 * Callback event on window updates,
	 * to adjust the UI components position relative to the screen.
	 *
	 * @param width    The new Width.
	 * @param height   The new Height.
	 */
	/*
	public void resizeScreen(float width, float height) {
		float topBarY = height - 20;
		phaseInfo.setWidth(width - 10);
		phaseInfo.setPosition(0, topBarY);

		playerStats.setPosition(10, height - playerStats.getHeight());
		nextButton.setPosition(width - nextButton.getWidth() - 10, 10);
	}
	*/

	/**
	 * Show plot information about current selected stats.
	 * @param plot           The land plot to show info.
	 * @param x              The <i>x</i> position to display the information.
	 * @param y              The <i>y</i> position to display the information.
	 */
	private void showPlotStats(LandPlot plot, float x, float y) {
		String plotStatText = "Ore: " + plot.getResource(ResourceType.ORE) + "\n"
				+ "Energy: " + plot.getResource(ResourceType.ENERGY) + "\n"
				+ "Food: " + plot.getResource(ResourceType.FOOD);

		plotStats.setText(plotStatText);
		plotStats.setPosition(x, y);
		plotStats.setVisible(true);
	}

	public void updateRoboticonSelection() {
		// TODO: Implement this method
	}

	/**
	 * Hide "Buy Land" button and plot information.
	 */
	public void hideBuyLand() {
		buyLandPlotBtn.setVisible(false);
		plotStats.setVisible(false);
	}

	/**
	 * Hide install roboticon dialog.
	 */
	public void hideInstallRoboticon() {
		installRoboticonTable.setVisible(false);
	}

	/**
	 * Check if install roboticon dialog is visible.
	 * @return   <code>true</code> if is visible, <code>false</code> if not visible.
	 */
	public boolean installRoboticonVisible() {
		return installRoboticonTable.isVisible();
	}

	/**
	 * Purchases the landplot that the player has clicked on if it is not already owned.
	 */
	public void buyLandPlotFunction(){
		hideBuyLand();
		if (buyLandPlotBtn.isDisabled()) {
			return ;
		}
		LandPlot selectedPlot = screen.getSelectedPlot();

		if (selectedPlot.hasOwner()) {
			return;
		}

		Player player = game.getPlayer();
		if (player.purchaseLandPlot(selectedPlot)) {
			TiledMapTileLayer.Cell playerTile = selectedPlot.getPlayerTile();
			playerTile.setTile(screen.getPlayerTile(player));
			textUpdate();

			nextButton.setVisible(true);
		}
	}
	/**
	 * The function that advances the phase of the game when the next button is clicked
	 */
	public void nextButtonFunction(){
		if (nextButton.isDisabled()) {
			return ;
		}
		if(game.canPurchaseLandThisTurn() == false){
			buyLandPlotBtn.setVisible(false);
			plotStats.setVisible(false);
			hideInstallRoboticon();
			game.nextPhase();
			installRoboticonSelect.setItems(game.getPlayer().getRoboticonAmountList());
			textUpdate();
		}
	}
	/**
	 * Presents the user with a list of roboticons that they can install on the land plot that they have clicked on. Once they have selected
	 * a roboticon to install, a function is called that will install the roboticon.
	 */
	private void installRoboticonFunction(){
		if (installRoboticonBtn.isDisabled()) {
			return ;
		}
		if (!listUpdated) { //prevents updating selection list from updating change listener
			LandPlot selectedPlot = screen.getSelectedPlot();
			if (selectedPlot.getOwner() == game.getPlayer() && !selectedPlot.hasRoboticon()) {
				Roboticon roboticon = null;
				ResourceType type = ResourceType.Unknown;
				int selection = installRoboticonSelect.getSelectedIndex();

				Array<Roboticon> roboticons = game.getPlayer().getRoboticons();
				switch (selection) {
					case 0:
						type = ResourceType.ORE;
						break;
					case 1:
						type = ResourceType.ENERGY;
						break;
					case 2:
						type = ResourceType.FOOD;
						break;
					default:
						type = ResourceType.Unknown;
						break;
				}

				for (Roboticon r : roboticons) {
					if (!r.isInstalled() && r.getCustomisation() == type) {
						roboticon = r;
						break;
					}
				}

				if (roboticon != null) {
					installRoboticonFunction(selectedPlot,roboticon);
					textUpdate();
				}

				hideInstallRoboticon();
				updateRoboticonList();

			} else listUpdated = false;
		}
	}
	/**
	 * Installs the specified roboticon on the specified land plot.
	 * @param selectedPlot The plot that has been selected
	 * @param roboticon The roboticon that is to be installed
	 */
	public void installRoboticonFunction(LandPlot selectedPlot, Roboticon roboticon){
        selectedPlot.installRoboticon(roboticon);
        TiledMapTileLayer.Cell roboticonTile = selectedPlot.getRoboticonTile();
        roboticonTile.setTile(screen.getResourcePlayerTile(selectedPlot.getOwner(),roboticon.getCustomisation()));
        selectedPlot.setHasRoboticon(true);
    }

    public void switchNextButton() {
		nextButton.setVisible(!nextButton.isVisible());
	}

	public void setNextButtonVisibility(boolean visible) {
		nextButton.setVisible(visible);
	}
}
