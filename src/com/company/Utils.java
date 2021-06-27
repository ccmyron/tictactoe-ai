package com.company;

import java.util.Optional;
import java.util.Scanner;

public class Utils {

    private static boolean isStringPresent(String[] arr, String s) {
        for (String element : arr) {
            if (element.equals(s)) {
                return true;
            }
        }

        return false;
    }

    public static Optional<GameResultType> findTheWinner(char[] stateArray) {
        int[][] winConditionArray = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        for (int i = 0; i < 8; i++) {
            if (stateArray[winConditionArray[i][0]] == stateArray[winConditionArray[i][1]] &&
                    stateArray[winConditionArray[i][1]] == stateArray[winConditionArray[i][2]] &&
                    stateArray[winConditionArray[i][0]] != ' ') {
                if (stateArray[winConditionArray[i][0]] == 'X') {
                    return Optional.of(GameResultType.X_WON);
                } else if (stateArray[winConditionArray[i][0]] == 'O') {
                    return Optional.of(GameResultType.O_WON);
                }
            }
        }

        boolean gameTied = true;
        for (int i = 0; i < 9; ++i) {
            if (stateArray[i] == ' ') {
                gameTied = false;
                break;
            }
        }

        if (gameTied) {
            return Optional.of(GameResultType.DRAW);
        }
        return Optional.empty();
    }

    public static GameCommand getMenuCommand() throws GameStopException {
        Scanner sc = new Scanner(System.in);
        String[] acceptableTokens = {"user", "easy", "medium", "hard"};

        while (true) {
            String command = sc.nextLine();
            String[] tokens = command.split(" ");

            switch (tokens[0]) {
                case "start" -> {
                    if (tokens.length != 3
                            || !isStringPresent(acceptableTokens, tokens[1])
                            || !isStringPresent(acceptableTokens, tokens[2])) {
                        System.out.println("Bad parameters!");
                        continue;
                    } else {
                        // First player
                        switch (tokens[1]) {
                            // Second player
                            case "user" -> {
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameCommand(PlayerType.USER, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameCommand(PlayerType.USER, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameCommand(PlayerType.USER, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameCommand(PlayerType.USER, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                            case "easy" -> {
                                // Second player
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameCommand(PlayerType.BOT_EASY, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameCommand(PlayerType.BOT_EASY, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameCommand(PlayerType.BOT_EASY, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameCommand(PlayerType.BOT_EASY, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                            case "medium" -> {
                                // Second player
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameCommand(PlayerType.BOT_MEDIUM, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameCommand(PlayerType.BOT_MEDIUM, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameCommand(PlayerType.BOT_MEDIUM, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameCommand(PlayerType.BOT_MEDIUM, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                            case "hard" -> {
                                // Second player
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameCommand(PlayerType.BOT_HARD, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameCommand(PlayerType.BOT_HARD, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameCommand(PlayerType.BOT_HARD, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameCommand(PlayerType.BOT_HARD, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                        }
                    }
                }

                case "exit" -> {
                    throw new GameStopException("Exiting...");
                }

                default -> {
                    System.out.println("Bad parameters!");
                    break;
                }
            }
        }
    }
}
