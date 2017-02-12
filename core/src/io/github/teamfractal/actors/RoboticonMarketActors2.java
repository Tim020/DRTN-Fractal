package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.RoboticonMarketScreen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.teamfractal.util.TTFont;

/**
 * Created by Joseph on 08/02/2017.
 */
public class RoboticonMarketActors2 extends Table {
    private static final Texture no_cust_texture;
    private static final Texture energy_texture;
    private static final Texture ore_texture;
    private static final Texture food_texture;
    private static final Texture no_robotic_texture;

    static {
        no_cust_texture = new Texture(Gdx.files.internal("roboticon_images/robot.png"));
        energy_texture = new Texture(Gdx.files.internal("roboticon_images/robot_energy.png"));
        ore_texture = new Texture(Gdx.files.internal("roboticon_images/robot_ore.png"));
        food_texture = new Texture(Gdx.files.internal("roboticon_images/robot_question.png")); //TODO: Create food roboticon texture and insert path here
        no_robotic_texture = new Texture(Gdx.files.internal("roboticon_images/no_roboticons.png"));
    }

    private RoboticonQuest game;
    private RoboticonMarketScreen screen;

    private Table purchaseTable;
    private Table upgradeTable;

    private Integer roboticonAmount;

    private Label lblRoboticonAmount;

    private TextButton subRoboticonButton;
    private TextButton addRoboticonButton;
    private TextButton buyRoboticonsButton;

    public RoboticonMarketActors2(RoboticonQuest game, RoboticonMarketScreen screen) {
        this.game = game;
        this.screen = screen;

        purchaseTable = new Table();
        upgradeTable = new Table();

        roboticonAmount = 0;

        constructLabels();
        constructButtons();

        TTFont montserratRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"));
        TTFont montserratLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"));
        montserratRegular.setSize(24);
        montserratLight.setSize(24);

        purchaseTable.add(new Label("PURCHASE ROBOTICONS", new Label.LabelStyle(montserratRegular.font(), Color.WHITE))).colspan(4);

        purchaseTable.row();
        purchaseTable.add(subRoboticonButton).width(25).padLeft(66);
        purchaseTable.add(lblRoboticonAmount).width(50);
        purchaseTable.add(addRoboticonButton).width(25);
        purchaseTable.add(buyRoboticonsButton).expandX().align(Align.right).padRight(55);

        purchaseTable.row();
        purchaseTable.add(new Label("CUSTOMISE ROBOTICONS", new Label.LabelStyle(montserratRegular.font(), Color.WHITE))).colspan(4).padTop(25);

        purchaseTable.debug();

        add(purchaseTable);
    }

    private void constructLabels() {
        lblRoboticonAmount = new Label(roboticonAmount.toString() + "/" +  game.market.getResource(ResourceType.ROBOTICON), game.skin);
        lblRoboticonAmount.setAlignment(Align.center);
    }

    private void constructButtons() {
        //Increases number of roboticons to be purchased
        addRoboticonButton = new TextButton("+", game.skin);
        addRoboticonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (roboticonAmount < game.market.getResource(ResourceType.ROBOTICON)) {
                    roboticonAmount += 1;
                    setRoboticonQuantityLabel(roboticonAmount);
                }
            }
        });

        //Decreases number of roboticons to be purchased
        subRoboticonButton = new TextButton("-", game.skin);
        subRoboticonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (roboticonAmount > 0) {
                    roboticonAmount -= 1;
                    setRoboticonQuantityLabel(roboticonAmount);
                }
            }
        });

        buyRoboticonsButton = new TextButton("PURCHASE", game.skin);
        buyRoboticonsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buyRoboticonFunction();
            }
        });
    }

    public void setRoboticonQuantityLabel(int quantity) {
        lblRoboticonAmount.setText(quantity + "/" + game.market.getResource(ResourceType.ROBOTICON));
    }

    public void buyRoboticonFunction() {
        game.getPlayer().purchaseRoboticonsFromMarket(roboticonAmount, game.market);
        roboticonAmount = 0;
        lblRoboticonAmount.setText(roboticonAmount.toString());
        //widgetUpdate();
    }

    /*
    public void moveLeftRoboticonInventoryFunction() {
        if (currentlySelectedRoboticonPos > 0) {
            currentlySelectedRoboticonPos--;
            setCurrentlySelectedRoboticon(currentlySelectedRoboticonPos);
        }
    }

    public void moveRightRoboticonInventoryFunction() {
        if (currentlySelectedRoboticonPos < roboticons.size() - 1) {
            currentlySelectedRoboticonPos++;
            setCurrentlySelectedRoboticon(currentlySelectedRoboticonPos);
        }
    }

    public void buyCustomisationFunction(ResourceType customisation, int pos) {


        game.getPlayer().purchaseCustomisationFromMarket(customisation, roboticons.get(pos), game.market);
        widgetUpdate();
    }

    public void widgetUpdate() {
        roboticons.clear();
        for (Roboticon r : game.getPlayer().getRoboticons()) {
            if (!r.isInstalled()) {
                roboticons.add(r);
            }
        }

        // Draws turn and phase info on screen
        if (this.topText != null) this.topText.remove();
        String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
        this.topText = new Label(phaseText, game.skin);
        topText.setWidth(120);
        topText.setPosition(screen.getStage().getWidth() / 2 - 40, screen.getStage().getViewport().getWorldHeight() - 20);
        screen.getStage().addActor(topText);

        // Draws player stats on screen
        if (this.playerStats != null) this.playerStats.remove();
        String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " + game.getPlayer().getEnergy() + " Food: "
                + game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
        this.playerStats = new Label(statText, game.skin);
        playerStats.setWidth(250);
        playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
        screen.getStage().addActor(playerStats);

        if (roboticons.size() == 0) {
            currentlySelectedRoboticonPos = -1;
        } else if (currentlySelectedRoboticonPos == -1) {
            currentlySelectedRoboticonPos = 0;
        }

        setCurrentlySelectedRoboticon(currentlySelectedRoboticonPos);

        marketStats.setText("Market - Roboticons: " + game.market.getResource(ResourceType.ROBOTICON));
    }

    public void setCurrentlySelectedRoboticon(int roboticonPos) {
        if (roboticonPos != -1) {

            ResourceType roboticonType = roboticons.get(roboticonPos).getCustomisation();

            switch (roboticonType) {
                case Unknown:
                    roboticonTexture = no_cust_texture;
                    break;
                case ENERGY:
                    roboticonTexture = energy_texture;
                    break;
                case ORE:
                    roboticonTexture = ore_texture;
                    break;
                case FOOD:
                    roboticonTexture = food_texture;
                    break;
                default:
                    break;
            }

            int id = roboticons.get(roboticonPos).getID();
            this.roboticonID.setText("Roboticon Issue Number: " + padZero(id, 4));

        } else {
            roboticonTexture = no_robotic_texture;
            this.roboticonID.setText("Roboticon Issue Number: ####");
        }

        roboticonImage.setDrawable(new TextureRegionDrawable(new TextureRegion(roboticonTexture)));
    }
    */
}
