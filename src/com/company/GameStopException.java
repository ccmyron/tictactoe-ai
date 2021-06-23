package com.company;

public class GameStopException extends Exception {
    public GameStopException(String errorMessage) {
        super(errorMessage);
    }
}
