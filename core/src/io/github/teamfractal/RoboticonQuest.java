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
import io.github.teamfractal.entity.AIPlayer;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.*;
import io.github.teamfractal.util.PlotManager;

import java.util.ArrayList;

/**
 * This is the main game boot up class.
 * It will set up all the necessary classes.
 */
public class RoboticonQuest extends Game {
    private static RoboticonQuest _instance;
    public Skin skin;
    public GameScreen gameScreen;
	public Market market;
	public RoboticonMarketScreen roboticonMarket;
	public TiledMap tmx;
    public PlotManager plotManager;
    private MainMenuScreen mainMenuScreen;
    private ArrayList<Player> playerList;
    private SpriteBatch batch;
    private int phase;
    private int currentPlayerIndex;
    private int landBoughtThisTurn;

	public RoboticonQuest(){
		_instance = this;
		reset(false);
	}

	public static RoboticonQuest getInstance() {
		return _instance;
	}

	public int getPlayerIndex(Player player) {
		return playerList.indexOf(player);
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setupSkin();
		
	
		gameScreen = new GameScreen(this);

		// Setup other screens.
		mainMenuScreen = new MainMenuScreen(this);
		

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

	public void reset(boolean AI) {
        this.currentPlayerIndex = 0;
        this.phase = 0;
        plotManager = new PlotManager();
        Player player1;
        Player player2;
        if (AI == true){
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

	public void nextPhase () {
		int newPhaseState = phase + 1;
		phase = newPhaseState;
		

		System.out.println("RoboticonQuest::nextPhase -> newPhaseState: " + newPhaseState);
		switch (newPhaseState) {
			// Phase 2: Purchase Roboticon
			case 2:
				this.roboticonMarket = new RoboticonMarketScreen(this);
				this.roboticonMarket.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, newPhaseState, 30));
				setScreen(this.roboticonMarket);

                break;

			// Phase 3: Roboticon Customisation
			case 3:
				AnimationPhaseTimeout timeoutAnimation = new AnimationPhaseTimeout(getPlayer(), this, newPhaseState, 30);
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
                if (this.getPlayer() instanceof AIPlayer) {
                    this.getPlayer().takeTurn(4);
                }
                break;

			// Phase 5: Generate resource for player.
			case 5:
				setScreen(new ResourceMarketScreen(this));

				break;
			

			// End phase - CLean up and move to next player.
			case 6:
				if(checkGameEnded() == true){
					
					setScreen(new EndGameScreen(this));
					break;
				}
				phase = newPhaseState = 1;
				this.nextPlayer();

				// No "break;" here!
				// Let the game to do phase 1 preparation.

			// Phase 1: Enable of purchase LandPlot
			case 1:
				setScreen(gameScreen);
				landBoughtThisTurn = 0;
				gameScreen.addAnimation(new AnimationShowPlayer(getPlayerInt() + 1));

                break;
        }

        if (this.getPlayer() instanceof AIPlayer) {
            this.getPlayer().takeTurn(newPhaseState);
        }

		if (gameScreen != null)
			gameScreen.getActors().textUpdate();
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


	/**
	 * Checks whether the game has ended based on whether all of the tiles have been claimed
	 * @return Returns true if ended, false if not
	 */
    private boolean checkGameEnded() {
        boolean ended = true;
		LandPlot[][] plots = plotManager.getLandPlots();
        for (LandPlot[] plot : plots) {
            for (LandPlot aPlot : plot) {
                if (aPlot != null) {
                    if (!aPlot.hasOwner()) {
                        ended = false;
                    }
                }
            }
        }
        return ended;
	}

	/**
	 * Returns the winner of the game, based on which player has the highest score
	 * @return
	 */

	public String getWinner(){
		String winner = null;
		if(playerList.get(0).calculateScore() > playerList.get(1).calculateScore()) {
			winner = "Player 1";
		}
		else{
				winner = "Player 2";
			}
		return winner;

	}

	/**
	 * Getter for the players of the game.
	 * @return The array containing the two players
	 */
	public ArrayList<Player> getPlayerList(){
		return this.playerList;
	}
}
