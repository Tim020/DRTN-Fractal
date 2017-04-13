package io.github.teamfractal.screens;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.ChancellorActor;

/**
 * Created by Matt TP on 06/04/2017.
 */
public class ChancellorScreen extends Stage {

    public RoboticonQuest game;
    private ChancellorActor actor;

    private int initialAttempts;
    private float timeoutPerAttempt;
    private float chancellorDuration;

    private float time;
    private float nextAttemptTime;
    private int attempts;
    private boolean chancellorIsDisplayed;
    private float currentChancellorDuration;

    /**
     * NEW: The constructor for the cancellor phase
     * @param game The RoboticonQuest object used to interact with the rest of the game
     * @param attempts The number of times the chancellor image will be displayed before moving onto the next game phase
     * @param timeoutPerAttempt The maximum amount of time the player has to wait until the chancellor is displayed
     * @param chancellorDuration The amount of time the chancellor is displayed for before hiding
     */
    public ChancellorScreen(RoboticonQuest game, int attempts, float timeoutPerAttempt, float chancellorDuration) {
        this.game = game;
        this.actor = new ChancellorActor(this);
        this.addActor(actor);
        this.actor.setVisible(false);

        this.initialAttempts = attempts;
        this.timeoutPerAttempt = timeoutPerAttempt;
        this.chancellorDuration = chancellorDuration;
    }

    /**
     * NEW: Initialises the variables used in this phase back to the default values
     */
    public void startPhase() {
        time = 0;
        nextAttemptTime = 0;
        attempts = initialAttempts;
        chancellorIsDisplayed = false;
        currentChancellorDuration = 0;
        generateNextShowTime();
    }

    /**
     * NEW: Called every frame to update timings
     * @param delta The amount of time passed between the current and previous frames
     */
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

    /**
     * NEW: Called by the chancellor actor when the image is clicked used for rewarding the player
     * and moving on to the next round
     */
    //TODO: Add reward for playerq
    public void chancellorClicked() {
        if(chancellorIsDisplayed) {
            System.out.println("Chancellor Clicked!");
            hideChancellor();
            endPhase();
        }
    }

    /**
     * NEW: Generates a waiting time until the chancellor is displayed again
     * or ends the phase if all of the attempts have been used
     */
    private void generateNextShowTime() {
        if (attempts > 0) {
            attempts--;
            nextAttemptTime = MathUtils.random(0f, timeoutPerAttempt - 1);
            time = 0;
            return;
        }
        endPhase();
    }

    /**
     * NEW: Moves onto the next game phase
     */
    private void endPhase() {
        game.nextPhase();
    }

    /**
     * NEW: Acts on the chancellor actor to display it
     */
    private void showChancellor() {
        currentChancellorDuration = 0;
        chancellorIsDisplayed = true;
        actor.Show();
    }

    /**
     * NEW: Acts on the chancellor actor to hide it
     */
    private void hideChancellor() {
        chancellorIsDisplayed = false;
        actor.Hide();
    }
}
