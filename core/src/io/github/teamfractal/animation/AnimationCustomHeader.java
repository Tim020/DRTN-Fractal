package io.github.teamfractal.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.github.teamfractal.screens.AbstractAnimationScreen;

/**
 * Created by Joseph on 15/02/2017.
 */
public class AnimationCustomHeader implements IAnimation {

    private float time;
    private float length;

    private String text;

    private BitmapFont font;

    private boolean play;

    public AnimationCustomHeader(String text, BitmapFont font, int length) {
        this.text = text;

        this.font = font;

        this.length = length;

        time = 0;
        play = false;
    }

    private float calculateOpacity() {
        if (time < 1) {
            return (float) Math.pow(time, 2);
        } else if (time < length - 1) {
            return 1;
        } else {
            return (float) Math.pow(length - time, 2);
        }
    }

    public void play() {
        play = true;
    }

    public void stop() {
        time = 0;
        play = false;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLength(float length) {
        this.length = length;
    }

    @Override
    public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
        if (play == true) {
            time += delta;

            if (time > length) {
                stop();
                return false;
            }

            batch.begin();

            font.setColor(1, 1, 1, calculateOpacity());
            GlyphLayout GL = new GlyphLayout(font, text);

            if (time < 1) {
                font.draw(batch, GL, Gdx.graphics.getWidth() / 2 - GL.width / 2, Gdx.graphics.getHeight() - 5 - (((float) Math.pow(time, 2)) * 30));
            } else {
                font.draw(batch, GL, Gdx.graphics.getWidth() / 2 - GL.width / 2, Gdx.graphics.getHeight() - 35);
            }

            batch.end();
        }

        return false;
    }


    @Override
    public void setAnimationFinish(IAnimationFinish callback) {

    }

    @Override
    public void callAnimationFinish() {

    }

    @Override
    public void cancelAnimation() {

    }

}
