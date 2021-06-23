package com.company;

public class GameCommand {
    private final PlayerType firstPlayer;
    private final PlayerType secondPlayer;

    public GameCommand(PlayerType firstPlayer, PlayerType secondPlayer) {
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
