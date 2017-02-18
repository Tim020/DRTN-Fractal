package io.github.teamfractal.screens;

import com.badlogic.gdx.graphics.Color;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.ResourceMarketActors;

import java.util.Random;

/**
 * Created by Joseph on 17/02/2017.
 */
public class ResourceMarketScreen extends Overlay {

    private RoboticonQuest game;

    private ResourceMarketActors actors;

    private int gambleMoneyWonCounter;
    private int gambleMoneyLostCounter;
    private int gambleWinLossCounter;

    public ResourceMarketScreen(RoboticonQuest game) {
        super(Color.GRAY, Color.WHITE, 520, 395, 0, 45, 3);

        this.game = game;

        actors = new ResourceMarketActors(game);
        table().add(actors);

        gambleStatisticsReset();
    }

    public ResourceMarketActors actors() {
        return actors;
    }

    public void gamble() {
        int playerRoll;
        int AIRoll;

        if (actors.gambleFieldValue().isEmpty()) {
            actors.setGambleStatusLabel("NO VALUE\nGIVEN", Color.RED);
        } else if (Integer.parseInt(actors.gambleFieldValue()) < 1) {
            actors.setGambleStatusLabel("INVALID VALUE\nGIVEN", Color.RED);
        } else if (Integer.parseInt(actors.gambleFieldValue()) > game.getPlayer().getMoney()) {
            actors.setGambleStatusLabel("CANNOT AFFORD\nGAMBLE", Color.RED);
        } else {
            Random RNGesus = new Random();

            playerRoll = RNGesus.nextInt(6) + 1;
            AIRoll = RNGesus.nextInt(6) + 1;

            if (playerRoll == AIRoll) {
                actors.setGambleStatusLabel("YOU\nDREW", Color.YELLOW);
            } else if (playerRoll > AIRoll) {
                actors.setGambleStatusLabel("YOU\nWON", Color.GREEN);
                game.getPlayer().setMoney(game.getPlayer().getMoney() + Integer.parseInt(actors.gambleFieldValue()));
                gambleMoneyWonCounter += Integer.parseInt(actors.gambleFieldValue());
                gambleWinLossCounter += 1;
            } else {
                actors.setGambleStatusLabel("YOU\nLOST", Color.RED);
                game.getPlayer().setMoney(game.getPlayer().getMoney() - Integer.parseInt(actors.gambleFieldValue()));
                gambleMoneyLostCounter += Integer.parseInt(actors.gambleFieldValue());
                gambleWinLossCounter -= 1;
            }

            actors.setGambleRollLabels(playerRoll, AIRoll);

            actors.setGambleStatisticsLabels(gambleMoneyWonCounter, gambleMoneyLostCounter, gambleWinLossCounter);
        }
    }

    public void gambleStatisticsReset() {
        gambleMoneyWonCounter = 0;
        gambleMoneyLostCounter = 0;
        gambleWinLossCounter = 0;

        actors.setGambleStatusLabel( "\n ", Color.WHITE);
        actors.setGambleRollLabels("-", "-");
        actors.setGambleStatisticsLabels(0, 0, 0);
    }
}
