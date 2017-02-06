package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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


    public EndGameActors(final RoboticonQuest game, EndGameScreen screen){
        this.game = game;
        this.screen = screen;
        this.player1Score = new Label("Player 1 Score = " + String.valueOf(game.getPlayerList().get(0).calculateScore()),game.skin);
        this.player2Score = new Label("Player 2 Score = " + String.valueOf(game.getPlayerList().get(1).calculateScore()),game.skin);
        this.winner = new Label("The winner is " + game.getWinner(), game.skin);
        this.title = new Label("End of Game", game.skin);
        this.space = new Label("      ", game.skin);
        add(title).padRight(-90).padTop(-300);
        row();
        row();
        add(player1Score).padLeft(-50);
        add(space);
        add(player2Score);
        row();
        row();
        add(winner).padRight(-90).padBottom(-300);


    }
}
