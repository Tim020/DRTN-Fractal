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
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.MiniGameScreen;
import io.github.teamfractal.screens.ResourceMarketScreen;

public class MinigameScreenActor extends Table {
    private RoboticonQuest game;
    private Integer buyOreAmount;
    private Integer sellOreAmount;
    private Integer buyEnergyAmount;
    private Integer sellEnergyAmount;
    private Integer buyFoodAmount;
    private Integer sellFoodAmount;
    private Label phaseInfo;
    private Label playerStats;
    private MiniGameScreen screen;
    //private ResourceMarketScreen screen;
    private TextButton backButton;
    private Label marketStats;
    private Label wellcomeLabel;


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
        wellcomeLabel = new Label("Wellcome to the Pub", game.skin);

        //Label wellcomeLabel = new Label("Wellcome to the Pub", skin);
        Label introLabel = new Label("You have entered a pub and the owner\n" +
                "is suggesting to gamble by rolling a dice", skin);
        Label textLabel = new Label("Bet some money, roll a dice and if you will beat the opponent, \n" +
                "your money will be doubled", skin);
        Label choiceLabel = new Label("Amount to gamble", skin);

        // Adjust properties.
        phaseInfo.setAlignment(Align.right);
        marketStats.setAlignment(Align.right);
        wellcomeLabel.setAlignment(Align.center);
        introLabel.setAlignment(Align.center);
        textLabel.setAlignment(Align.center);
        choiceLabel.setAlignment(Align.center);

        // Add UI components to screen.
        stage.addActor(phaseInfo);
        stage.addActor(backButton);
        stage.addActor(wellcomeLabel);
        stage.addActor(introLabel);
        stage.addActor(textLabel);
        stage.addActor(choiceLabel);



        // Setup UI Layout.

        // Row: intro text
        add(introLabel);
        rowWithHeight(40);

        // Row: text
        add(textLabel);
        rowWithHeight(20);

        // Row: Player and Market Stats.
        add(playerStats);
        add();
        add(choiceLabel);
        add().spaceRight(20);
        rowWithHeight(20);


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
        // update player stats, phase text, and the market stats.
        String phaseText =
                "Player " + (game.getPlayerInt() + 1) + "; " +
                        "Phase " + game.getPhase() + " - " + game.getPhaseString();

        String statText =
                        "Your Money: "  + game.getPlayer().getMoney();

        phaseInfo.setText(phaseText);
        playerStats.setText(statText);


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
}
