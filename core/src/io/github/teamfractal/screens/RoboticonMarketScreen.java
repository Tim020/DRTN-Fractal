package io.github.teamfractal.screens;

import com.badlogic.gdx.graphics.Color;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.RoboticonMarketActors;

/**
 * Created by Joseph on 15/02/2017.
 */
public class RoboticonMarketScreen extends Overlay {

    private RoboticonMarketActors actors;

    public RoboticonMarketScreen(RoboticonQuest game, Color fillColor, Color lineColor, int lineThickness) {
        super(fillColor, lineColor, 340, 340, lineThickness);

        actors = new RoboticonMarketActors(game);
        table().add(actors);
    }

    public RoboticonMarketActors actors() {
        return actors;
    }
}
