package com.company;

public class Main {
    public static void main(String[] args) throws GameStopException {
        while (true) {
            try {
                var command = Utils.getGameRule();
                new Game().play(command);
            } catch (GameStopException stopException) {
                break;
            }
        }
    }
}