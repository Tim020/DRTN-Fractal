package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.ChancellorScreen;

/**
 * Created by Matt TP on 06/04/2017.
 *
 * Controls the chancellor image used in the chancellor phase
 */
public class ChancellorActor extends Image {

    private ChancellorScreen screen;
    private static Texture chancellorTexture = new Texture(Gdx.files.internal("roboticon_images/robot.png"));

    /**
     * Constructor for the chancellor image, setting the size and click listener
     * @param scrn The chancellor stage object
     */
    public ChancellorActor(ChancellorScreen scrn) {
        super(chancellorTexture);
        screen = scrn;
        setWidth(200);
        setHeight(200);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)  {
                super.clicked(event, x, y);
                screen.chancellorClicked();
            }
        });
    }

    /**
     * Displays the chancellor image in a random location on the screen.
     */
    public void Show() {
        setPosition(MathUtils.random(0, Gdx.graphics.getWidth() - getWidth()), MathUtils.random(0, Gdx.graphics.getHeight() - getHeight()));
        setVisible(true);
    }

    /**
     * Hides the chancellor image.
     */
    public void Hide() {
        setVisible(false);
    }
}
