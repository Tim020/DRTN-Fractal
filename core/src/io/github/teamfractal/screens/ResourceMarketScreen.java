package io.github.teamfractal.screens;

import com.badlogic.gdx.graphics.Color;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.ResourceMarketActors;

/**
 * Created by Joseph on 17/02/2017.
 */
public class ResourceMarketScreen extends Overlay {

    private ResourceMarketActors actors;

    public ResourceMarketScreen(RoboticonQuest game) {
        super(Color.GRAY, Color.WHITE, 350, 395, 0, 45, 3);

        actors = new ResourceMarketActors(game);
        table().add(actors);
    }

    public ResourceMarketActors actors() {
        return actors;
    }
}
