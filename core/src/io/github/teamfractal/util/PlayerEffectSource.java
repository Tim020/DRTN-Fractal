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

    private PlayerEffect uhOh;
    
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

        uhOh = new PlayerEffect("Uh Oh!", "Someone left the lights on over night again. Who was it? \nI bet it was Darrell," +
                " it's always Darrell. Either way, \nlooks like it used a lot of your energy. \n\n -50 Energy",0,-50,0,0, false);
        
        vikingRaid = new PlayerEffect("Viking Raid", "You have been raided by a band of intergalactic Vikings." +
                "They took:\n\n-10 Ore -10 Energy -10 Food and -10 Money", 0, 0, 0, -10, false);
    }

    public void implementEffects() {
        add(partyHard);
        add(uhOh);
        add(vikingRaid);
    }
}
