package com.company;

public class GameRule {
    private final PlayerType firstPlayer;
    private final PlayerType secondPlayer;

    /**
     * GameRule Constructor
     *
     * Instantiates an object with 2 players and sets their type.
     * @see PlayerType
     */

    public GameRule(PlayerType firstPlayer, PlayerType secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public PlayerType getFirstPlayer() {
        return firstPlayer;
    }

    public PlayerType getSecondPlayer() {
        return secondPlayer;
    }
}
