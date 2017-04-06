package io.github.teamfractal.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.ChancellorScreen;

/**
 * Created by Matt TP on 06/04/2017.
 */
public class ChancellorActor extends Image {

    private ChancellorScreen screen;
    private static Texture chancellorTexture;

    public ChancellorActor(ChancellorScreen scrn) {
        //super(chancellorTexture);
        screen = scrn;
        setPosition(0, 0);
        setWidth(200);
        setHeight(200);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)  {
                System.out.println("Chancellor Clicked!");
                screen.chancellorClicked();
            }
        });
    }

    public void Show() {
        //TODO: Move to a random location
        System.out.println("Show");
        setVisible(true);
    }

    public void Hide() {
        System.out.println("Hide");
        setVisible(false);
    }
}
