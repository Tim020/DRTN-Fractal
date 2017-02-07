package io.github.teamfractal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.teamfractal.animation.AnimationPhaseTimeout;
import io.github.teamfractal.animation.AnimationShowPlayer;
import io.github.teamfractal.animation.IAnimationFinish;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.PlotEffect;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.screens.MainMenuScreen;
import io.github.teamfractal.screens.ResourceMarketScreen;
import io.github.teamfractal.screens.RoboticonMarketScreen;
import io.github.teamfractal.util.PlotManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is the main game boot up class.
 * It will set up all the necessary classes.
 */
public class RoboticonQuest extends Game {
	static RoboticonQuest _instance;
	public static RoboticonQuest getInstance() {
		return _instance;
	}


	private PlotManager plotManager;
	SpriteBatch batch;
	public Skin skin;
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	private int phase;
	private int currentPlayer;
	public ArrayList<Player> playerList;
	public Market market;
	private int landBoughtThisTurn;

	private PlotEffect[] plotEffects;
	private float effectChance;

	public int getPlayerIndex (Player player) {
		return playerList.indexOf(player);
	}

	public TiledMap tmx;
	
	public RoboticonQuest(){
		_instance = this;
		reset();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setupSkin();

		gameScreen = new GameScreen(this);

		// Setup other screens.
		mainMenuScreen = new MainMenuScreen(this);

		//Setup tile and player effects for later application
		setupEffects();

		setScreen(mainMenuScreen);
	}

	public Batch getBatch() {
		return batch;
	}

	/**
	 * Setup the default skin for GUI components.
	 */
	private void setupSkin() {
		skin = new Skin(
			Gdx.files.internal("skin/skin.json"),
			new TextureAtlas(Gdx.files.internal("skin/skin.atlas"))
		);
	}

	/**
	 * Clean up
	 */
	@Override
	public void dispose () {
		mainMenuScreen.dispose();
		gameScreen.dispose();
		skin.dispose();
		batch.dispose();
	}
	
	public int getPhase(){
		return this.phase;
	}

	public void reset() {
		this.currentPlayer = 0;
		this.phase = 0;

		Player player1 = new Player(this);
		Player player2 = new Player(this);
		this.playerList = new ArrayList<Player>();
		this.playerList.add(player1);
		this.playerList.add(player2);
		this.currentPlayer = 0;
		this.market = new Market();
		plotManager = new PlotManager();
	}

	public void implementPhase () {
		System.out.println("RoboticonQuest::nextPhase -> newPhaseState: " + phase);
		switch (phase) {
			// Phase 2: Purchase Roboticon
			case 2:
				RoboticonMarketScreen roboticonMarket = new RoboticonMarketScreen(this);
				roboticonMarket.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, phase, 30));
				setScreen(roboticonMarket);
				break;

			// Phase 3: Roboticon Customisation
			case 3:
				AnimationPhaseTimeout timeoutAnimation = new AnimationPhaseTimeout(getPlayer(), this, phase, 30);
				gameScreen.addAnimation(timeoutAnimation);
				timeoutAnimation.setAnimationFinish(new IAnimationFinish() {
					@Override
					public void OnAnimationFinish() {
						gameScreen.getActors().hideInstallRoboticon();
					}
				});
				gameScreen.getActors().updateRoboticonSelection();
				setScreen(gameScreen);
				break;

			// Phase 4: Purchase Resource
			case 4:
				generateResources();
				break;

			// Phase 5: Generate resource for player.
			case 5:
				setScreen(new ResourceMarketScreen(this));
				break;
			

			// End phase - CLean up and move to next player.
			case 6:
				phase = 1;

				if(checkGameEnded() == true){
					Player winner = getWinner();
					// TODO: 01/02/2017 A function here that creates the end game screen 
				}
				this.nextPlayer();
				// No "break;" here!
				// Let the game to do phase 1 preparation.

			// Phase 1: Enable of purchase LandPlot
			case 1:
				setScreen(gameScreen);
				landBoughtThisTurn = 0;
				gameScreen.addAnimation(new AnimationShowPlayer(getPlayerInt() + 1));

