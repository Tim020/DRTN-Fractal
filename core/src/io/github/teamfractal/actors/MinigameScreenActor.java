package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.MiniGame;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.MiniGameScreen;
import io.github.teamfractal.screens.ResourceMarketScreen;

public class MinigameScreenActor extends Table {
    private final AdjustableActor play;
    private RoboticonQuest game;
    private Label phaseInfo;
    private Label playerStats;
    private Label gamblingInfo;
    private Label comment;
    private Label win;
    private MiniGameScreen screen;
    private TextButton backButton;
    private Label marketStats;
    private Label wellcomeLabel;
    private int diceValue1;
    private int diceValue2;
    private int gamblingMoney;
    private int totalWin;


    /**
     * Initialise market actors.
     * @param game       The game object.
     * @param screen     The screen object.
     */
    public MinigameScreenActor(final RoboticonQuest game, MiniGameScreen screen) { // need to be changed
        center();

        Skin skin = game.skin;
        this.game = game;
        this.screen = screen;
        Stage stage = screen.getStage();


        // Create UI Components
        phaseInfo = new Label("", game.skin);
        backButton = new TextButton("Back to Market ->", game.skin);

        playerStats = new Label("", game.skin);
        marketStats = new Label("", game.skin);
        gamblingInfo = new Label("", game.skin);
        comment = new Label("", game.skin);
        win = new Label("", game.skin);
        wellcomeLabel = new Label("Wellcome to the Pub", game.skin);

        play = createAdjustable("Amount to gamble", "Roll a dice");


        Label introLabel = new Label("You have entered a pub\n" +
                "here you can gamble by rolling a dice", skin);
        Label textLabel = new Label("Bet some money, roll a dice and if you will beat the opponent, \n" +
                "your money will be doubled", skin);

        // Adjust properties.
        phaseInfo.setAlignment(Align.right);
        marketStats.setAlignment(Align.right);
        wellcomeLabel.setAlignment(Align.center);
        introLabel.setAlignment(Align.center);
        textLabel.setAlignment(Align.center);

        // Add UI components to screen.
        stage.addActor(phaseInfo);
        stage.addActor(backButton);
        stage.addActor(wellcomeLabel);
        stage.addActor(introLabel);
        stage.addActor(textLabel);


        // Setup UI Layout.

        // Row: intro text
        add(introLabel);
        rowWithHeight(40);

        // Row: text
        add(textLabel);
        rowWithHeight(80);

        // Row: Player and Market Stats.
        add(playerStats);
        add();
        //add(win);
        add().spaceRight(20);
        rowWithHeight(20);
        add(play);
        rowWithHeight(20);
        add(gamblingInfo);
        rowWithHeight(10);
        add(comment);

        bindEvents();
        widgetUpdate();
    }

    /**
     * Bind button events.
     */
    private void bindEvents() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(screen.getRMS());
                widgetUpdate();
            }
        });
    }

    /**
     * Add an empty row to current table.
     * @param height  The y for that empty row.
     */
    private void rowWithHeight(int height) {
        row();
        add().spaceTop(height);
        row();
    }

    /**
     * Updates all widgets on screen
     */
    private void widgetUpdate() {
        // update player stats, phase text and gambling information.
        String phaseText =
                "Player " + (game.getPlayerInt() + 1) + "; " +
                        "Phase " + game.getPhase() + " - " + game.getPhaseString();

        String statText =
                        "Your Money: "  + game.getPlayer().getMoney() + "      " +
                                "Total win/loss: " + totalWin;

        String dice =
                "You scored: " + "[" + diceValue1 + "]" + "  " +
                        "Opponent scored: " + "[" + diceValue2 + "]" ;

        String result1 = "Draw!";

        String result2 = "You loose " + gamblingMoney + " !";

        String result3 = "You win " + gamblingMoney + " !";

        String result4 = " ";

        String winValue = "Total win/loss: " + totalWin;



        phaseInfo.setText(phaseText);
        playerStats.setText(statText);
        gamblingInfo.setText(dice);
        win.setText(winValue);

        if (diceValue1 == 0){
            comment.setText(result4);
        }
        else if (diceValue1 < diceValue2) {
            comment.setText(result2);
        }
        else if (diceValue1 > diceValue2) {
            comment.setText(result3);
        }
        else {
            comment.setText(result1);
        }
    }

    /**
     * Respond to the screen resize event, updates widgets position
     * accordingly.
     * @param width    The new x.
     * @param height   The new Height.
     */
    public void screenResize(float width, float height) {

        // Top center
        wellcomeLabel.setPosition((width - wellcomeLabel.getWidth())/2, height - 20);

        // Top Left
        phaseInfo.setPosition(0, height - 20);
        phaseInfo.setWidth(width - 10);

        // Bottom Right
        backButton.setPosition(width - backButton.getWidth() - 10, 10);
        setWidth(width);
    }


    /**
     * Sync. information with the adjustable.
     *
     * @param adjustableActor The adjustable to manipulate with.
     */
    private void updateAdjustable(AdjustableActor adjustableActor) {
        adjustableActor.setMax(game.getPlayer().getMoney());
    }

    private int getDiceValue1() {
        return diceValue1;
    }

    private void setDiceValue1(int value){
        this.diceValue1 = value;
    }

    private int getDiceValue2() {
        return diceValue2;
    }

    private void setDiceValue2(int value){
        this.diceValue2 = value;
    }

    private void setGamblingMoney(int money) {
        this.gamblingMoney = money;
    }

    /**
     * Generate an adjustable actor for gambling.
     * @return The adjustable actor generated.
     */
    private AdjustableActor createAdjustable(String title, String action) {
        final Player player = game.getPlayer();
        final AdjustableActor adjustableActor = new AdjustableActor(game.skin, title, action);
        updateAdjustable(adjustableActor);
        adjustableActor.setActionEvent(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final MiniGame diceValue1 = new MiniGame();
                final MiniGame diceValue2 = new MiniGame();
                setDiceValue1(diceValue1.WinGame());
                setDiceValue2(diceValue2.WinGame());
                if (player.getMoney() == 0) {
                    // does nothing
                }
                else if (getDiceValue1() == getDiceValue2()) {
                    // does nothing
                }
                else if (getDiceValue1() < getDiceValue2()) {
                    player.setGamblingMoney(player.getMoney() - adjustableActor.getValue());
                    setGamblingMoney(adjustableActor.getValue());
                    totalWin = totalWin - adjustableActor.getValue();
                    updateAdjustable(adjustableActor);
                }
                else {
                    player.setGamblingMoney(player.getMoney() + adjustableActor.getValue());
                    setGamblingMoney(adjustableActor.getValue());
                    totalWin = totalWin + adjustableActor.getValue();
                    updateAdjustable(adjustableActor);
                }

                MinigameScreenActor.this.widgetUpdate();
            }
        });
        return adjustableActor;
    }

}
