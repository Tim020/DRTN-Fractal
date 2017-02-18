package io.github.teamfractal.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.screens.Overlay;
import io.github.teamfractal.util.TTFont;

/**
 * Created by Joseph on 01/02/2017.
 */
public class PlayerEffect {

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Array holding the inventory modifiers that the effect imposes
     * These are either to be added/subtracted to/from the player's current resource counts or multiplied by them,
     * depending on the value of the [multiply] variable declared below
     */
    private float[] modifiers;

    /**
     * Determines whether the effect is to add to (or subtract from) the player's resources or to multiply them by set
     * factors
     */
    private boolean multiply;

    /**
     * Overlay to provide a visual indication of the effect's applications and influences
     */
    private Overlay overlay;

    public PlayerEffect(String name, String description, float oreModifier, float energyModifier, float foodModifier, float moneyModifier, boolean multiply) {
        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference

        this.modifiers = new float[5];
        this.modifiers[0] = oreModifier;
        this.modifiers[1] = energyModifier;
        this.modifiers[2] = foodModifier;
        this.modifiers[3] = moneyModifier;

        this.multiply = multiply;

        this.runnable = runnable;

        this.overlay = new Overlay(Color.OLIVE, Color.WHITE, 3);
        //Construct a visual interface through which the effect can be identified
    }

    /**
     * Method that populates the effect's associated overlay
     */
    public void constructOverlay(final GameScreen gameScreen) {
        TextButton.TextButtonStyle overlayButtonStyle = new TextButton.TextButtonStyle();
        overlayButtonStyle.font = gameScreen.getGame().headerFontRegular.font();
        overlayButtonStyle.pressedOffsetX = -1;
        overlayButtonStyle.pressedOffsetY = -1;
        overlayButtonStyle.fontColor = Color.WHITE;

        Label headerLabel = new Label("PLAYER EFFECT IMPOSED", new Label.LabelStyle(gameScreen.getGame().headerFontRegular.font(), Color.CHARTREUSE));
        Label titleLabel = new Label(name, new Label.LabelStyle(gameScreen.getGame().headerFontLight.font(), Color.WHITE));
        Label descriptionLabel = new Label(description, new Label.LabelStyle(gameScreen.getGame().smallFontLight.font(), Color.WHITE));

        headerLabel.setAlignment(Align.left);
        titleLabel.setAlignment(Align.right);
        descriptionLabel.setAlignment(Align.left);

        overlay.table().add(headerLabel).width(330).left();
        overlay.table().add(titleLabel).width(descriptionLabel.getWidth() - 330).right();
        overlay.table().row();
        overlay.table().add(descriptionLabel).left().colspan(2).padTop(5).padBottom(20);

        overlay.table().row().colspan(2);
        TextButton closeButton = new TextButton("CLOSE", overlayButtonStyle);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.removeOverlay();
            }
        });

        overlay.table().add(closeButton);

        overlay.resize(descriptionLabel.getWidth() + 20, headerLabel.getHeight() + descriptionLabel.getHeight() + closeButton.getHeight() + 35);
    }

    /**
     * Imposes the effect on the player by changing the resources that they have. Their resources can either be
     * multiplied or divided
     * @param player The player that is to be effected
     */
    public void impose(Player player) {
        if (multiply == true) {
            player.setResource(ResourceType.ORE, (int) ((float) player.getOre() * modifiers[0]));
            player.setResource(ResourceType.ENERGY, (int) ((float) player.getEnergy() * modifiers[1]));
            player.setResource(ResourceType.FOOD, (int) ((float) player.getFood() * modifiers[2]));
            player.setMoney((int) ((float) player.getMoney() * modifiers[3]));
        } else {
            player.setResource(ResourceType.ORE, player.getOre() + (int) modifiers[0]);
            player.setResource(ResourceType.ENERGY, player.getEnergy() + (int) modifiers[1]);
            player.setResource(ResourceType.FOOD, player.getFood() + (int) modifiers[2]);
            player.setMoney(player.getMoney() + (int) modifiers[3]);
        }
    }

    /**
     * Getter for the name of the effect
     * @return The name of the effect
     */
    public String name() {
        return name;
    }

    /**
     * Getter for the description of the effect
     * @return The description of the effect
     */
    public String description() {
        return description;
    }

    /**
     * Getter for the overlay
     * @return The overlay of the effect
     */
    public Overlay overlay() { return overlay; }
}
