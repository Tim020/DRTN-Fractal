package io.github.teamfractal.screens;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.ChancellorActor;

/**
 * Created by Matt TP on 06/04/2017.
 */
public class ChancellorScreen extends Stage {

    private RoboticonQuest game;
    private ChancellorActor actor;

    private int initialAttempts;
    private float timeoutPerAttempt;
    private float chancellorDuration;

    private float time;
    private float nextAttemptTime;
    private int attempts;
    private boolean chancellorIsDisplayed;
    private float currentChancellorDuration;

    public ChancellorScreen(RoboticonQuest game, int attempts, float timeoutPerAttempt, float chancellorDuration) {
        this.game = game;
        this.actor = new ChancellorActor(this);
        this.addActor(actor);
        this.actor.setVisible(false);

        this.initialAttempts = attempts;
        this.timeoutPerAttempt = timeoutPerAttempt;
        this.chancellorDuration = chancellorDuration;
    }

    public void startPhase() {
        time = 0;
        nextAttemptTime = 0;
        attempts = initialAttempts;
        chancellorIsDisplayed = false;
        currentChancellorDuration = 0;
        generateNextShowTime();
    }

    // An update method called every frame.
    @Override
    public void act(float delta) {
        super.act(delta);

        time += delta;
        if (chancellorIsDisplayed) {
            currentChancellorDuration += delta;
            if (currentChancellorDuration >= chancellorDuration) {
                hideChancellor();
                generateNextShowTime();
            }
        } else {
            if (time >= nextAttemptTime) {
                showChancellor();
            }
        }
    }

    public void chancellorClicked() {
        if(chancellorIsDisplayed) {
            //TODO: Give the player some reward
            endPhase();
        }
    }

    private void generateNextShowTime() {
        if (attempts > 0) {
            attempts--;
            nextAttemptTime = MathUtils.random(0f, timeoutPerAttempt - 1);
            time = 0;
            System.out.println("Next show time in " + nextAttemptTime);
            return;
        }
        endPhase();
    }

    private void endPhase() {
        game.nextPhase();
    }

    private void showChancellor() {
        currentChancellorDuration = 0;
        chancellorIsDisplayed = true;
        actor.Show();
    }

    private void hideChancellor() {
        chancellorIsDisplayed = false;
        actor.Hide();
    }
}
