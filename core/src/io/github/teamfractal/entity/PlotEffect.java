package io.github.teamfractal.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.screens.Overlay;
import io.github.teamfractal.util.TTFont;

/**
 * Created by Joseph on 31/01/2017.
 */
public class PlotEffect extends Array<Float[]> {

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Object containing a method that the effect can automatically trigger if and when it is run
     */
    private Runnable runnable;

    /**
     * Array storing all of the plots to have been affected by this effect (in the order by which they were affected)
     */
    private Array<LandPlot> plotRegister;

    /**
     * Overlay to provide a visual indication of the effect's presence and influences
     */
    private Overlay overlay;

    /**
     * Constructor that assigns a name, a description, variably-applicable modifiers and a custom method to the effect
     *
     * @param name The name of the effect
     * @param description A description of the effect
     * @param modifiers The production modifiers that the effect can impose {0: ORE | 1: ENERGY | 2: FOOD}
     *
     */
    public PlotEffect(String name, String description, Float[] modifiers, Runnable runnable) {
        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference

        super.add(modifiers);
        //Store the effect's modifiers at the base of the internal stack

        this.runnable = runnable;
        //Assign the effect to the proprietary method provided

        this.plotRegister = new Array<LandPlot>();
        //Establish the separate LandPlot stack to track affected tiles

        this.overlay = new Overlay(Color.GRAY, Color.WHITE, 3);
        //Construct a visual interface through which the effect can be identified
    }

    /**
     * Overloaded constructor that assigns a name, a description and variably-applicable modifiers to the effect
     *
     * @param name        The name of the effect
     * @param description A description of the effect
     * @param modifiers   The production modifiers that the effect can impose {0: ORE | 1: ENERGY | 2: FOOD}
     */
    public PlotEffect(String name, String description, Float[] modifiers) {
        this(name, description, modifiers, new Runnable() {
            @Override
            public void run() {
                //This is meant to be empty
            }
        });
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

        Label headerLabel = new Label("PLOT EFFECT IMPOSED", new Label.LabelStyle(gameScreen.getGame().headerFontRegular.font(), Color.YELLOW));
        Label titleLabel = new Label(name, new Label.LabelStyle(gameScreen.getGame().headerFontLight.font(), Color.WHITE));
        Label descriptionLabel = new Label(description, new Label.LabelStyle(gameScreen.getGame().smallFontLight.font(), Color.WHITE));

        headerLabel.setAlignment(Align.left);
        titleLabel.setAlignment(Align.right);
        descriptionLabel.setAlignment(Align.left);

        overlay.table().add(headerLabel).width(300).left();
        overlay.table().add(titleLabel).width(descriptionLabel.getWidth() - 300).right();
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
     * Imposes the effect's modifiers on the provided plot
     * Assumes that the modifiers to be imposed at any given time will be at the head of the internal stack
     *
     * @param plot The plot to be affected
     * @param mode The mode of effect [0: ADD | 1: MULTIPLY | 2: OVERWRITE]
     */
    public void impose(LandPlot plot, int mode) {
        Float[] originalModifiers = new Float[3];
        Float[] newModifiers;
        //Declare temporary arrays to handle modifier modifications

        newModifiers = super.pop();
        //Assume that the modifiers on the top of the stack are the modifiers to be imposed

        for (int i = 0; i < 3; i++) {
            originalModifiers[i] = plot.productionModifiers[i];
            //Save each of the specified tile's original modifiers

            switch (mode) {
                case (0):
                    plot.productionModifiers[i] = plot.productionModifiers[i] + newModifiers[i];
                    //MODE 0: Add/subtract to/from the original modifiers
                    break;
                case (1):
                    plot.productionModifiers[i] = plot.productionModifiers[i] * newModifiers[i];
                    //MODE 1: Multiply the original modifiers
                    break;
                case (2):
                    plot.productionModifiers[i] = newModifiers[i];
                    //MODE 2: Replace the original modifiers
                    break;
            }
        }

        super.add(originalModifiers);
        //Add the tile's original modifiers to the stack for later access...

        super.add(newModifiers);
        //...and return the imposed modifiers to the top of the stack

        plotRegister.add(plot);
        //Push the plot that's about to be modified on to the appropriate registration stack
    }

    /**
     * Reverts the modifiers of the land plot to their values before the effect was applied
     */
    public void revert() {
        if (plotRegister.size > 0) {
            Float[] originalModifiers;
            LandPlot lastPlot;

            swapTop();
            originalModifiers = super.pop();

            lastPlot = plotRegister.pop();

            for (int i = 0; i < 3; i++) {
                lastPlot.productionModifiers[i] = originalModifiers[i];
            }
        }
    }
    /**
     * Reverts all tiles that have been affected back to their original state
     */
    public void revertAll() {
        while (plotRegister.size > 0) {
            revert();
        }
    }
    /**
     * Swaps the postions of the top two values within the internal stack
     */
    private void swapTop() {
        if (super.size > 1) {
            Float[] i = super.pop();
            Float[] j = super.pop();

            super.add(i);
            super.add(j);
        }
    }

    /**
     * Executes the runnable
     */
    public void executeRunnable() {
        runnable.run();
    }

    /**
     * Getter for the runnable
     * @return The runnable
     */
    public Runnable getRunnable() {
        return runnable;
    }

    /**
     * Sets the method that the effect will run when it's imposed on a given tile
     *
     * @param runnable The method to be executed when this effect is invoked
     */
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Getter for the name of the effect
     * @return The name of the effect
     */
    public String name() {
        return name;
    }

    /**
     * Getter for the description if the effect
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
