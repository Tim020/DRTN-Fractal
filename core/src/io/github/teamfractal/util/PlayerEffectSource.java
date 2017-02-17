package io.github.teamfractal.util;

import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.PlayerEffect;

/**
 * Created by Joseph on 16/02/2017.
 */
public class PlayerEffectSource extends Array<PlayerEffect> {

    private RoboticonQuest engine;

    private PlayerEffect partyHard;
    private PlayerEffect vikingRaid;

    public PlayerEffectSource(final RoboticonQuest engine) {
        this.engine = engine;

        configureEffects();
        implementEffects();
    }

    public void configureEffects() {
        partyHard = new PlayerEffect("Party Hard", "You decided to throw a party on your newfound acquisition because " +
                "you're a capitalist and your money\nis worthless to you. Unfortunately, you got too drunk and " +
                "attempted to use some your fat stacks as Cards\nAgainst Humanity by scrawling immature statements " +
                "all over them with a permanent marker, thereby\nrendering them worthess.\n\n-30 Money", 0, 0, 0, -30, false);

        vikingRaid = new PlayerEffect("Viking Raid", "You have been raided by a group of intergalactical Vikings." +
                "They took:\n\n-10 Ore -10 Energy -10 Food and -10 Money", 0, 0, 0, -10, false);
    }

    public void implementEffects() {
        add(partyHard);
        add(vikingRaid);
    }
}
