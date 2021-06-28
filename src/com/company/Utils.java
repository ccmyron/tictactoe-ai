package com.company;

import java.util.Optional;
import java.util.Scanner;

public class Utils {

    /**
     * Checks if a given string is present in a given string array
     *
     * @param arr string array
     * @param s string to be checked for
     */

    private static boolean isStringPresent(String[] arr, String s) {
        for (String element : arr) {
            if (element.equals(s)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Finds the winner based on 3x3 rules
     *
     * Goes through each possible winning combination.
     * Returns an empty optional if there isn't any combinations on the board.
     *
     * @param board the game board
     *
     * @see Optional
     */

    public static Optional<GameResultType> findTheWinner(char[] board) {
        /* Game winning combinations */
        int[][] winArray = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        /* Go through all combinations */
        for (int i = 0; i < 8; i++) {
            if (board[winArray[i][0]] == board[winArray[i][1]] &&
                    board[winArray[i][1]] == board[winArray[i][2]] &&
                    board[winArray[i][0]] != ' ') {
                if (board[winArray[i][0]] == 'X') {
                    return Optional.of(GameResultType.X_WON);
                } else if (board[winArray[i][0]] == 'O') {
                    return Optional.of(GameResultType.O_WON);
                }
            }
        }

        /* Check if the game is tied */
        boolean gameTied = true;
        for (int i = 0; i < 9; ++i) {
            if (board[i] == ' ') {
                gameTied = false;
                break;
            }
        }
        if (gameTied) {
            return Optional.of(GameResultType.DRAW);
        }

        return Optional.empty();
    }


    /**
     * Finds the 'potential' winner based on 3x3 rules
     *
     * Goes through each possible winning combination, and checks if there's
     * one missing move that could complete the game.
     * Used for mediumMove in the Game class
     *
     * @param board the game board
     *
     * @see Optional
     */

    public static Optional<Integer> findTheWinnerMove(char[] board) {
        int[][] winArray = {
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
            if (    board[winArray[i][0]] == board[winArray[i][1]]
                 && board[winArray[i][0]] != ' '
                 && board[winArray[i][2]] == ' '
            ) {
                return Optional.of(winArray[i][2]);
            } else if (board[winArray[i][0]] == board[winArray[i][2]]
                    && board[winArray[i][0]] != ' '
                    && board[winArray[i][1]] == ' '
            ) {
                return Optional.of(winArray[i][1]);
            } else if (board[winArray[i][1]] == board[winArray[i][2]]
                    && board[winArray[i][1]] != ' '
                    && board[winArray[i][0]] == ' '
            ) {
                return Optional.of(winArray[i][0]);
            }
        }

        return Optional.empty();
    }


    /**
     * Returns a GameRule instance with both players set, based on user input
     *
     * @see GameRule
     * @see GameStopException
     */

    public static GameRule getGameRule() throws GameStopException {
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
                                        return new GameRule(PlayerType.USER, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameRule(PlayerType.USER, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameRule(PlayerType.USER, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameRule(PlayerType.USER, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                            case "easy" -> {
                                // Second player
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameRule(PlayerType.BOT_EASY, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameRule(PlayerType.BOT_EASY, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameRule(PlayerType.BOT_EASY, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameRule(PlayerType.BOT_EASY, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                            case "medium" -> {
                                // Second player
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameRule(PlayerType.BOT_MEDIUM, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameRule(PlayerType.BOT_MEDIUM, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameRule(PlayerType.BOT_MEDIUM, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameRule(PlayerType.BOT_MEDIUM, PlayerType.BOT_HARD);
                                    }
                                }
                            }
                            case "hard" -> {
                                // Second player
                                switch (tokens[2]) {
                                    case "user" -> {
                                        return new GameRule(PlayerType.BOT_HARD, PlayerType.USER);
                                    }
                                    case "easy" -> {
                                        return new GameRule(PlayerType.BOT_HARD, PlayerType.BOT_EASY);
                                    }
                                    case "medium" -> {
                                        return new GameRule(PlayerType.BOT_HARD, PlayerType.BOT_MEDIUM);
                                    }
                                    case "hard" -> {
                                        return new GameRule(PlayerType.BOT_HARD, PlayerType.BOT_HARD);
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
