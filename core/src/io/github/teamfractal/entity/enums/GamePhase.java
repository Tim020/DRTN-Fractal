package io.github.teamfractal.entity.enums;

/**
 * NEW
 * Created by md995 on 15/03/17.
 */
public enum GamePhase {
    TILE_ACQUISITION, //phase 1
    ROBOTICON_PURCHASE, //phase 2
    ROBOTICON_CUSTOMISATION, //phase 3
    RESOURCE_GENERATION, //phase 4
    CHANCELLOR, //phase 5
    MARKET, //phase 6
    NEXT_PLAYER;

    public GamePhase next() {
        return values()[(ordinal() + 1) % values().length];
    }
}