				clearEffects();
				setEffects();
				break;
		}

		if (gameScreen != null)
			gameScreen.getActors().textUpdate();
	}

	public void nextPhase() {
		phase += 1;
		implementPhase();
	}

	public void setPhase(int phase) {
		this.phase = phase;
		implementPhase();
	}

	/**
	 * Phase 4: generate resources.
	 */
	private void generateResources() {
		// Switch back to purchase to game screen.
		setScreen(gameScreen);

		// Generate resources.
		Player p = getPlayer();
		p.generateResources();
	}

	/**
	 * Event callback on player bought a {@link io.github.teamfractal.entity.LandPlot}
	 */
	public void landPurchasedThisTurn() {
		landBoughtThisTurn ++;
	}

	public boolean canPurchaseLandThisTurn () {
		return landBoughtThisTurn < 1;
	}

	public String getPhaseString () {
		int phase = getPhase();

		switch(phase){
			case 1:
				return "Buy Land Plot";

			case 2:
				return "Purchase Roboticons";

			case 3:
				return "Install Roboticons";

			case 4:
				return "Resource Generation";

			case 5:
				return "Resource Auction";

			default:
				return "Unknown phase";
		}

	}

	public Player getPlayer(){
		return this.playerList.get(this.currentPlayer);
	}
	
	public int getPlayerInt(){
		return this.currentPlayer;
	}

	public void nextPlayer(){
		if (this.currentPlayer == playerList.size() - 1){
			this.currentPlayer = 0; 
		}
		else{
			this.currentPlayer ++;
		}
	}

	public PlotManager getPlotManager() {
		return plotManager;
	}

	private void setupEffects() {
		//Initialise the fractional chance of any given effect being applied at the start of a round
		effectChance = (float) 1;

		plotEffects = new PlotEffect[1];

		plotEffects[0] = new PlotEffect("Duck-Related Disaster", "A horde of ducks infest your most " +
				"food-producing tile, ruining many of the crops on it. Food production on that tile is reduced by " +
				"80% for this turn.", new Float[]{(float) 1, (float) 1, (float) 0.2}, new Runnable() {
			@Override
			public void run() {
				if (getPlayer().getLandList().size() == 0) {
					return;
				}

				LandPlot foodProducer = getPlayer().getLandList().get(0);

				for (LandPlot plot : getPlayer().getLandList()) {
					if (plot.getResource(ResourceType.FOOD) > foodProducer.getResource(ResourceType.FOOD)) {
						foodProducer = plot;
					}
				}
				plotEffects[0].impose(foodProducer, 1);
			}
		});

		for (PlotEffect PE : plotEffects) {
			PE.constructCustomOverlay(gameScreen);
		}
	}

	private void setEffects() {
		Random RNGesus = new Random();

		for (PlotEffect PE : plotEffects) {
			if (RNGesus.nextFloat() < effectChance) {
				PE.executeRunnable();

				gameScreen.addOverlay(PE.overlay());
			}
		}
	}

	private void clearEffects() {
		for (PlotEffect PE : plotEffects) {
			PE.revertAll();
		}
	}

	/**
	 * Checks whether the game has ended based on whether all of the tiles have been claimed
	 * @return Returns true if ended, false if not
	 */
	public boolean checkGameEnded(){
		boolean ended = true;
		LandPlot[][] plots = plotManager.getLandPlots();
		for(int x = 0; x < plots[0].length ; x++){
			for(int y = 0; y < plots [1].length ; y++){
				if(plots[x][y].hasOwner() == false){
					ended = false;
				}
			}
		}
		return ended;
	}

	/**
	 * Returns the winner of the game, based on which player has the highest score
	 * @return
	 */
	public Player getWinner(){
		Player winner = null;
		if(playerList.get(0).calculateScore() > playerList.get(1).calculateScore()) {
			winner = playerList.get(0);
		}
		else{
				winner = playerList.get(1);
			}
		return winner;
	}

	public float getEffectChance() {
		return effectChance;
	}

	public void setEffectChance(float effectChance) {
		this.effectChance = effectChance;
	}
}
