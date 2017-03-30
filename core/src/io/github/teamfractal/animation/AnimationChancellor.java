package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.AbstractAnimationScreen;


/**
 * Created by mtpsgame on 30/03/2017.
 */
public class AnimationChancellor implements IAnimation {

    private RoboticonQuest game;
    private int attempts;
    private float timeoutPerAttempt;
    private float chancellorDuration;

    private float time;
    private float nextAttemptTime;
    private boolean chancellorIsDisplayed;
    private float currentChancellorDuration;

    public AnimationChancellor(RoboticonQuest game, int attempts, float timeoutPerAttempt, float chancellorDuration) {
        this.game = game;
        this.attempts = attempts;
        this.timeoutPerAttempt = timeoutPerAttempt;
        this.chancellorDuration = chancellorDuration;
        chancellorIsDisplayed = false;
        time = 0;
        currentChancellorDuration = 0;
        generateNextShowTime();
    }

    @Override
    public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
        time += delta;
        if (chancellorIsDisplayed) {
            currentChancellorDuration += delta;
            if (currentChancellorDuration >= chancellorDuration) {
                hideChancellor();
                return generateNextShowTime();
            }
            return false;
        }

        if (time >= nextAttemptTime) {
            showChancellor();
        }

        return false;
    }

    private boolean generateNextShowTime() {
        if (attempts > 0) {
            attempts--;
            nextAttemptTime = MathUtils.random(0f, timeoutPerAttempt - 1);
            time = 0;
            return false;
        }
        return true;
    }

    private void showChancellor() {
        currentChancellorDuration = 0;
        chancellorIsDisplayed = true;
        // show the chancellor actor on the game screen.
    }

    private void hideChancellor() {
        chancellorIsDisplayed = false;
        // hide the chancellor actor from the game screen.

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
