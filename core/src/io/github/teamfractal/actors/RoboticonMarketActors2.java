package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.teamfractal.RoboticonQuest;
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

        purchaseTable.add(new Label("PURCHASE ROBOTICONS", new Label.LabelStyle(montserratRegular.font(), Color.WHITE))).colspan(3);

        purchaseTable.row();
        purchaseTable.add(subRoboticonButton);
        purchaseTable.add(lblRoboticonAmount);
        purchaseTable.add(addRoboticonButton);

        purchaseTable.row();
        purchaseTable.add(buyRoboticonsButton).colspan(3);
    }

    private void constructLabels() {
        lblRoboticonAmount = new Label(roboticonAmount.toString(), game.skin);
    }

    private void constructButtons() {
        //Increases number of roboticons to be purchased
        addRoboticonButton = new TextButton("+", game.skin);
        addRoboticonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addRoboticonFunction();
            }
        });

        //Decreases number of roboticons to be purchased
        subRoboticonButton = new TextButton("-", game.skin);
        subRoboticonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                subRoboticonFunction();
            }
        });

        buyRoboticonsButton = new TextButton("Purchase", game.skin);
        buyRoboticonsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buyRoboticonFunction();
            }
        });
    }

    public void addRoboticonFunction() {
        roboticonAmount += 1;
        this.lblRoboticonAmount.setText(roboticonAmount.toString());
    }

    public void subRoboticonFunction() {
        if (roboticonAmount > 0) {
            roboticonAmount -= 1;
            lblRoboticonAmount.setText(roboticonAmount.toString());
        }
    }

    public void buyRoboticonFunction() {
        game.getPlayer().purchaseRoboticonsFromMarket(roboticonAmount, game.market);
        roboticonAmount = 0;
        lblRoboticonAmount.setText(roboticonAmount.toString());
        //widgetUpdate();
    }
}
