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

    public AnimationCustomHeader(String text, BitmapFont font, int length) {
        this.text = text;

        this.font = font;

        this.length = length;
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

    @Override
    public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
        time += delta;

        if (time > length) {
            return true;
        }

        batch.begin();

        font.setColor(1, 1, 1, calculateOpacity());
        GlyphLayout GL = new GlyphLayout(font, text);

        if (time < 1) {
            font.draw(batch, GL, Gdx.graphics.getWidth() / 2 - GL.width / 2, Gdx.graphics.getHeight() - 20 - (((float) Math.pow(time, 2)) * 30));
        } else {
            font.draw(batch, GL, Gdx.graphics.getWidth() / 2 - GL.width / 2, Gdx.graphics.getHeight() - 50);
        }

        batch.end();
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
