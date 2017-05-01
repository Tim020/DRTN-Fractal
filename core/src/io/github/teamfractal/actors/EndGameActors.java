/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.EndGameScreen;

/**
 * Created by Jack on 04/02/2017.
 */

public class EndGameActors extends Table {
    private RoboticonQuest game;
    private EndGameScreen screen;
    private Label player1Score;
    private Label player2Score;
    private Label winner;
    private Label title;
    private Label space;
    
    /**
     -     * Creates the labels that are to appear in the end game screen
     -     * @param game The current game
     -     * @param screen The screen the actors are to be placed on
     -     */
    public EndGameActors(final RoboticonQuest game, EndGameScreen screen){
        this.game = game;
        this.screen = screen;
        this.player1Score = new Label("Player 1 Score = " + String.valueOf(game.getPlayerList().get(0).calculateScore()),game.skin);
        this.player2Score = new Label("Player 2 Score = " + String.valueOf(game.getPlayerList().get(1).calculateScore()),game.skin);
        this.winner = new Label(game.getWinner(), game.skin);
        winner.setAlignment(Align.center);
        this.title = new Label("End of Game", game.skin);
        this.space = new Label("      ", game.skin);
        add(title).padRight(-175).padTop(-300);
        row();
        row();
        add(player1Score).padLeft(-50);
        add(space);
        add(player2Score);
        row();
        row();
        add(winner).padRight(-170).padBottom(-300);


    }
}
