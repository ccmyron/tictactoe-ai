package com.company;

public enum PlayerType {
    USER,
    BOT_EASY,
    BOT_MEDIUM,
    BOT_HARD;

    public static PlayerType from(String type) {
        switch (type) {
            case "user":
                return PlayerType.USER;
            case "easy":
                return PlayerType.BOT_EASY;
            case "medium":
                return PlayerType.BOT_MEDIUM;
            case "hard":
                return PlayerType.BOT_HARD;
        }

        throw new IllegalArgumentException("Invalid player type");
    }
}
