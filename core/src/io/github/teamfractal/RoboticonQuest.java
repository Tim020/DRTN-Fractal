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
import io.github.teamfractal.entity.*;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.*;
import io.github.teamfractal.util.PlotManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is the main game boot up class.
 * It will set up all the necessary classes.
 */
public class RoboticonQuest extends Game {
    private static RoboticonQuest _instance;
	public RoboticonMarketScreen roboticonMarket;
	public TiledMap tmx;
	public Skin skin;
	public GameScreen gameScreen;
	public Market market;
    public PlotManager plotManager;
    private MainMenuScreen mainMenuScreen;
    private ArrayList<Player> playerList;
    private SpriteBatch batch;
    private int phase;
	private int currentPlayer;
	private int landBoughtThisTurn;
	private PlotEffect[] plotEffects;
	private float effectChance;
	private int currentPlayerIndex;

	public RoboticonQuest() {
		_instance = this;
		reset(false);
	}

	public static RoboticonQuest getInstance() {
		return _instance;
	}





	public int getPlayerIndex (Player player) {

		return playerList.indexOf(player);
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

	public void setPhase(int phase) {
		this.phase = phase;
		implementPhase();
	}

	public void reset(boolean AI) {
        this.currentPlayerIndex = 0;
        this.phase = 0;
        plotManager = new PlotManager();
        Player player1;
        Player player2;
        if (AI) {
            player1 = new AIPlayer(this);
            player2 = new Player(this);
        } else{
            player1 = new Player(this);
            player2 = new Player(this);
        }

        this.playerList = new ArrayList<Player>();
        this.playerList.add(player1);
		this.playerList.add(player2);
        this.currentPlayerIndex = 0;
        this.market = new Market();

    }

    private void implementPhase() {
        System.out.println("RoboticonQuest::nextPhase -> newPhaseState: " + phase);
		switch (phase) {
			// Phase 2: Purchase Roboticon
			case 2:

				this.roboticonMarket = new RoboticonMarketScreen(this);
                this.roboticonMarket.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, phase, 30));
                setScreen(this.roboticonMarket);

				this.getPlayer().takeTurn();
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

				this.getPlayer().takeTurn();
                break;

			// Phase 4: Purchase Resource
			case 4:
				generateResources();
				this.getPlayer().takeTurn();
                break;

			// Phase 5: Generate resource for player.
			case 5:
				setScreen(new ResourceMarketScreen(this));

				this.getPlayer().takeTurn();
				break;


			// End phase - CLean up and move to next player.
			case 6:
				phase = 1;

                if (checkGameEnded()) {

					setScreen(new EndGameScreen(this));
					break;
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

        		this.getPlayer().takeTurn();
				break;
		}


		if (gameScreen != null)
			gameScreen.getActors().textUpdate();
	}

	public void nextPhase() {
		phase += 1;
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

        return this.playerList.get(this.currentPlayerIndex);
    }

    public int getPlayerInt() {
        return this.currentPlayerIndex;
    }

    void nextPlayer() {
        if (this.currentPlayerIndex == playerList.size() - 1) {
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex++;
        }

    }



	private void setupEffects() {
		//Initialise the fractional chance of any given effect being applied at the start of a round
		effectChance = (float) 0.05;

		plotEffects = new PlotEffect[1];

		plotEffects[0] = new PlotEffect("Duck-Related Disaster", "A horde of ducks infest your most " +
				"food-producing tile, ruining many of the crops on it. Food\nproduction on that tile is reduced by " +
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
			PE.constructOverlay(gameScreen);
		}
	}

	private void setEffects() {
		Random RNGesus = new Random();

		for (PlotEffect PE : plotEffects) {
			if (RNGesus.nextFloat() <= effectChance) {
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
    private boolean checkGameEnded() {
        boolean ended = true;
		LandPlot[][] plots = plotManager.getLandPlots();
        for (LandPlot[] plot : plots) {
            for (LandPlot aPlot : plot) {
                if (aPlot == null) {
					ended = false;
                }
            }
        }
        return ended;
	}

	/**
	 * Returns the winner of the game, based on which player has the highest score
     * @return String returning the winning player
     */

	public String getWinner(){
        String winner;
        if(playerList.get(0).calculateScore() > playerList.get(1).calculateScore()) {
			winner = "Player 1";
		}
		else{
				winner = "Player 2";
			}
		return winner;
	}

	public float getEffectChance() {
		return effectChance;
	}

	public void setEffectChance(float effectChance) {
		this.effectChance = effectChance;
	}

	/**
	 * Getter for the players of the game.
	 * @return The array containing the two players
	 */
	public ArrayList<Player> getPlayerList(){
		return this.playerList;
	}
}
