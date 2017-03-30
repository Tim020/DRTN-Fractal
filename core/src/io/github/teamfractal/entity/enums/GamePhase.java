package io.github.teamfractal.entity.enums;

/**
 * NEW
 * Created by md995 on 15/03/17.
 */
public enum GamePhase {
    TILE_ACQUISITION,
    ROBOTICON_PURCHASE,
    ROBOTICON_CUSTOMISATION,
    RESOURCE_GENERATION,
    CHANCELLOR,
    MARKET,
    NEXT_PLAYER;

    public GamePhase next() {
        return values()[(ordinal() + 1) % values().length];
    }
}
